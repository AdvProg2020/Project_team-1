package commands;

import controller.AccountMenu;
import controller.HandleMenu;
import controller.ViewPersonalInfoMenu;

public class ViewPersonalInfoCommand extends Command {
    AccountMenu menu;

    public ViewPersonalInfoCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        this.menu = (AccountMenu) HandleMenu.getMenu();
        HandleMenu.setMenu(new ViewPersonalInfoMenu());
        return menu.getAccount().toString();
    }
}
