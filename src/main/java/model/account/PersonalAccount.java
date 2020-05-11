package model.account;

import model.Commodity;
import model.DiscountCode;
import model.log.BuyLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PersonalAccount extends SimpleAccount {
    private HashMap<DiscountCode, Integer> discountCodes;
    private ArrayList<BuyLog> buyLogs;
    private HashMap<Commodity,Integer> cart;
    private BusinessAccount businessAccount;
    private int credit;

    public PersonalAccount(String username, String firstName, String lastName, String email, String phoneNumber, String password) throws Exception {
        super(username, firstName, lastName, email, phoneNumber, password);
        discountCodes = new HashMap<>();
        buyLogs = new ArrayList<>();
        cart = new HashMap<>();
        businessAccount = null;
        credit = 0;
    }

    public Set<DiscountCode> getDiscountCodes() {
        return discountCodes.keySet();
    }

    public void addDiscountCode(DiscountCode discountCode) {
        discountCodes.put(discountCode, 0);
    }

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

    public void addBuyLog(BuyLog buyLog) {
        buyLogs.add(buyLog);
    }

    public HashMap<Commodity, Integer> getCart() {
        return cart;
    }

    public int getTotalPrice() {
        int price = 0;
        for (Commodity commodity : cart.keySet()) {
            price += cart.get(commodity) * commodity.getPrice();
        }
        return price;
    }

    public void addToCart(Commodity commodity) {
        cart.put(commodity, 1);
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

    public int getCredit() {
        return credit;
    }

    public void addToCredit(int amount) {
        credit += amount;
    }

    public void doesHaveThisDiscount(DiscountCode discountCode) throws Exception {
        if (discountCodes.containsKey(discountCode)) {
            return;
        }
        throw new Exception("you can't use this discount code");
    }

    public void useThisDiscount(DiscountCode discountCode) {
        int numberOfTimesUsed = discountCodes.get(discountCode) + 1;
        discountCodes.remove(discountCode);
        if (numberOfTimesUsed < discountCode.getMaximumNumberOfUses()) {
            discountCodes.put(discountCode, numberOfTimesUsed);
        }
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
