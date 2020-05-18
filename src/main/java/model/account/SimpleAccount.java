package model.account;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public abstract class SimpleAccount {
    protected final transient String VALID_USERNAME = "^\\w{2,20}$";
    protected final transient String VALID_FIRST_NAME_AND_LAST_NAME = "^[a-zA-z ]{1,20}$";
    protected final transient String VALID_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    protected final transient String VALID_PHONE_NUMBER = "^(/+98|0098|0)\\d{10}$";
    protected final transient String VALID_PASSWORD = "^.{4,20}$";
    // Todo test password strength
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String hashedPassword;
    private String accountType;

    public SimpleAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                         String password, String accountType) throws Exception {
        changeUsername(username);
        changeFirstName(firstName);
        changeLastName(lastName);
        changeEmail(email);
        changePhoneNumber(phoneNumber);
        changePassword(password);
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getUsername() {
        return username;
    }

    public void changeUsername(String username) throws Exception {
        if (username.matches(VALID_USERNAME)) {
            this.username = username;
        } else {
            throw new Exception("Invalid username. User name just contain 4 to 10 alphanumerical characters.");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void changeFirstName(String firstName) throws Exception {
        if (firstName.matches(VALID_FIRST_NAME_AND_LAST_NAME)) {
            this.firstName = firstName;
        } else {
            throw new Exception("Invalid first name. First name just contain alphabetical characters.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void changeLastName(String lastName) throws Exception {
        if (lastName.matches(VALID_FIRST_NAME_AND_LAST_NAME)) {
            this.lastName = lastName;
        } else {
            throw new Exception("Invalid last name. Last name just contain alphabetical characters.");
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void changeEmail(String email) throws Exception {
        if (email.matches(VALID_EMAIL)) {
            this.email = email;
        } else {
            throw new Exception("Invalid email address.");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void changePhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber.matches(VALID_PHONE_NUMBER)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new Exception("Invalid Iran phone number.");
        }
    }

    public boolean isPasswordCorrect(String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public void changePassword(String password) throws Exception {
        if (password.matches(VALID_PASSWORD)) {
            this.hashedPassword = hashPassword(password);
        } else {
            throw new Exception("Invalid password.");
        }
    }

    @Override
    public String toString() {
        return "SimpleAccount{" +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }

    protected String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public String getInformation() {
        return "username: " + this.username + "\n" +
                "first name: " + this.firstName + "\n" +
                "last name: " + this.lastName + "\n" +
                "email: " + this.email + "\n" +
                "phone number: " + this.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleAccount)) return false;
        SimpleAccount account = (SimpleAccount) o;
        return Objects.equals(getUsername(), account.getUsername());
    }
}
