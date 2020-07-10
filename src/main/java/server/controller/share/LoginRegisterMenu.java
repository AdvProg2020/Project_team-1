package server.controller.share;

import client.Session;
import common.model.account.*;
import server.data.YaDataManager;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import common.model.share.Request;
import client.view.commandline.View;

import java.io.IOException;

public class LoginRegisterMenu extends Menu{

    public void checkUserNameAvailability(String username) throws InvalidLoginInformationException {
        try {
            if (YaDataManager.getAccountWithUserName(username) != null) {
                throw new InvalidLoginInformationException("This username is taken");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void isThereManagerAccount() throws InvalidAccessException {
        try {
            if (YaDataManager.getManagers().size() > 0) {
                throw new InvalidAccessException("You can't create manager account. Contact holy manager to create manager account");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerManagerAccount(String username, String firstName, String lastName, String email,
                                       String phoneNumber, String password) throws InvalidAccountInfoException{
        ManagerAccount newAccount = new ManagerAccount(username, firstName, lastName, email, phoneNumber, password);
        try {
            YaDataManager.addManager(newAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerPersonalAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password) throws InvalidAccountInfoException {
        PersonalAccount newAccount = new PersonalAccount(username, firstName, lastName, email, phoneNumber, password);
        try {
            YaDataManager.addPerson(newAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerResellerAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password, String businessName) throws InvalidAccountInfoException {
        BusinessAccount newAccount = new BusinessAccount(username, firstName, lastName, email,
                phoneNumber, password, businessName);
        Request request;
        try {
            request = new Request(newAccount, null);
            YaDataManager.addRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerManagerAccount(String username, String firstName, String lastName, String email,
                                       String phoneNumber, String password, String imagePath) throws InvalidAccountInfoException{
        ManagerAccount newAccount = new ManagerAccount(username, firstName, lastName, email, phoneNumber, password, imagePath);
        try {
            YaDataManager.addManager(newAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerPersonalAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password, String imagePath) throws InvalidAccountInfoException {
        PersonalAccount newAccount = new PersonalAccount(username, firstName, lastName, email, phoneNumber, password, imagePath);
        try {
            YaDataManager.addPerson(newAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerResellerAccount(String username, String firstName, String lastName, String email,
                                        String phoneNumber, String password, String businessName, String imagePath)
                                        throws InvalidAccountInfoException {
        BusinessAccount newAccount = new BusinessAccount(username, firstName, lastName, email,
                phoneNumber, password, businessName, imagePath);
        Request request;
        try {
            request = new Request(newAccount, null);
            YaDataManager.addRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoginRegisterMenu() {
        fxmlFileAddress = "../../../fxml/LoginRegister.fxml";
        stageTitle = "Login or Register";
    }

    public void login(String username, String password) throws InvalidLoginInformationException {
        SimpleAccount simpleAccount = null;
        try {
            simpleAccount = YaDataManager.getAccountWithUserName(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (simpleAccount == null) {
            throw new InvalidLoginInformationException("Invalid username.");
        } else if (!simpleAccount.isPasswordCorrect(password)) {
            throw new InvalidLoginInformationException("Invalid password");
        }
        Session.setOnlineAccount(simpleAccount);
        if (simpleAccount instanceof ManagerAccount){
            MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
        }else if (simpleAccount instanceof  BusinessAccount){
            MenuHandler.getInstance().setCurrentMenu(View.resellerMenu);
        }else if (simpleAccount instanceof  PersonalAccount){
            MenuHandler.getInstance().setCurrentMenu(View.customerMenu);
        }else if (simpleAccount instanceof SupportAccount){
            MenuHandler.getInstance().setCurrentMenu(View.supportMenu);
        }
    }
}
