package commands;

import model.Commodity;
import model.SuperMarket;
import model.account.PersonalAccount;

import java.util.HashMap;
import java.util.regex.Matcher;

public class DecreaseCommand extends Command {
    Matcher matcher;

    public DecreaseCommand() {
        this.regex = "^decrease ?<productId> \\S+";
    }

    public boolean checkIsIdNumeric(String id) {
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void decreaseCommodity(Commodity commodity, HashMap<Commodity,Integer> cart){
        if (cart.get(commodity) - 1 == 0)
            cart.remove(commodity);
        else
            cart.put(commodity,cart.get(commodity) + 1);
    }

    @Override
    public String runCommand(String command) throws Exception {
        if (!checkIsIdNumeric(matcher.group("productId"))) {
            return "product id should be numeric";
        }
        int id = Integer.parseInt(matcher.group("productId"));
        Commodity commodity = SuperMarket.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount) SuperMarket.getOnlineAccount();
        HashMap<Commodity, Integer> cart = personalAccount.getCart();
        if (cart.containsKey(commodity)) {
          decreaseCommodity(commodity, cart );
        } else
            return "Commodity is not exist";
        return "Commodity successfully removed from the cart";
    }
}
