package commands;

import controller.GetCartMenu;
import controller.HandleMenu;
import model.Commodity;
import model.SuperMarket;
import model.account.PersonalAccount;

public class ViewCartCommand extends Command {
    public ViewCartCommand() {
        super.regex = "^view cart$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new GetCartMenu());
        return "Enter your next command";
    }
}
