package controller;

import commands.Command;
import model.SuperMarket;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersMenu extends ProductsMenu implements CommandProcess {
    private static ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
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
        for (Command managerCommand : commands) {
            if (managerCommand.checkCommand(command))
                return managerCommand.runCommand(command);
        }
        return "invalid command";
    }
}