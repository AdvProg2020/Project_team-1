package commands;

import controller.AccountMenu;
import controller.HandleMenu;
import main.Main;

public class ViewPersonalInfoCommand extends Command {
    AccountMenu menu;

    public ViewPersonalInfoCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        this.menu = (AccountMenu) HandleMenu.getMenu();
        Main.print(menu.getAccount().toString());
        String string = Main.scan();
        if (string.equals("back")) {
            return null;
        }
        return "invalid command";
    }
}
