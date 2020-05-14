package controller;

import old.CommandProcess;
import old.ProductsMenu;
import old.commands.Command;
import model.DataManager;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.io.IOException;
import java.util.ArrayList;

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
