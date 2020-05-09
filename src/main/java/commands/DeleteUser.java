package commands;

import controller.HandleMenu;
import controller.ManageUsersMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteUser extends Command {
    public DeleteUser() {
        super.regex = "delete (?<username>\\S+)";
    }

    @Override
    public String runCommand(String command) {
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        ManageUsersMenu menu = (ManageUsersMenu) HandleMenu.getMenu();
        try {
            menu.deleteAccountWithUsername(matcher.group("username"));
        } catch (Exception e) {
            return e.getMessage();
        }
        return "user deleted successfully";
    }
}
