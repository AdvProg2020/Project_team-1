package old;

import old.commands.Command;
import model.DataManager;

import java.util.ArrayList;

public class ManageProductsMenu extends ProductsMenu implements CommandProcess {
    CommandProcess commandProcess;

    private static ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public void removeCommodityWithId(int id) throws Exception {
        DataManager.getAllCommodities().remove(DataManager.getCommodityById(id));
    }

    public String commandProcessor(String command) throws Exception {
        for (Command managerCommand : commands) {
            if (managerCommand.checkCommand(command))
                return managerCommand.runCommand(command);
        }
        return "invalid command";
    }
}
