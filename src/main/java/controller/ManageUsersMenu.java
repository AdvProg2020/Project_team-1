package controller;

import model.DataManager;
import model.Session;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;
import view.View;

import java.io.IOException;

public class ManageUsersMenu extends Menu {

    public void createNewManager(ManagerAccount managerAccount) throws Exception {
        DataManager.addManagerAccount(managerAccount);
    }

    public boolean checkEmail(String email) throws IOException {
        for (ManagerAccount manager : DataManager.getAllManagers()) {
            if (manager.getEmail().equals(email)){
                return true;
            }
        }
        for (PersonalAccount personalAccount : DataManager.getAllPersonalAccounts()) {
            if (personalAccount.getEmail().equals(email))
                return true;
        }
        for (BusinessAccount reseller : DataManager.getAllResellers()) {
            if (reseller.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public SimpleAccount getAccountWithUserNameFromDatabase(String username) throws IOException {
        return DataManager.getAccountWithUserName(username);
    }

    public void deleteUser(String username) throws Exception {
        DataManager.deleteAccountWithUserName(username);
        if (Session.getOnlineAccount().getUsername().equalsIgnoreCase(username)) {
            Session.setOnlineAccount(null);
            MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
        }
    }


}
