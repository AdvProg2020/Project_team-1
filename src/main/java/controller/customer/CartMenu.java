package controller.customer;

import controller.comparator.Sort;
import controller.data.YaDataManager;
import controller.share.Menu;
import controller.share.MenuHandler;
import model.Session;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.commodity.Commodity;
import model.commodity.DiscountCode;
import model.commodity.Off;
import model.log.BuyLog;
import model.log.SellLog;

import java.io.IOException;
import java.util.*;

import static view.View.cartMenu;
import static view.View.commodityMenu;

public class CartMenu extends Menu {
    private String productSortType = "visits";
    public int calculateTotalPrice() {
        int price = 0;
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = account.getCart();
        for (Commodity commodity : cart.keySet()) {
            price += commodity.getPrice() * cart.get(commodity);
        }
        return price;
    }

    public void decrease(int id) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        if (personalAccount.hasThisInCart(commodity)) {
            if (personalAccount.getAmount(commodity) == 1) {
                personalAccount.removeFromCart(commodity);
                YaDataManager.removePerson(personalAccount);
                YaDataManager.addPerson(personalAccount);
                throw new Exception("successfully removed");
            }
            int amount = personalAccount.getAmount(commodity) - 1;
            personalAccount.removeFromCart(commodity);
            YaDataManager.removePerson(personalAccount);
            cart.put(commodity, amount);
            YaDataManager.addPerson(personalAccount);
            throw new Exception("successfully decreased");
        }
        throw new Exception("this product isn't in your cart");
    }

    public int getAmountInCart(Commodity commodity) throws Exception {
        return ((PersonalAccount) Session.getOnlineAccount()).getAmount(commodity);
    }

    public void increase(int id) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        YaDataManager.removePerson(personalAccount);
        if (personalAccount.hasThisInCart(commodity)) {
            int amount = personalAccount.getAmount(commodity);
            personalAccount.removeFromCart(commodity);
            cart.put(commodity, amount + 1);
            YaDataManager.addPerson(personalAccount);
            throw new Exception("successfully increased");
        }
        cart.put(commodity, 1);
        YaDataManager.addPerson(personalAccount);
        throw new Exception("successfully added");
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
                for (Commodity offCommodity : off.getCommodities()) {
                    if (offCommodity.getCommodityId() == commodity.getCommodityId()) {
                        return off.getDiscountPercent();
                    }
                }
            }
        }
        return 0;
    }

    private int useDiscountCode(int price, DiscountCode discountCode) {
        if (discountCode.getMaximumDiscountPrice() <= discountCode.getDiscountPercentage() * price / 100) {
            price -= discountCode.getMaximumDiscountPrice();
        } else {
            price -= discountCode.getDiscountPercentage() * price / 100;
        }
        return price;
    }

    public void purchase(DiscountCode discountCode) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        int price = calculateTotalPrice();
        if (discountCode != null)
            price = cartMenu.useDiscountCode(price, discountCode);
        if (price > account.getCredit()) {
            account.dontUseDiscountCode(discountCode);
            throw new Exception("you don't have enough money to pay");
        }
        YaDataManager.removePerson(account);
        account.addToCredit(-price);
        BuyLog buyLog = new BuyLog(new Date(), account.getCart().keySet(), price, calculateTotalPrice() -
                price, discountCode);
        account.addBuyLog(buyLog);
        YaDataManager.addPerson(account);
        makeSellLogs(buyLog.getSellers(), account);
    }

    private void makeSellLogs(Set<BusinessAccount> sellers, PersonalAccount account) throws IOException {
        try {
            for (BusinessAccount seller : sellers) {
                Set<Commodity> commodities = new HashSet<>();
                double received = 0;
                double deducted = 0;
                double discount;
                for (Commodity commodity : account.getCart().keySet()) {
                    if (commodity.getSeller().getUsername().equals(seller.getUsername())) {
                        commodities.add(commodity);
                        discount = (double) getDiscountPercentage(commodity) / 100;
                        deducted += discount * commodity.getPrice();
                        received += commodity.getPrice() - deducted;
                    }
                }
                YaDataManager.removeBusiness(seller);
                seller.addSellLog(new SellLog(new Date(), (int) received, (int) deducted, commodities, account));
                YaDataManager.addBusiness(seller);
            }
        } catch (NullPointerException ignored) {
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
        commodities.addAll(account.getCart().keySet());
        Sort.sortProductArrayList(commodities, this.productSortType);
        return commodities;
    }
}
