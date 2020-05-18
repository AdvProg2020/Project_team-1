package controller.customer;

import controller.comparator.Sort;
import controller.share.Menu;
import controller.share.MenuHandler;
import model.commodity.DiscountCode;
import model.Session;
import model.account.PersonalAccount;
import model.log.BuyLog;

import java.util.ArrayList;
import java.util.Set;

import static view.View.*;

public class CustomerMenu extends Menu {
    private String orderSortType = "payed money";
    public double getBalance() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getCredit();
    }

    public Set<DiscountCode> getMyDiscounts() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getDiscountCodes();
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
}
