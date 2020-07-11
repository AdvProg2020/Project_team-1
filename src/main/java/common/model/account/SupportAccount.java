package common.model.account;

import common.model.exception.InvalidAccountInfoException;

public class SupportAccount extends SimpleAccount{
    public SupportAccount(String username, String firstName, String lastName, String email, String phoneNumber, String password, String imagePath) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password,"support", imagePath);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
