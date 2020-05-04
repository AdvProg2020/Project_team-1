package controller;

import commands.Command;
import commands.LoginCommand;
import commands.RegisterCommand;
import model.Request;
import model.SuperMarket;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.util.HashSet;

public class LoginRegisterMenu extends ProductsMenu implements CommandProcess {

    private static HashSet<Command> registerAndLoginCommands = new HashSet<>();

    public void registerPersonalAccount(PersonalAccount personalAccount) {
        SuperMarket.addAccount(personalAccount);
    }

    public void sendBusinessAccountRequest(PersonalAccount personalAccount, BusinessAccount businessAccount) {
        SuperMarket.addRequest(new Request(businessAccount, personalAccount  ));
    }

    public boolean isUserNameValid(String userName) {
        for (SimpleAccount account : SuperMarket.getAllAccounts()) {
            if (account.getUsername().equals(userName))
                return true;
        }
        return false;
    }

    public void login(String userName, String password) {
        SimpleAccount onlineAccount = SuperMarket.getAccountByUserAndPassword(userName, password);
        SuperMarket.setOnlineAccount(onlineAccount);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        registerAndLoginCommands.add(new LoginCommand());
        registerAndLoginCommands.add(new RegisterCommand());
        for (Command registerAndLoginCommand : registerAndLoginCommands) {
            if (registerAndLoginCommand.checkCommand(command))
                return registerAndLoginCommand.runCommand(command);
        }
        return "invalid command";
    }
}
