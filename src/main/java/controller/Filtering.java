package controller;

import commands.*;

import java.util.ArrayList;

public class Filtering implements CommandProcess {
    public static ArrayList<Command> filteringCommands = new ArrayList<Command>();
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command filteringCommand : filteringCommands) {
            if (filteringCommand.checkCommand(command))
                return filteringCommand.runCommand(command);
        }
        return "invalid command";
    }

}
