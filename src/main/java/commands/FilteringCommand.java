package commands;

import controller.ProductsMenu;
import main.Main;

import java.util.ArrayList;
import java.util.Scanner;

public class FilteringCommand extends Command {

    private static ArrayList<Command> filteringCommands = new ArrayList<Command>();

    public FilteringCommand() {
        super.regex = "$filtering^";
    }

    @Override
    public String runCommand(String command) throws Exception {
       filteringCommands.add(new ShowAvailableFiltersCommand());
       filteringCommands.add(new Filter());
       filteringCommands.add(new CurrentFilter());
       filteringCommands.add(new DisableFilter());
        String input = Main.scan();
        for (Command filteringCommand : filteringCommands) {
            if (filteringCommand.checkCommand(input))
                 return filteringCommand.runCommand(input);
        }
        return "invalid command";
    }
}
