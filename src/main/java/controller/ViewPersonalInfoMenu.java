package controller;

import commands.Command;
import model.account.SimpleAccount;

import java.util.HashSet;

public class ViewPersonalInfoMenu extends ProductsMenu implements CommandProcess {
    private static HashSet<Command> commands = new HashSet<>();
    private SimpleAccount account;

    public ViewPersonalInfoMenu(SimpleAccount account) {
        this.account = account;
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

    @Override
    public String runCommand(String command) {

    }
}
