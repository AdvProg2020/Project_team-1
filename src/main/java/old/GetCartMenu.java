package old;

import old.commands.Command;

import java.util.ArrayList;

public class GetCartMenu implements CommandProcess {
    public static ArrayList<Command> getCartMenuCommands = new ArrayList<Command>();
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command getCartMenuCommand : getCartMenuCommands) {
            if (getCartMenuCommand.checkCommand(command))
                return   getCartMenuCommand.runCommand(command);
        }
        return "invalid command";
    }
    CommandProcess commandProcess;

}
