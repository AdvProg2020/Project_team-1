package controller;

import commands.Command;

import java.util.ArrayList;

public class CreateDiscountCodeMenu implements CommandProcess {
    private static ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command command1 : commands) {
            if (command1.checkCommand(command))
                return command1.runCommand(command);
        }

        return "invalid input";
    }

    @Override
    public String runCommand(String command) {
        return null;
    }


}
