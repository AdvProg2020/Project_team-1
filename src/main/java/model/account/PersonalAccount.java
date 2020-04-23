package model.account;

import model.Commodity;
import model.DiscountCode;
import model.log.BuyLog;

import java.util.ArrayList;

public class PersonalAccount extends SimpleAccount {
    private ArrayList<DiscountCode> discountCodes;
    private ArrayList<BuyLog> buyLogs;
    private ArrayList<Commodity> cart;
    private BusinessAccount businessAccount;
    private double credit;

    public PersonalAccount(String username, String firstName, String lastName, String email, String phoneNumber, String password) throws Exception {
        super(username, firstName, lastName, email, phoneNumber, password);
        discountCodes = new ArrayList<DiscountCode>();
        buyLogs = new ArrayList<BuyLog>();
        cart = new ArrayList<Commodity>();
        businessAccount = null;
        credit = 0.0;
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public void addDiscountCode(DiscountCode discountCode) {
        discountCodes.add(discountCode);
    }

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

    public void addBuyLog(BuyLog buyLog) {
        buyLogs.add(buyLog);
    }

    public ArrayList<Commodity> getCart() {
        return cart;
    }

    public void addToCart(Commodity commodity) {
        cart.add(commodity);
    }

    public void removeFromCart(Commodity commodity) {
        cart.remove(commodity);
    }

    public BusinessAccount getBusinessAccount() {
        return businessAccount;
    }

    public void setBusinessAccount(BusinessAccount businessAccount) {
        this.businessAccount = businessAccount;
    }

    public double getCredit() {
        return credit;
    }

    public void addToCredit(double amount) {
        credit += amount;
    }
}
