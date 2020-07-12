package server.controller.customer;

import client.Session;
import common.model.account.BusinessAccount;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import common.model.commodity.DiscountCode;
import common.model.commodity.Off;
import common.model.log.BuyLog;
import common.model.log.SellLog;
import server.controller.comparator.Sort;
import server.controller.share.Menu;
import server.controller.share.MenuHandler;
import server.data.YaDataManager;

import java.io.IOException;
import java.util.*;

import static client.view.commandline.View.cartMenu;
import static client.view.commandline.View.commodityMenu;

public class CartMenu extends Menu {
    private String productSortType = "visits";
    private BuyLog buyLog;

    public BuyLog getBuyLog() {
        return buyLog;
    }

    public CartMenu() {
        fxmlFileAddress = "../../../fxml/customer/Cart.fxml";
    }

    public int calculateTotalPrice() throws Exception {
        int price = 0;
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
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
    }

    public int getAmountInCart(Commodity commodity) throws Exception {
        return ((PersonalAccount) Session.getOnlineAccount()).getAmount(commodity.getCommodityId());
    }

    public void increase(int id) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        personalAccount.addToCart(id);
    }

    public DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        if (!code.equals("")) {
            DiscountCode discountCode = YaDataManager.getDiscountCodeWithCode(code);
            account.doesHaveThisDiscount(discountCode);
            discountCode.isActive();
            account.useThisDiscount(discountCode);
            return discountCode;
        }
        return null;
    }

    public int getDiscountPercentage(Commodity commodity) throws IOException {
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

    public void purchase(DiscountCode discountCode) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        int price = calculateTotalPrice();
        if (discountCode != null && !discountCode.equals(""))
            price = cartMenu.useDiscountCode(price, discountCode);
        if ((double) price > account.getCredit()) {
            if (discountCode != null && !discountCode.equals("")) {
                account.dontUseDiscountCode(discountCode);
            }
            throw new Exception("you don't have enough money to pay");
        }
        account.addToCredit(-price);
        BuyLog buyLog = new BuyLog(new Date(), new HashSet<>(account.getCart().keySet()), price, calculateTotalPrice() -
                price, discountCode == null ? "No discount" : discountCode.getCode());
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

    public void checkIsCommoditiesAvailable() throws Exception {
        HashMap<Integer, Integer> cart = ((PersonalAccount) Session.getOnlineAccount()).getCart();
        for (int commodityId : cart.keySet()) {
            Commodity commodity = YaDataManager.getCommodityById(commodityId);
            if (commodity.getInventory() < cart.get(commodityId)) {
                throw new Exception("Some of products in your cart are not available");
            }
        }
    }

    public void goToCommodityMenu(int id) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(id);
        YaDataManager.removeCommodity(commodity);
        commodity.setNumberOfVisits(commodity.getNumberOfVisits() + 1);
        YaDataManager.addCommodity(commodity);
        MenuHandler.getInstance().setCurrentMenu(commodityMenu);
        commodityMenu.setCommodity(commodity);
        commodityMenu.setPreviousMenu(cartMenu);
    }

    public void setProductSortType(String productSortType) {
        this.productSortType = productSortType;
    }

    public ArrayList<Commodity> getCartProducts() throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        ArrayList<Commodity> commodities = new ArrayList<>();
        for (Integer commodityId : account.getCart().keySet()) {
            commodities.add(YaDataManager.getCommodityById(commodityId));
        }
        Sort.sortProductArrayList(commodities, this.productSortType);
        return commodities;
    }
}
