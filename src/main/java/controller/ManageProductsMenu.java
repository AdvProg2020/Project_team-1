package controller;

import commands.Command;
import model.SuperMarket;

import java.util.ArrayList;

public class ManageProductsMenu extends ProductsMenu implements CommandProcess {
    private static ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public void removeCommodityWithId(int id) throws Exception {
        SuperMarket.getAllCommodities().remove(SuperMarket.getCommodityById(id));
    }

    public String commandProcessor(String command) throws Exception {
        for (Command managerCommand : commands) {
            if (managerCommand.checkCommand(command))
                return managerCommand.runCommand(command);
        }
        return "invalid command";
    }
}
