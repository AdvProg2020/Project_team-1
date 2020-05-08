package commands.business_panel;

import commands.Command;
import controller.HandleMenu;
import controller.business_menu.ManageOffMenu;
import model.Commodity;
import model.Off;
import model.SuperMarket;
import model.account.BusinessAccount;

public class ViewOffs extends Command {

    public ViewOffs() {
        super.regex = "view offs";
    }

    @Override
    public String runCommand(String command) throws Exception {
        StringBuilder result = new StringBuilder("");
        for (Off off: ((BusinessAccount) SuperMarket.getOnlineAccount()).getOffs()) {
            result.append(off.toString());
            result.append('\n');
        }
        HandleMenu.setMenu(new ManageOffMenu());
        return result.toString();
    }
}
