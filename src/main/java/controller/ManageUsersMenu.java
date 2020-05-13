package controller;

import old.CommandProcess;
import old.ProductsMenu;
import old.commands.Command;
import model.DataManager;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersMenu extends Menu {

    public SimpleAccount getAccountWithUsername(String username) throws Exception {
        ArrayList<SimpleAccount> allAccounts = DataManager.getAllAccounts();
        return DataManager.getAccountWithUsername(username);
    }

    public void deleteAccountWithUsername(String username) throws Exception {
        DataManager.getAllAccounts().remove(DataManager.getAccountWithUsername(username));
    }

    public void createNewManager(ManagerAccount managerAccount) {
        DataManager.addAccount(managerAccount);
    }


}
