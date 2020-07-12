package common.model.account;

import common.model.commodity.Commodity;
import common.model.commodity.DiscountCode;
import common.model.exception.InvalidAccountInfoException;
import common.model.log.BuyLog;
import server.data.YaDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PersonalAccount extends SimpleAccount {
    private HashMap<DiscountCode, Integer> discountCodes;
    private ArrayList<BuyLog> buyLogs;
    private HashMap<Integer, Integer> cart;
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
        credit = 1000.0;
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean hasThisInCart(int commodityId) {
        return cart.containsKey(commodityId);
    }

    public int getAmount(int commodityId) {
        if (cart.containsKey(commodityId)) {
            return cart.get(commodityId);
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
            if (code.getCode().equals(discountCode.getCode())) {
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

    public HashMap<Integer, Integer> getCart() {
        return cart;
    }

    public void addToCart(Integer commodityId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(commodityId);
        if (!hasThisInCart(commodityId)) {
            if (commodity.getInventory() > 0)
                cart.put(commodityId, 1);
            else
                throw new Exception("We don't enough number of this commodity, excuse us");
        } else {

            if (cart.get(commodityId) < commodity.getInventory())
                cart.put(commodityId, cart.get(commodityId) + 1);
            else
                throw new Exception("We don't enough number of this commodity, excuse us");
        }
    }

    public void removeFromCart(int commodityId) throws Exception {
        if (cart.containsKey(commodityId)) {
            if (cart.get(commodityId) == 1) {
                cart.remove(commodityId);
            } else {
                cart.put(commodityId, cart.get(commodityId) - 1);
            }
            return;
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
