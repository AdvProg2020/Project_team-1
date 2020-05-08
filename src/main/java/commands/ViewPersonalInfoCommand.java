package commands;

import controller.AccountMenu;
import controller.HandleMenu;
import controller.ViewPersonalInfoMenu;
import model.SuperMarket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewPersonalInfoCommand extends Command {
    AccountMenu menu;

    public ViewPersonalInfoCommand() {
        super.regex = "^view personal info$";
    }

    @Override
    public String runCommand(String command) {
        this.menu = (AccountMenu) HandleMenu.getMenu();
        HandleMenu.setMenu(new ViewPersonalInfoMenu(SuperMarket.getOnlineAccount()));
        return menu.getAccount().getInformation();
    }

    public boolean checkCommand(String command) {
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        return matcher.matches();
    }
}
