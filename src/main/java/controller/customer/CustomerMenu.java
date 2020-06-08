package controller.customer;

import controller.comparator.Sort;
import controller.share.Menu;
import controller.share.MenuHandler;
import model.Session;
import model.account.PersonalAccount;
import model.commodity.DiscountCode;
import model.log.BuyLog;

import java.util.ArrayList;

import static view.commandline.View.*;

public class CustomerMenu extends Menu {
    private String orderSortType = "payed";
    private String discountsSortType = "code";
    public double getBalance() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getCredit();
    }

    public ArrayList<DiscountCode> getMyDiscounts() throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        ArrayList<DiscountCode> discountCodes = new ArrayList<>(account.getDiscountCodes());
        Sort.sortDiscountArrayList(discountCodes, this.discountsSortType);
        discountCodes.removeIf(discountCode -> !discountCode.isOnTime());
        return discountCodes;
    }

    public int getNumberOfTimesUsedDiscount(DiscountCode discountCode) {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getNumberOfTimesUsed(discountCode);
    }

    public void goToCartMenu() {
        cartMenu.setPreviousMenu(customerMenu);
        MenuHandler.getInstance().setCurrentMenu(cartMenu);
    }

    private void goToOrderMenu() {
        MenuHandler.getInstance().setCurrentMenu(orderMenu);
        orderMenu.setPreviousMenu(customerMenu);
    }

    public void setOrderSortType(String orderSortType) {
        this.orderSortType = orderSortType;
    }

    public ArrayList<BuyLog> getOrders() throws Exception {
        goToOrderMenu();
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        Sort.sortBuyLogArrayList(account.getBuyLogs(), this.orderSortType);
        return account.getBuyLogs();
    }

    public void setDiscountsSortType(String discountsSortType) {
        this.discountsSortType = discountsSortType;
    }
}
