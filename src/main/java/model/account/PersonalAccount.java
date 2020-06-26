package model.account;

import controller.data.YaDataManager;
import model.commodity.Commodity;
import model.commodity.DiscountCode;
import model.exception.InvalidAccountInfoException;
import model.log.BuyLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PersonalAccount extends SimpleAccount {
    private HashMap<DiscountCode, Integer> discountCodes;
    private ArrayList<BuyLog> buyLogs;
    private HashMap<Commodity, Integer> cart;
    private double credit;

    public PersonalAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "personal");
        discountCodes = new HashMap<>();
        buyLogs = new ArrayList<>();
        cart = new HashMap<>();
        credit = 1000.0;
    }

    public PersonalAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password, String imagePath) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "personal", imagePath);
        discountCodes = new HashMap<>();
        buyLogs = new ArrayList<>();
        cart = new HashMap<>();
        credit = 0.0;
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean hasThisInCart(Commodity commodity) {
        for (Commodity commodity1 : cart.keySet()) {
            if (commodity.equals(commodity1)) {
                return true;
            }
        }
        return false;
    }

    public int getAmount(Commodity commodity) throws Exception {
        for (Commodity commodity1 : cart.keySet()) {
            if (commodity.equals(commodity1)) {
                return cart.get(commodity1);
            }
        }
        return 0;
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
                return;
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

    private boolean isCommodityInTheCart(Commodity commodity) {
        for (Commodity commodity1 : cart.keySet()) {
            if (commodity1.getCommodityId() == commodity.getCommodityId())
                return true;
        }
        return false;
    }

    public void addToCart(Commodity commodity) throws Exception {
        if (!isCommodityInTheCart(commodity)) {
            if (commodity.getInventory() > 0)
                cart.put(commodity, 1);
            else
                throw new Exception("We don't enough number of this commodity, excuse us");
        } else {

            if (cart.get(commodity) < commodity.getInventory())
                cart.put(commodity, cart.get(commodity) + 1);
            else
                throw new Exception("We don't enough number of this commodity, excuse us");
        }
    }

    public void removeFromCart(Commodity commodity) throws Exception {
        for (Commodity commodity1 : cart.keySet()) {
            if (commodity.equals(commodity1)) {
                cart.remove(commodity1);
                return;
            }
        }
        throw new Exception("this product is not in your cart");
    }

    public double getCredit() {
        return credit;
    }

    public void addToCredit(double amount) {
        credit += amount;
    }

    public void doesHaveThisDiscount(DiscountCode discountCode) throws Exception {
        for (DiscountCode code : discountCodes.keySet()) {
            if (code.equals(discountCode)) {
                if (discountCodes.get(code) < code.getMaximumNumberOfUses()) {
                    return;
                }
            }
        }
        throw new Exception("you can't use this discount code");
    }

    public void useThisDiscount(DiscountCode discountCode) throws IOException {
        YaDataManager.removePerson(this);
        int numberOfTimesUsed = discountCodes.get(discountCode) + 1;
        discountCodes.put(discountCode, numberOfTimesUsed);
        YaDataManager.addPerson(this);
    }

    public void dontUseDiscountCode(DiscountCode discountCode) throws IOException {
        YaDataManager.removePerson(this);
        int numberOfTimesUsed = discountCodes.get(discountCode) - 1;
        discountCodes.put(discountCode, numberOfTimesUsed);
        YaDataManager.addPerson(this);
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
