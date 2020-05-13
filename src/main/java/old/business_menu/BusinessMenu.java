package old.business_menu;

import old.commands.Command;
import old.AccountMenu;
import old.CommandProcess;
import model.account.SimpleAccount;

import java.util.HashSet;

public class BusinessMenu extends AccountMenu implements CommandProcess {
    CommandProcess commandProcess;
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
