package server.controller.customer;

import client.Session;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.model.account.BusinessAccount;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import common.model.commodity.DiscountCode;
import common.model.commodity.Off;
import common.model.log.BuyLog;
import common.model.log.SellLog;
import client.controller.share.Menu;
import server.controller.Statistics;
import server.dataManager.YaDataManager;
import static server.Main.socketB;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import static client.Main.inputStream;
import static client.Main.outputStream;
import static client.view.commandline.View.cartMenu;

public class CartMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private BuyLog buyLog;

    public CartMenu() {
        fxmlFileAddress = "../../../fxml/customer/Cart.fxml";
    }

    public BuyLog getBuyLog() {
        return buyLog;
    }

    public int calculateTotalPrice(PersonalAccount account) throws Exception {
        int price = 0;
        HashMap<Integer, Integer> cart = account.getCart();
        for (int commodityId : cart.keySet()) {
            Commodity commodity = YaDataManager.getCommodityById(commodityId);
            price += commodity.getPrice() * cart.get(commodityId);
        }
        return price;
    }

    public void decrease(int id) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        personalAccount.removeFromCart(id);
        outputStream.writeUTF("remove from cart " + id);
        outputStream.flush();
    }

    public void increase(int id) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        personalAccount.addToCart(id);
        outputStream.writeUTF("add to cart " + id);
        outputStream.flush();
    }

    public DiscountCode getDiscountCodeWithCode(String code , String username) throws Exception {
        PersonalAccount account = YaDataManager.getPersonWithUserName(username);
        if (!code.equals("")) {
            outputStream.writeUTF("send discount with code " + code);
            outputStream.flush();
            DiscountCode discountCode = yaGson.fromJson(inputStream.readUTF(), new TypeToken<DiscountCode>() {
            }.getType());
            account.doesHaveThisDiscount(discountCode);
            discountCode.isActive();
            account.useThisDiscount(discountCode);
            outputStream.writeUTF("update person " + yaGson.toJson(Session.getOnlineAccount(), new TypeToken<PersonalAccount>() {
                    }.getType()));
            outputStream.flush();
            return discountCode;
        }
        return null;
    }

    private int getDiscountPercentage(Commodity commodity) throws IOException {
        for (Off off : YaDataManager.getOffs()) {
            if (off.isActive()) {
                for (int offCommodityId : off.getCommoditiesId()) {
                    if (offCommodityId == commodity.getCommodityId()) {
                        return off.getDiscountPercent();
                    }
                }
            }
        }
        return 0;
    }

    private int useDiscountCode(int price, DiscountCode discountCode) {
        price -= Math.min(discountCode.getMaximumDiscountPrice(), discountCode.getDiscountPercentage() * price / 100);
        return price;
    }

    public void purchase(DiscountCode discountCode , String username) throws Exception { // FIXME: 07/24/2020
        PersonalAccount account = YaDataManager.getPersonWithUserName(username);
        int price = calculateTotalPrice(account);
        if (discountCode != null && !discountCode.equals(""))
            price = cartMenu.useDiscountCode(price, discountCode);
        if ((double) price > account.getCredit()) {
            if (discountCode != null && !discountCode.equals("")) {
                account.dontUseDiscountCode(discountCode);
            }
            throw new Exception("you don't have enough money to pay");
        }
        account.addToCredit(-price);
        for (int commodityId : account.getCart().keySet()) {
            int depositPrice = 0;
            Commodity commodity = YaDataManager.getCommodityById(commodityId);
            depositPrice += commodity.getPrice() * account.getCart().get(commodityId);
            BusinessAccount businessAccount = YaDataManager.getSellerWithUserName(commodity.getSellerUsername());
            int wage = (int)Math.round((Statistics.updatedStats.getWage()*depositPrice)/100);
            int pureDepositPrice = depositPrice - wage;
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socketB.getOutputStream()));
            String token;
            dataOutputStream.writeUTF("get_token bank bank");
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
            token = dataInputStream.readUTF();
            dataOutputStream.writeUTF("create_receipt " + token + " move " + depositPrice + " " + account.getAccountID() + " 1 ");
            dataOutputStream.flush();
            String receipt = dataInputStream.readUTF();
            dataOutputStream.writeUTF("pay " + receipt);
            dataOutputStream.flush();
            businessAccount.addToCredit(pureDepositPrice);
            YaDataManager.removeBusiness(businessAccount);
            YaDataManager.addBusiness(businessAccount);
        }
        BuyLog buyLog = new BuyLog(new Date(), new HashSet<>(account.getCart().keySet()), price,
                calculateTotalPrice(account) - price,
                discountCode == null ? "No discount" : discountCode.getCode());
        account.addBuyLog(buyLog);
        reduceCommodityAmount(account.getCart());
        account.clearCart();
        YaDataManager.removePerson(account);
        YaDataManager.addPerson(account);
        makeSellLogs(buyLog.getSellersUsername(), account);
        this.buyLog = buyLog;
    }

    private void reduceCommodityAmount(HashMap<Integer, Integer> cart) throws Exception {
        for (int commodityId : cart.keySet()) {
            Commodity commodity = YaDataManager.getCommodityById(commodityId);
            commodity.setInventory(commodity.getInventory() - cart.get(commodityId));
        }
    }

    private void makeSellLogs(Set<String> sellersUsername, PersonalAccount account) throws Exception {
        try {
            for (String sellerUsername : sellersUsername) {
                Set<Integer> commoditiesId = new HashSet<>();
                double received = 0;
                double deducted = 0;
                double discount;
                for (int commodityId : account.getCart().keySet()) {
                    Commodity commodity = YaDataManager.getCommodityById(commodityId);
                    if (commodity.getSellerUsername().equals(sellerUsername)) {
                        commoditiesId.add(commodityId);
                        discount = (double) getDiscountPercentage(commodity) / 100;
                        deducted += discount * commodity.getPrice();
                        received += commodity.getPrice() - deducted;
                    }
                }
                BusinessAccount seller = YaDataManager.getSellerWithUserName(sellerUsername);
                seller.addSellLog(new SellLog(new Date(), (int) received, (int) deducted, commoditiesId, account.getUsername()));
                YaDataManager.removeBusiness(seller);
                YaDataManager.addBusiness(seller);
            }
        } catch (NullPointerException ignored) {
        }
    }

    public void purchaseViaBank(DiscountCode discountCode , String username , String token) throws Exception {
        System.out.println("oomadam toosh");
        PersonalAccount account = YaDataManager.getPersonWithUserName(username);
        int price = calculateTotalPrice(account);
        if (discountCode != null && !discountCode.equals("")) {
            price = cartMenu.useDiscountCode(price, discountCode);
        }
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        for (int commodityId : account.getCart().keySet()) {
            int depositPrice = 0;
            Commodity commodity = YaDataManager.getCommodityById(commodityId);
            depositPrice += commodity.getPrice() * account.getCart().get(commodityId);
            BusinessAccount businessAccount = YaDataManager.getSellerWithUserName(commodity.getSellerUsername());
            int wage = (int)Math.round((Statistics.updatedStats.getWage()*depositPrice)/100);
            System.out.println("wage " + wage);
            int pureDepositPrice = depositPrice - wage;
            dataOutputStream.writeUTF("create_receipt " + token + " move " + depositPrice + " " + account.getAccountID() + " 1 ");
            String receipt = dataInputStream.readUTF();
            dataOutputStream.writeUTF("pay " + receipt);
            String respond = dataInputStream.readUTF();
            System.out.println("Respond " + respond);
            if (!respond.equals("done successfully"))
                throw new Exception(respond);
            businessAccount.addToCredit(pureDepositPrice);
            YaDataManager.removeBusiness(businessAccount);
            YaDataManager.addBusiness(businessAccount);
        }
        BuyLog buyLog = new BuyLog(new Date(), new HashSet<>(account.getCart().keySet()), price,
                calculateTotalPrice(account) - price,
                discountCode == null ? "No discount" : discountCode.getCode());
        account.addBuyLog(buyLog);
        reduceCommodityAmount(account.getCart());
        account.clearCart();
        YaDataManager.removePerson(account);
        YaDataManager.addPerson(account);
        makeSellLogs(buyLog.getSellersUsername(), account);
    }
}
