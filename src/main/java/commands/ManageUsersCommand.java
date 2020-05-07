package commands;

import controller.HandleMenu;
import controller.ManageUsersMenu;
import controller.ManagerMenu;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersCommand extends Command {
    @Override
    public String runCommand(String command) throws Exception {
        ManagerMenu menu = (ManagerMenu) HandleMenu.getMenu();
        ArrayList<SimpleAccount> accounts = menu.getAllAccounts();
        HandleMenu.setMenu(new ManageUsersMenu());
        String output = accounts.get(0).getUsername();
        for (SimpleAccount account : accounts) {
            output += "\n" + account.getUsername();
        }
        return output;
    }
}
