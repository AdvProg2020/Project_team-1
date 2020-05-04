package controller;

import model.SuperMarket;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersMenu extends ProductsMenu implements CommandProcess {
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

}
