package server.controller.share;


import common.model.account.*;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class ViewPersonalInfoMenu extends Menu{
    public void editFirstName(String newFirstName, SimpleAccount account) throws Exception {
        account.changeFirstName(newFirstName);
        updateFile(account);
    }

    public void editLastName(String newLastName, SimpleAccount account) throws Exception {
        account.changeLastName(newLastName);
        updateFile(account);
    }

    public void editEmail(String newEmail, SimpleAccount account) throws Exception {
        if (!isEmailValid(newEmail))
            throw new Exception("Email is not available");
        account.changeEmail(newEmail);
        updateFile(account);
    }

    public void editPassword(String newPassword, SimpleAccount account) throws Exception {
        account.changePassword(newPassword);
        updateFile(account);
    }
    public void editPhoneNumber(String newPhoneNumber, SimpleAccount account) throws Exception {
        account.changePhoneNumber(newPhoneNumber);
        updateFile(account);
    }

    public void updateFile(SimpleAccount simpleAccount) throws Exception {
        System.out.println(simpleAccount.getFirstName());
        if (simpleAccount instanceof BusinessAccount){
            YaDataManager.deleteAccountWithUserName(simpleAccount.getUsername());
            YaDataManager.addBusiness((BusinessAccount) simpleAccount);
        }
        if (simpleAccount instanceof PersonalAccount){
            YaDataManager.deleteAccountWithUserName(simpleAccount.getUsername());
            YaDataManager.addPerson((PersonalAccount) simpleAccount);
        }
        if (simpleAccount instanceof ManagerAccount){
            YaDataManager.deleteAccountWithUserName(simpleAccount.getUsername());
            YaDataManager.addManager((ManagerAccount) simpleAccount);
        }
    }

    public boolean isEmailValid(String newEmail) throws IOException {
        for (PersonalAccount person : YaDataManager.getPersons()) {
            if (person.getEmail().equals(newEmail))
                return false;
        }
        for (ManagerAccount manager : YaDataManager.getManagers()) {
            if (manager.getEmail().equals(newEmail))
                return false;
        }
        for (BusinessAccount businessAccount : YaDataManager.getBusinesses()) {
            if (businessAccount.getEmail().equals(newEmail))
                return false;
        }
        for (SupportAccount supportAccount : YaDataManager.getSupports()) {
            if (supportAccount.getEmail().equals(newEmail))
                return false;
        }

        return true;

    }

    public ViewPersonalInfoMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/ViewPersonalInfo.fxml";
    }
}
