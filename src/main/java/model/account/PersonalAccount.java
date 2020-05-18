package model.account;

import model.commodity.Commodity;
import model.commodity.DiscountCode;
import model.log.BuyLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PersonalAccount extends SimpleAccount {
    private HashMap<DiscountCode, Integer> discountCodes;
    private ArrayList<BuyLog> buyLogs;
    private HashMap<Commodity, Integer> cart;
    private double credit;

    public PersonalAccount(String username, String firstName, String lastName, String email, String phoneNumber, String password) throws Exception {
        super(username, firstName, lastName, email, phoneNumber, password, "personal");
        discountCodes = new HashMap<>();
        buyLogs = new ArrayList<>();
        cart = new HashMap<>();
        credit = 0.0;
    }

    public HashMap<DiscountCode, Integer> discountCodeIntegerHashMap() {
        return discountCodes;
    }

    public Set<DiscountCode> getDiscountCodes() {
        return discountCodes.keySet();
    }

    public void addDiscountCode(DiscountCode discountCode) {
        discountCodes.put(discountCode, 0);
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        for (DiscountCode code : discountCodes.keySet()) {
            if (code.equals(discountCode)) {
                discountCodes.remove(code);
            }
        }
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

    private boolean isCommodityInTheCart(Commodity commodity) {
        return cart.containsKey(commodity);
    }

    public void addToCart(Commodity commodity) throws Exception {
        if (isCommodityInTheCart(commodity)) {
            if (commodity.getInventory() > 0)
                cart.put(commodity, 1);
            else
                throw new Exception("we don't enough number of this commodity, excuse us");
        } else {
            if (cart.get(commodity) < commodity.getInventory())
                cart.put(commodity, cart.get(commodity) + 1);
            else
                throw new Exception("we don't enough number of this commodity, excuse us");
        }
    }

    public void removeFromCart(Commodity commodity) {
        cart.remove(commodity);
    }

    public double getCredit() {
        return credit;
    }

    public void addToCredit(double amount) {
        credit += amount;
    }

    public void doesHaveThisDiscount(DiscountCode discountCode) throws Exception {
        if (discountCodes.containsKey(discountCode) && discountCodes.get(discountCode) < discountCode.getMaximumNumberOfUses()) {
            return;
        }
        throw new Exception("you can't use this discount code");
    }

    public void useThisDiscount(DiscountCode discountCode) {
        int numberOfTimesUsed = discountCodes.get(discountCode) + 1;
        discountCodes.put(discountCode, numberOfTimesUsed);
    }

    public void dontUseDiscountCode(DiscountCode discountCode) {
        int numberOfTimesUsed = discountCodes.get(discountCode) - 1;
        discountCodes.put(discountCode, numberOfTimesUsed);
    }

    public int getNumberOfTimesUsed(DiscountCode discountCode) {
        return discountCodes.get(discountCode);
    }

    @Override
    public String toString() {
        return "PersonalAccount{" +
                "credit=" + credit +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
