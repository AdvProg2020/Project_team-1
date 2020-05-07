package commands;

import model.Commodity;
import model.SuperMarket;
import model.account.PersonalAccount;

import java.util.HashMap;
import java.util.regex.Matcher;

public class IncreaseCommand extends Command {
    Matcher matcher;
    public IncreaseCommand() {
        this.regex = "^increase ?<productId>\\S+";
    }

    public boolean checkIsIdNumeric(String id){
        try {
            Integer.parseInt(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String runCommand(String command) throws Exception {
        if (!checkIsIdNumeric(matcher.group("productId"))){
            return "product id should be numeric";
        }
        int id = Integer.parseInt(matcher.group("productId"));
        Commodity commodity = SuperMarket.getCommodityById(id);
        PersonalAccount personalAccount = (PersonalAccount)SuperMarket.getOnlineAccount();
        HashMap<Commodity , Integer > cart = personalAccount.getCart();
        if (cart.containsKey(commodity))
            cart.put(commodity,cart.get(commodity) + 1);
        else
            cart.put(commodity,1);
        return "Commodity successfully added to the cart";
    }
}
