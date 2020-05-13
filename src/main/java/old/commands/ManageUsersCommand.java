package old.commands;

import old.HandleMenu;
import old.ManageUsersMenu;
import old.ManagerMenu;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersCommand extends Command {
    public ManageUsersCommand() {
        super.regex = "manage users";
    }

    @Override
    public String runCommand(String command) throws Exception {
        ManagerMenu menu = (ManagerMenu) HandleMenu.getMenu();
        ArrayList<SimpleAccount> accounts = menu.getAllAccounts();
        HandleMenu.setMenu(new ManageUsersMenu());
        String output = accounts.get(0).getUsername();
        for (int i = 1; i < accounts.size(); i++) {
            output += "\n" + accounts.get(i).getUsername();
        }
        return output;
    }
}
