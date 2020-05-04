package controller;

import commands.Command;

import java.util.ArrayList;

public class ManageRequestMenu implements CommandProcess {
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
