package controller;

import old.CommandProcess;
import old.ProductsMenu;
import old.commands.Command;
import model.DataManager;
import model.account.ManagerAccount;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManageUsersMenu extends Menu {

    public void createNewManager(ManagerAccount managerAccount) throws Exception {
        DataManager.addManagerAccount(managerAccount);
    }


}
