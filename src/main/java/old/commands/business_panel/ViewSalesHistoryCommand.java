package old.commands.business_panel;

import old.commands.Command;
import model.DataManager;
import model.account.BusinessAccount;
import model.log.SellLog;

public class ViewSalesHistoryCommand extends Command {
    public ViewSalesHistoryCommand() {
        super.regex = "^view sales history$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        StringBuilder result = new StringBuilder("");
        for (SellLog sellLog : ((BusinessAccount) DataManager.getOnlineAccount()).getSellLogs()) {
            result.append(sellLog.toString() + "\n");
        }
        return result.toString();
    }
}
