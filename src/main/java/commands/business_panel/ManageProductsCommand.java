package commands.business_panel;

import commands.Command;
import model.Commodity;
import model.SuperMarket;
import model.account.BusinessAccount;

public class ManageProductsCommand extends Command {
    public ManageProductsCommand() {
        super.regex = "$manage products^";
    }


    @Override
    public String runCommand(String command) throws Exception {
        StringBuilder result = new StringBuilder("");
        for (Commodity commodity : ((BusinessAccount) SuperMarket.getOnlineAccount()).getCommodities()) {
            result.append(commodity.toString());
            result.append('\n');
        }
        //Change menu
        return result.toString();
    }
}
