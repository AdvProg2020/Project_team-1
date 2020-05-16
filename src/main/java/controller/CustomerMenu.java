package controller;

import model.DiscountCode;
import model.Session;
import model.account.PersonalAccount;

import java.util.Set;

import static view.View.*;

public class CustomerMenu extends Menu {
    public double getBalance() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getCredit();
    }

    public Set<DiscountCode> getMyDiscounts() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getDiscountCodes();
    }

    public void goToCartMenu() {
        cartMenu.setPreviousMenu(customerMenu);
        MenuHandler.getInstance().setCurrentMenu(cartMenu);
    }

    public void goToOrderMenu() {
        MenuHandler.getInstance().setCurrentMenu(orderMenu);
        orderMenu.setPreviousMenu(customerMenu);
    }
}
