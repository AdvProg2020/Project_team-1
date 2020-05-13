package controller;

import commands.Command;
import model.Commodity;

import java.util.ArrayList;

public class DigestMenu implements CommandProcess  {
    public static ArrayList<Command> getDigestMenuCommand() {
        return digestMenuCommand;
    }

    private static ArrayList<Command> digestMenuCommand = new ArrayList<Command>();
    Commodity commodity;

    public DigestMenu(Commodity commodity) {
        this.commodity = commodity;
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command digestMenuCommand : digestMenuCommand) {
            if (digestMenuCommand.checkCommand(command))
                return digestMenuCommand.runCommand(command);
        }
        return "invalid command";
    }
}
