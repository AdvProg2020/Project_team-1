package old.get_info_to_purchase;

import old.commands.Command;
import old.CommandProcess;

import java.util.ArrayList;

public class GetAddressMenu implements CommandProcess {
    public static ArrayList<Command> commands = new ArrayList<Command>();
    CommandProcess commandProcess;
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command filteringCommand : commands) {
            if (filteringCommand.checkCommand(command))
                return filteringCommand.runCommand(command);
        }
        return "invalid command";
    }
}
