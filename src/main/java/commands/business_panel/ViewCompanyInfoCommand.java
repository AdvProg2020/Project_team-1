package commands.business_panel;

import commands.Command;
import model.SuperMarket;
import model.account.BusinessAccount;

public class ViewCompanyInfoCommand extends Command {

    public ViewCompanyInfoCommand() {
        super.regex = "^view company information$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        return ((BusinessAccount)SuperMarket.getOnlineAccount()).getBusinessName();
    }
}
