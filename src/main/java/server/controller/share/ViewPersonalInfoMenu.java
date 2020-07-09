package server.controller.share;

import client.Session;
import server.data.YaDataManager;
import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;

public class ViewPersonalInfoMenu extends Menu{
    public void editFirstName(String newFirstName, SimpleAccount account) throws Exception {
        account.changeFirstName(newFirstName);
        updateFile();
    }

    public void editLastName(String newLastName, SimpleAccount account) throws Exception {
        account.changeLastName(newLastName);
        updateFile();
    }

    public void editEmail(String newEmail, SimpleAccount account) throws Exception {
        account.changeEmail(newEmail);
        updateFile();
    }

    public void editPassword(String newPassword, SimpleAccount account) throws Exception {
        account.changePassword(newPassword);
        updateFile();
    }
    public void editPhoneNumber(String newPhoneNumber, SimpleAccount account) throws Exception {
        account.changePhoneNumber(newPhoneNumber);
        updateFile();
    }

    public void updateFile() throws Exception {
        if (Session.getOnlineAccount() instanceof BusinessAccount){
            YaDataManager.deleteAccountWithUserName(Session.getOnlineAccount().getUsername());
            YaDataManager.addBusiness((BusinessAccount) Session.getOnlineAccount());
        }
        if (Session.getOnlineAccount() instanceof PersonalAccount){
            YaDataManager.deleteAccountWithUserName(Session.getOnlineAccount().getUsername());
            YaDataManager.addPerson((PersonalAccount) Session.getOnlineAccount());
        }
        if (Session.getOnlineAccount() instanceof ManagerAccount){
            YaDataManager.deleteAccountWithUserName(Session.getOnlineAccount().getUsername());
            YaDataManager.addManager((ManagerAccount) Session.getOnlineAccount());
        }
    }

    public ViewPersonalInfoMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/ViewPersonalInfo.fxml";
    }
}
