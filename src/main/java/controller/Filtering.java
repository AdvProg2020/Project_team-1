package controller;

import commands.*;

import java.util.ArrayList;

public class Filtering implements CommandProcess {
    public static ArrayList<Command> filteringCommands = new ArrayList<Command>();
    @Override
    public String commandProcessor(String command) throws Exception {
        HandleMenu.setMenu(new Filtering());
        return "Enter your next Command";
    }

}
