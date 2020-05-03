package commands;

import controller.AccountMenu;
import controller.HandleMenu;
import controller.ViewPersonalInfoMenu;
import model.SuperMarket;

public class ViewPersonalInfoCommand extends Command {
    AccountMenu menu;

    public ViewPersonalInfoCommand() {
        super.regex = "^view personal info$";
    }

    @Override
    public String runCommand(String command) {
        this.menu = (AccountMenu) HandleMenu.getMenu();
        HandleMenu.setMenu(new ViewPersonalInfoMenu(SuperMarket.getOnlineAccount()));
        return menu.getAccount().toString();
    }
}
