package controller;


import commands.Command;

import java.util.ArrayList;

public class ManageCategoryMenu implements CommandProcess {
    public static ArrayList<Command> manageCategoryMenuCommands = new ArrayList<Command>();
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command manageCategoryMenuCommand : manageCategoryMenuCommands) {
            if (manageCategoryMenuCommand.checkCommand(command))
               return manageCategoryMenuCommand.runCommand(command);
        }
        return "invalid command";
    }
}
