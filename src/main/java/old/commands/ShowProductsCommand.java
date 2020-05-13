package old.commands;

import model.Commodity;
import model.DataManager;
import model.account.PersonalAccount;

public class ShowProductsCommand extends Command {
    public ShowProductsCommand() {
        this.regex = "^view cart$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String respond = "";
        PersonalAccount personalAccount = (PersonalAccount) DataManager.getOnlineAccount();
        for (Commodity commodity : personalAccount.getCart().keySet()) {
            respond += "[" + commodity.toString() + "]";
        }
        return respond;
    }
}
