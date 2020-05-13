package old;

import old.commands.Command;
import model.DataManager;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersMenu extends ProductsMenu implements CommandProcess {
    CommandProcess commandProcess;

    private static ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public SimpleAccount getAccountWithUsername(String username) throws Exception {
        ArrayList<SimpleAccount> allAccounts = DataManager.getAllAccounts();
        return DataManager.getAccountWithUsername(username);
    }

    public void deleteAccountWithUsername(String username) throws Exception {
        DataManager.getAllAccounts().remove(DataManager.getAccountWithUsername(username));
    }

    public void createNewManager(ManagerAccount managerAccount) {
        DataManager.addAccount(managerAccount);
    }

    public String commandProcessor(String command) throws Exception {
        for (Command managerCommand : commands) {
            if (managerCommand.checkCommand(command))
                return managerCommand.runCommand(command);
        }
        return "invalid command";
    }
}
