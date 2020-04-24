package controller;

import main.Main;
import model.Request;
import model.SuperMarket;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRegisterMenu extends ProductsMenu implements CommandProcess {
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
    public void commandProcessor(String command) {
        if (command.startsWith("login ")) {
            Matcher matcher = Pattern.compile("login (?<username>\\S+)").matcher(command);
            if (matcher.matches()) {
                if (isUserNameValid(matcher.group("username"))) {
                    String password = Main.getConsoleScanner().nextLine();
                    login(matcher.group("username"), password);
                }
            }
        }
    }
}
