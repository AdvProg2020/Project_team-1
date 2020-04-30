package controller;

import commands.Command;
import commands.ViewPersonalInfoCommand;
import model.SuperMarket;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.HashSet;

public class ManagerMenu extends AccountMenu implements CommandProcess {
    private static HashSet<Command> managerMenuCommands = new HashSet<>();

    public ManagerMenu(SimpleAccount account) {
        super(account);
    }

    public ArrayList<SimpleAccount> getAllAccounts() {
        return SuperMarket.getAllAccounts();
    }

    public SimpleAccount getAccountWithUsername(String username) throws Exception {
        ArrayList<SimpleAccount> allAccounts = SuperMarket.getAllAccounts();
        return SuperMarket.getAccountWithUsername(username);
    }

    public void deleteAccountWithUsername(String username) throws Exception {
        SuperMarket.getAllAccounts().remove(SuperMarket.getAccountWithUsername(username));
    }

    public void createNewManager(ManagerAccount managerAccount) {
        SuperMarket.addAccount(managerAccount);
    }

    public String commandProcessor(String command) throws Exception {
        managerMenuCommands.add(new ViewPersonalInfoCommand("view personal info"));
        for (Command registerAndLoginCommand : managerMenuCommands) {
            if (registerAndLoginCommand.checkCommand(command))
                return registerAndLoginCommand.runCommand(command);
        }
        return "invalid command";
    }

    @Override
    public String runCommand(String command) {
        return null;
    }
}
