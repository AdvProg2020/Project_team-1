package old.commands;

import old.HandleMenu;
import old.LoginRegisterMenu;
import model.Commodity;
import model.DataManager;
import model.account.PersonalAccount;

public class AddToCartCommand extends Command {
    Commodity commodity;

    public AddToCartCommand() {
        this.regex = "^add to cart$";
        this.commodity = (Commodity) DataManager.getNearHand();
    }

    @Override
    public String runCommand(String command) throws Exception {
        if (DataManager.getOnlineAccount() == null){
            HandleMenu.setMenu(new LoginRegisterMenu());
            return "please login first";
        }
        PersonalAccount personalAccount = ((PersonalAccount) DataManager.getOnlineAccount());
        personalAccount.addToCart(commodity);
        return "Commodity successfully added to cart";
    }
}
