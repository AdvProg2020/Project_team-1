package old.commands.business_panel;

import old.commands.Command;
import old.HandleMenu;
import old.ManageProductsMenu;
import model.Commodity;
import model.DataManager;
import model.account.BusinessAccount;

public class ManageProductsCommand extends Command {
    public ManageProductsCommand() {
        super.regex = "$manage products^";
    }


    @Override
    public String runCommand(String command) throws Exception {
        StringBuilder result = new StringBuilder("");
        for (Commodity commodity : ((BusinessAccount) DataManager.getOnlineAccount()).getCommodities()) {
            result.append(commodity.toString());
            result.append('\n');
        }
        HandleMenu.setMenu(new ManageProductsMenu());
        return result.toString();
    }
}
