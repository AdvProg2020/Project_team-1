package commands;

import controller.HandleMenu;
import controller.LoginRegisterMenu;
import model.Commodity;
import model.SuperMarket;
import model.account.PersonalAccount;

public class AddToCartCommand extends Command {
    Commodity commodity;

    public AddToCartCommand() {
        this.regex = "^add to cart$";
        this.commodity = (Commodity)SuperMarket.getNearHand();
    }

    @Override
    public String runCommand(String command) throws Exception {
        if (SuperMarket.getOnlineAccount() == null){
            HandleMenu.setMenu(new LoginRegisterMenu());
            return "please login first";
        }
        PersonalAccount personalAccount = ((PersonalAccount)SuperMarket.getOnlineAccount());
        personalAccount.addToCart(commodity);
        return "Commodity successfully added to cart";
    }
}
