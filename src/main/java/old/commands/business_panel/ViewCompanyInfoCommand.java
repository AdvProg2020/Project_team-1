package old.commands.business_panel;

import old.commands.Command;
import model.DataManager;
import model.account.BusinessAccount;

public class ViewCompanyInfoCommand extends Command {

    public ViewCompanyInfoCommand() {
        super.regex = "^view company information$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        return ((BusinessAccount) DataManager.getOnlineAccount()).getBusinessName();
    }
}
