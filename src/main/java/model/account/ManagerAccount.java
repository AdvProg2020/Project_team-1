package model.account;

public class ManagerAccount extends SimpleAccount {

    public ManagerAccount(String username, String firstName, String lastName, String email, String phoneNumber, String password) throws Exception {
        super(username, firstName, lastName, email, phoneNumber, password, "manager");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
