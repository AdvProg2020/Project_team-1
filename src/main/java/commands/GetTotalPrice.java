package commands;

import model.Commodity;
import model.SuperMarket;
import model.account.PersonalAccount;

import java.util.HashMap;

public class GetTotalPrice extends Command {
    public GetTotalPrice() {
        this.regex = "show total price";
    }

    @Override
    public String runCommand(String command) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) SuperMarket.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        int price = 0;
        for (Commodity commodity : cart.keySet()) {
            price += cart.get(commodity) * commodity.getPrice();
        }
        return "total price is " + price;
    }
}
