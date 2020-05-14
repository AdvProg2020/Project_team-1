package controller;

import model.DataManager;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.io.IOException;

public class ManageUsersMenu extends Menu {

    public void createNewManager(ManagerAccount managerAccount) throws Exception {
        DataManager.addManagerAccount(managerAccount);
    }

    public SimpleAccount getAccountWithUserNameFromDatabase(String username) throws IOException {
        return DataManager.getAccountWithUserName(username);
    }
    public void deleteUser(String username) throws Exception{
        DataManager.deleteAccountWithUserName(username);
    }


}
