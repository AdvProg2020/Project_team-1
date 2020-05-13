package old;

import old.commands.Command;

import java.util.ArrayList;

public class GetDiscountCodes implements CommandProcess {
    private static ArrayList<Command> discountCodeCommands = new ArrayList<Command>();

    public static ArrayList<Command> getDiscountCodeCommands() {
        return discountCodeCommands;
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command discountCodeCommand : discountCodeCommands) {
            if (discountCodeCommand.checkCommand(command))
                return discountCodeCommand.runCommand(command);
        }
        return "invalid command";
    }
    CommandProcess commandProcess;

}