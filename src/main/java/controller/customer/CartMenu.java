package controller.customer;

import controller.Menu;
import controller.MenuHandler;
import model.*;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.log.BuyLog;
import model.log.SellLog;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static view.View.cartMenu;
import static view.View.commodityMenu;

public class CartMenu extends Menu {
    public int calculateTotalPrice() {
        int price = 0;
        PersonalAccount account = (PersonalAccount) DataManager.getOnlineAccount();
        HashMap<Commodity, Integer> cart = account.getCart();
        for (Commodity commodity : cart.keySet()) {
            price += commodity.getPrice() * cart.get(commodity);
        }
        return price;
    }

    public void decrease(int id) throws Exception {
        Commodity commodity = DataManager.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        if (cart.containsKey(commodity)) {
            if (cart.get(commodity) - 1 == 0) {
                cart.remove(commodity);
                throw new Exception("successfully removed");
            }
            cart.put(commodity, cart.get(commodity) - 1);
            throw new Exception("successfully decreased");
        }
        throw new Exception("this commodity isn't in your cart");
    }

    public void increase(int id) throws Exception {
        Commodity commodity = DataManager.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        if (cart.containsKey(commodity)) {
            cart.put(commodity, cart.get(commodity) + 1);
            throw new Exception("successfully increased");
        }
        cart.put(commodity, 1);
        throw new Exception("successfully added");
    }

    public DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        if (!code.equals("")) {
            DiscountCode discountCode = DataManager.getDiscountCodeWithCode(code);
            account.doesHaveThisDiscount(discountCode);
            discountCode.isStillValid();
            account.useThisDiscount(discountCode);
            return discountCode;
        }
        return null;
    }

    public int getDiscountPercentage(Commodity commodity) throws IOException {
        for (Off off : DataManager.getAllOffs()) {
            for (Commodity offCommodity : off.getCommodities()) {
                if (offCommodity.getCommodityId() == commodity.getCommodityId()) {
                    return off.getDiscountPercent();
                }
            }
        }
        return 0;
    }

    public int useDiscountCode(int price, DiscountCode discountCode) {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        if (discountCode.getMaximumDiscountPrice() <= discountCode.getDiscountPercentage() * price / 100) {
            price -= discountCode.getMaximumDiscountPrice();
        } else {
            price -= discountCode.getDiscountPercentage() * price / 100;
        }
        return price;
    }

    public void purchase(int price, DiscountCode discountCode, String address, String phone, String postalCode) throws
            Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        if (price > account.getCredit()) {
            throw new Exception("you don't have enough money to pay");
        }
        account.addToCredit(-price);
        BuyLog buyLog = new BuyLog(new Date(), account.getCart().keySet(), price, discountCode, address, phone,
                postalCode);
        account.addBuyLog(buyLog);
        makeSellLogs(buyLog.getSellers(), account);
    }

    private void makeSellLogs(Set<BusinessAccount> sellers, PersonalAccount account) throws IOException {
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
            seller.addSellLog(new SellLog(new Date(), (int) received, (int) deducted, commodities, account));
        }
    }

    public void goToCommodityMenu(int id) throws Exception {
        Commodity commodity = DataManager.getCommodityById(id);
        MenuHandler.getInstance().setCurrentMenu(commodityMenu);
        commodityMenu.setCommodity(commodity);
        commodityMenu.setPreviousMenu(cartMenu);
    }
}
