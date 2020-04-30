package commands;

import controller.ProductsMenu;
import main.Main;

import java.util.ArrayList;
import java.util.Scanner;

public class FilteringCommand extends Command {

    private static ArrayList<Command> filteringCommands = new ArrayList<Command>();

    public FilteringCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
       filteringCommands.add(new ShowAvailableFiltersCommand("$show available filters^"));
       filteringCommands.add(new Filter("$filter \\S+ \\S+ ?\\S+ ?\\S+ ?\\S+^"));
       filteringCommands.add(new CurrentFilter("$current filter^"));
       filteringCommands.add(new DisableFilter("$disable filter (?<filter name>\\S+)"));
        String input = Main.scan();
        for (Command filteringCommand : filteringCommands) {
            if (filteringCommand.checkCommand(input))
                 return filteringCommand.runCommand(input);
        }
        return "invalid command";
    }
}