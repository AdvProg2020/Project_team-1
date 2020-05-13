package old.commands;

import old.Filtering;
import controller.HandleMenu;

import java.util.ArrayList;

public class FilteringCommand extends Command {

    private static ArrayList<Command> filteringCommands = new ArrayList<Command>();

    public FilteringCommand() {
        super.regex = "$filtering^";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new Filtering());
        return "Enter your next Command";
    }
}
