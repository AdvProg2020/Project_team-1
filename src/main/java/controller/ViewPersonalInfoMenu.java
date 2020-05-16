package controller;

import model.DataManager;
import model.Session;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;

public class ViewPersonalInfoMenu extends Menu{
    public void editFirstName(String newFirstName, ManagerAccount account) throws Exception {
        account.changeFirstName(newFirstName);
    }

    public void editLastName(String newLastName, ManagerAccount account) throws Exception {
        account.changeLastName(newLastName);
    }

    public void editEmail(String newEmail, ManagerAccount account) throws Exception {
        account.changeEmail(newEmail);
    }

    public void editPassword(String newPassword, ManagerAccount account) throws Exception {
        account.changePassword(newPassword);
    }
    public void editPhoneNumber(String newPhoneNumber, ManagerAccount account) throws Exception {
        account.changePhoneNumber(newPhoneNumber);
    }

    public void updateFile() throws Exception {
        if (Session.getOnlineAccount() instanceof BusinessAccount){
            DataManager.deleteAccountWithUserName(Session.getOnlineAccount().getUsername());
            DataManager.addResellerAccount((BusinessAccount) Session.getOnlineAccount());
        }
        if (Session.getOnlineAccount() instanceof PersonalAccount){
            DataManager.deleteAccountWithUserName(Session.getOnlineAccount().getUsername());
            DataManager.addPersonalAccount((PersonalAccount) Session.getOnlineAccount());
        }
        if (Session.getOnlineAccount() instanceof ManagerAccount){
            DataManager.deleteAccountWithUserName(Session.getOnlineAccount().getUsername());
            DataManager.addManagerAccount((ManagerAccount) Session.getOnlineAccount());
        }
    }

}
