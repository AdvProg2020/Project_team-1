package controller.share;

import model.Session;
import controller.data.YaDataManager;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;
import model.share.Request;
import view.View;

public class LoginRegisterMenu extends Menu{

    public void checkUserNameAvailability(String username) throws Exception{
        if (YaDataManager.getAccountWithUserName(username) != null) {
            throw new Exception("this username is taken.");
        }
    }

    public void isThereManagerAccount() throws Exception{
        if (YaDataManager.getManagers().size() > 0) {
            throw new Exception("You can't create manager account. Contact holy manager to create manager account.");
        }
    }

    public void registerManagerAccount(String username, String firstName, String lastName, String email,
                                       String phoneNumber, String password) throws Exception{
        ManagerAccount newAccount = new ManagerAccount(username, firstName, lastName, email, phoneNumber, password);
        YaDataManager.addManager(newAccount);
    }

    public void registerPersonalAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password) throws Exception{
        PersonalAccount newAccount = new PersonalAccount(username, firstName, lastName, email, phoneNumber, password);
        YaDataManager.addPerson(newAccount);
    }

    public void registerResellerAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password, String businessName) throws Exception{
        BusinessAccount newAccount = new BusinessAccount(username, firstName, lastName, email,
                phoneNumber, password, businessName);
        Request request = new Request(newAccount, null);
        YaDataManager.addRequest(request);
    }

    public void login(String username, String password) throws Exception{
        SimpleAccount simpleAccount = YaDataManager.getAccountWithUserName(username);
        if (simpleAccount == null) {
            throw new Exception("Invalid username.");
        } else if (!simpleAccount.isPasswordCorrect(password)) {
            throw new Exception("Invalid password");
        }
        Session.setOnlineAccount(simpleAccount);
        if (simpleAccount instanceof ManagerAccount){
            MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
        }else if (simpleAccount instanceof  BusinessAccount){
            MenuHandler.getInstance().setCurrentMenu(View.resellerMenu);
        }else if (simpleAccount instanceof  PersonalAccount){
            MenuHandler.getInstance().setCurrentMenu(View.customerMenu);
        }
    }
}
