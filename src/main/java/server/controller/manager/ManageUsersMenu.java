package server.controller.manager;

import server.controller.share.Menu;
import server.controller.share.MenuHandler;
import client.Session;
import server.data.YaDataManager;
import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import client.view.commandline.View;

import java.io.IOException;

public class ManageUsersMenu extends Menu {

    public void createNewManager(ManagerAccount managerAccount) throws Exception {
        if (YaDataManager.isUsernameExist(managerAccount.getUsername())) {
            throw new Exception("Invalid username");
        }
        if (checkEmail(managerAccount.getEmail())) {
            throw new Exception("This email is unavailable");
        }
        YaDataManager.addManager(managerAccount);
    }

    public ManageUsersMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/ManageUsers.fxml";
    }

    public boolean checkEmail(String email) throws IOException {
        for (ManagerAccount manager : YaDataManager.getManagers()) {
            if (manager.getEmail().equals(email)){
                return true;
            }
        }
        for (PersonalAccount personalAccount : YaDataManager.getPersons()) {
            if (personalAccount.getEmail().equals(email))
                return true;
        }
        for (BusinessAccount reseller : YaDataManager.getBusinesses()) {
            if (reseller.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public SimpleAccount getAccountWithUserNameFromDatabase(String username) throws IOException {
        return YaDataManager.getAccountWithUserName(username);
    }

    public void deleteUser(String username) throws Exception {
        YaDataManager.deleteAccountWithUserName(username);
        if (Session.getOnlineAccount().getUsername().equalsIgnoreCase(username)) {
            Session.setOnlineAccount(null);
            MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
        }
    }


}
