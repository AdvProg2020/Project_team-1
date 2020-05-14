package controller.customer;

import controller.Menu;
import model.Commodity;
import model.DataManager;
import model.DiscountCode;
import model.Session;
import model.account.PersonalAccount;

import java.util.HashMap;

public class CartMenu extends Menu {
    public int calculateTotalPrice() {
        int price = 0;
        PersonalAccount account = (PersonalAccount) DataManager.getOnlineAccount();
        HashMap<Commodity, Integer> cart = account.getCart();
        for (Commodity commodity : cart.keySet()) {
            price += commodity.getPrice() * cart.get(commodity);
        }
        return price;
    }

    public void decrease(int id) throws Exception {
        Commodity commodity = DataManager.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        if (cart.containsKey(commodity)) {
            if (cart.get(commodity) - 1 == 0) {
                cart.remove(commodity);
                throw new Exception("successfully removed");
            }
            cart.put(commodity, cart.get(commodity) - 1);
            throw new Exception("successfully decreased");
        }
        throw new Exception("this commodity isn't in your cart");
    }

    public void increase(int id) throws Exception {
        Commodity commodity = DataManager.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        if (cart.containsKey(commodity)) {
            cart.put(commodity, cart.get(commodity) + 1);
            throw new Exception("successfully increased");
        }
        cart.put(commodity, 1);
        throw new Exception("successfully added");
    }

    public DiscountCode checkDiscountCode(String code) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        if (!code.equals("")) {
            DiscountCode discountCode = DataManager.getDiscountCodeWithCode(code);
            account.doesHaveThisDiscount(discountCode);
            discountCode.isStillValid();
            account.useThisDiscount(discountCode);
            return discountCode;
        }
        return null;
    }
}
