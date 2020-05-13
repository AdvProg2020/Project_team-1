package old.commands;

import old.HandleMenu;
import old.ManageUsersMenu;
import model.account.SimpleAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewUser extends Command {
    public ViewUser() {
        super.regex = "view (?<username>\\S+)";
    }

    @Override
    public String runCommand(String command) {
        ManageUsersMenu menu = (ManageUsersMenu) HandleMenu.getMenu();
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        SimpleAccount account;
        try {
            account = menu.getAccountWithUsername(matcher.group("username"));
        } catch (Exception e) {
            return e.getMessage();
        }
        return account.getInformation();
    }
}