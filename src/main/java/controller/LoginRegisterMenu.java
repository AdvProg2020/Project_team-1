package controller;

import model.DataManager;
import model.Session;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import javax.xml.crypto.Data;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRegisterMenu extends Menu{

    public void checkUserNameAvailability(String username) throws Exception{
        if (DataManager.getAccountWithUserName(username) != null) {
            throw new Exception("this username is taken.");
        }
    }

    public void isThereManagerAccount() throws Exception{
        if (DataManager.getAllManagers().length == 0) {
            throw new Exception("You can't create manager account. Contact holy manager to create manager account.");
        }
    }

    public void registerManagerAccount() throws Exception{

    }

    public void registerPersonalAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password) throws Exception{
        PersonalAccount newAccount = new PersonalAccount(username, firstName, lastName, email, phoneNumber, password);
        DataManager.addPersonalAccount(newAccount);
    }

    public void registerResellerAccount() throws Exception{

    }

    public void login(String username, String password) throws Exception{
        SimpleAccount simpleAccount = DataManager.getAccountWithUserName(username);
        if (simpleAccount == null) {
            throw new Exception("Invalid username.");
        } else if (!simpleAccount.isPasswordCorrect(password)) {
            throw new Exception("Invalid password");
        }
        Session.setSimpleAccount(simpleAccount);
    }
}
