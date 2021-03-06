package server.controller.share;

import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import common.model.share.Request;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class LoginRegisterMenu {
    public void checkUserNameAvailability(String username) throws InvalidLoginInformationException {
        try {
            if (YaDataManager.getAccountWithUserName(username) != null) {
                throw new InvalidLoginInformationException("This username is taken");
            }
            for (Request request : YaDataManager.getRequests()) {
                if (request.getObj() instanceof BusinessAccount
                        && ((BusinessAccount) request.getObj()).getUsername().equals(username)) {
                    throw new InvalidLoginInformationException("This username is taken");
                }
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
                                       String phoneNumber, String password, String imagePath) throws InvalidAccountInfoException {
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
    }

    public void setAccountId(SimpleAccount simpleAccount, String accountID) throws Exception {
        simpleAccount.setAccountID(accountID);
        YaDataManager.removePerson((PersonalAccount) simpleAccount);
        YaDataManager.addPerson((PersonalAccount) simpleAccount);
    }

    public void setAccountIDRequest(Request request, String accountID) throws IOException {
        ((BusinessAccount) request.getObj()).setAccountID(accountID);
        YaDataManager.removeRequest(request);
        YaDataManager.addRequest(request);
    }
}
