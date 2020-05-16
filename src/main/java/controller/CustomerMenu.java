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
        HandleMenu.setMenu(cartMenu);
    }

    public void goToOrderMenu() {
        HandleMenu.setMenu(orderMenu);
        orderMenu.setPreviousMenu(customerMenu);
    }
}
