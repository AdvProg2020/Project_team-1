package commands;

import controller.Filtering;
import controller.HandleMenu;
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
        HandleMenu.setMenu(new Filtering());
        return "Enter your next Command";
    }
}
