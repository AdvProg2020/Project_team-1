package controller;

import commands.Command;
import commands.LoginCommand;
import commands.LoginCommand;
import main.Main;
import model.Request;
import model.SuperMarket;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRegisterMenu extends ProductsMenu implements CommandProcess {

    private static ArrayList <Command> registerAndLoginCommands = new ArrayList<Command>();

    public void registerPersonalAccount(PersonalAccount personalAccount) {
        SuperMarket.addAccount(personalAccount);
    }

    public void sendBusinessAccountRequest(PersonalAccount personalAccount, BusinessAccount businessAccount) {
        SuperMarket.addRequest(new Request(businessAccount, personalAccount));
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
    public String commandProcessor(String command) {
        registerAndLoginCommands.add(new LoginCommand("$login (?<username>\\S+)"));
        for (Command registerAndLoginCommand : registerAndLoginCommands) {
            if (registerAndLoginCommand.checkCommand(command))
                return registerAndLoginCommand.runCommand(command);
        }
        return "invalid command";
    }
}
