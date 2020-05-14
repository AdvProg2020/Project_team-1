package controller;

import model.DiscountCode;
import model.Session;
import model.account.PersonalAccount;

import java.util.Set;

public class CustomerMenu extends Menu {
    public double getBalance() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getCredit();
    }

    public Set<DiscountCode> getMyDiscounts() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getDiscountCodes();
    }
}
