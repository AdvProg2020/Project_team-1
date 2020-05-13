package old.commands.business_panel;

import old.commands.Command;
import model.SuperMarket;
import model.account.BusinessAccount;

public class ViewBalance extends Command {

    public ViewBalance() {
        super.regex = "^view balance$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        return String.valueOf(((BusinessAccount)SuperMarket.getOnlineAccount()).getCredit());
    }
}
