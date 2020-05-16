package controller;

import model.DataManager;
import model.Request;
import model.Session;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;
import view.View;

public class LoginRegisterMenu extends Menu{

    public void checkUserNameAvailability(String username) throws Exception{
        if (DataManager.getAccountWithUserName(username) != null) {
            throw new Exception("this username is taken.");
        }
    }

    public void isThereManagerAccount() throws Exception{
        if (DataManager.getAllManagers().length > 0) {
            throw new Exception("You can't create manager account. Contact holy manager to create manager account.");
        }
    }

    public void registerManagerAccount(String username, String firstName, String lastName, String email,
                                       String phoneNumber, String password) throws Exception{
        ManagerAccount newAccount = new ManagerAccount(username, firstName, lastName, email, phoneNumber, password);
        DataManager.addManagerAccount(newAccount);
    }

    public void registerPersonalAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password) throws Exception{
        PersonalAccount newAccount = new PersonalAccount(username, firstName, lastName, email, phoneNumber, password);
        DataManager.addPersonalAccount(newAccount);
    }

    public void registerResellerAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password, String businessName) throws Exception{
        BusinessAccount newAccount = new BusinessAccount(username, firstName, lastName, email,
                phoneNumber, password, businessName);
        DataManager.addResellerAccount(newAccount);
    }

    public void login(String username, String password) throws Exception{
        SimpleAccount simpleAccount = DataManager.getAccountWithUserName(username);
        if (simpleAccount == null) {
            throw new Exception("Invalid username.");
        } else if (!simpleAccount.isPasswordCorrect(password)) {
            throw new Exception("Invalid password");
        }
        Session.setOnlineAccount(simpleAccount);
        if (simpleAccount instanceof ManagerAccount){
            MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
        }else if (simpleAccount instanceof  BusinessAccount){
            MenuHandler.getInstance().setCurrentMenu(View.resellerMenu);
        }else if (simpleAccount instanceof  PersonalAccount){
            MenuHandler.getInstance().setCurrentMenu(View.customerMenu);
        }
    }
}
