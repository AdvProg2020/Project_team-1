package controller;

import commands.Command;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class PersonalAccountMenu extends AccountMenu implements CommandProcess {

    public static ArrayList<Command> personalAccountMenuCommands = new ArrayList<Command>();

    public PersonalAccountMenu(SimpleAccount account) {
        super(account);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command personalAccountMenuCommand : personalAccountMenuCommands) {
            if (personalAccountMenuCommand.checkCommand(command))
                return personalAccountMenuCommand.runCommand(command);
        }
        return "invalid command";
    }
}
