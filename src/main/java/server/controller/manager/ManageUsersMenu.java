package server.controller.manager;

import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import common.model.account.SupportAccount;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class ManageUsersMenu {
    private boolean checkEmail(String email) throws IOException {
        for (ManagerAccount manager : YaDataManager.getManagers()) {
            if (manager.getEmail().equals(email)) {
                return true;
            }
        }
        for (PersonalAccount personalAccount : YaDataManager.getPersons()) {
            if (personalAccount.getEmail().equals(email))
                return true;
        }
        for (BusinessAccount reseller : YaDataManager.getBusinesses()) {
            if (reseller.getEmail().equals(email)) {
                return true;
            }
        }

        for (SupportAccount support : YaDataManager.getSupports()) {
            if (support.getEmail().equalsIgnoreCase(email))
                return true;
        }

        return false;
    }

    public void deleteUser(String username, String onlineAccount) throws Exception {

        if (onlineAccount.equalsIgnoreCase(username)) {
            throw new Exception("You cant delete your self.");
        }
        YaDataManager.deleteAccountWithUserName(username);
    }

    public void createNewSupport(SupportAccount supportAccount) throws Exception {
        if (YaDataManager.isUsernameExist(supportAccount.getUsername())) {
            throw new Exception("Invalid username");
        }
        if (checkEmail(supportAccount.getEmail())) {
            throw new Exception("This email is unavailable");
        }
        YaDataManager.addSupport(supportAccount);
    }
}
