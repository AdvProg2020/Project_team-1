package controller;

import commands.Command;
import model.account.SimpleAccount;

import java.util.HashSet;

public abstract class AccountMenu extends ProductsMenu implements CommandProcess {
    private static HashSet<Command> managerMenuCommands = new HashSet<>();
    private SimpleAccount account;

    public AccountMenu(SimpleAccount account) {
        this.account = account;
    }

    public SimpleAccount getAccount() {
        return this.account;
    }

    public void editFirstName(String newFirstName) throws Exception {
        this.account.changeFirstName(newFirstName);
    }

    public void editLastName(String newLastName) throws Exception {
        this.account.changeLastName(newLastName);
    }

    public void editEmail(String newEmail) throws Exception {
        this.account.changeLastName(newEmail);
    }

    public void editPhoneNumber(String newPhoneNumber) throws Exception {
        this.account.changeLastName(newPhoneNumber);
    }
}