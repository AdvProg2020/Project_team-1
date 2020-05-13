package old.commands.business_panel.manage_off;

import old.commands.Command;
import model.Off;
import model.DataManager;
import model.account.BusinessAccount;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewOffDeatails extends Command {
    public ViewOffDeatails() {
        super.regex = "^view (\\w+)$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        String offId = matcher.group(1);
        Off off = ((BusinessAccount) DataManager.getOnlineAccount()).getOffById(offId);
        if (off == null) {
            throw new Exception("Product ID not found.");
        }
        return off.toString();
    }
}
