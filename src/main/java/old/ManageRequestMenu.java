package old;

import old.commands.Command;

import java.util.ArrayList;

public class ManageRequestMenu implements CommandProcess {
    CommandProcess commandProcess;

    public static ArrayList<Command> manageRequestMenuCommands = new ArrayList<Command>();
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command manageRequestMenuCommand : manageRequestMenuCommands) {
            if (manageRequestMenuCommand.checkCommand(command))
               return manageRequestMenuCommand.runCommand(command);
        }
        return "invalid command";
    }
}
