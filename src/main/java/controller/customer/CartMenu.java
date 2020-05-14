package controller.customer;

import controller.Menu;
import model.Commodity;
import model.DataManager;
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
}
