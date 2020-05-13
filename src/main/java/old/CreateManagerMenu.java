package old;

import old.commands.Command;
import old.commands.get_info_to_reg.GetUsername;

import java.util.ArrayList;

public class CreateManagerMenu extends ProductsMenu implements CommandProcess {
    public static ArrayList<Command> allCommands = new ArrayList<>();

    public CreateManagerMenu() {
        allCommands.add(new GetUsername());
    }
    CommandProcess commandProcess;

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command getUsername : allCommands) {
            if (getUsername.checkCommand(command))
                return getUsername.runCommand(command);
        }
        return "invalid command";
    }
}
