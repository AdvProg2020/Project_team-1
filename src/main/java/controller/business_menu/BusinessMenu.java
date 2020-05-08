package controller.business_menu;

import commands.Command;
import controller.AccountMenu;
import controller.CommandProcess;
import model.account.SimpleAccount;

import java.util.HashSet;

public class BusinessMenu extends AccountMenu implements CommandProcess {

    private static HashSet<Command> commands = new HashSet<>();

    public BusinessMenu(SimpleAccount account) {
        super(account);
    }

    @Override
    public String commandProcessor(String inputCommand) throws Exception {
        for (Command command : commands) {
            if (command.checkCommand(inputCommand))
                return command.runCommand(inputCommand);
        }
        return "invalid command";
    }

}
