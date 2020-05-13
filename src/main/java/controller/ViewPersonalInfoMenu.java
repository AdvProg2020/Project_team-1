package controller;

import model.account.ManagerAccount;

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

}
