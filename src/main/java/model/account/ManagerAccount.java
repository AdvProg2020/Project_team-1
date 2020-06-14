package model.account;

import javafx.scene.image.Image;
import model.exception.InvalidAccountInfoException;

public class ManagerAccount extends SimpleAccount {

    public ManagerAccount(String username, String firstName, String lastName, String email,
                          String phoneNumber, String password) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "manager");
    }

    public ManagerAccount(String username, String firstName, String lastName, String email,
                          String phoneNumber, String password, Image image) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "manager", image);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
