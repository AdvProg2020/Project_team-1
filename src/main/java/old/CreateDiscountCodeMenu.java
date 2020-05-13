package old;

import old.commands.Command;

import java.util.ArrayList;

public class CreateDiscountCodeMenu extends ProductsMenu implements CommandProcess {
    private static ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }
    public CommandProcess commandProcess1;
    private ProductsMenu productsMenu
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command command1 : commands) {
            if (command1.checkCommand(command))
                return command1.runCommand(command);
        }

        return "invalid input";
    }




}
