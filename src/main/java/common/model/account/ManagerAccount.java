package common.model.account;

import common.model.exception.InvalidAccountInfoException;

public class ManagerAccount extends SimpleAccount {

    public ManagerAccount(String username, String firstName, String lastName, String email,
                          String phoneNumber, String password) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "manager");
    }

    public ManagerAccount(String username, String firstName, String lastName, String email,
                          String phoneNumber, String password, String imagePath) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "manager", imagePath);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
