package controller;

import commands.Command;
import commands.get_info_to_reg.GetAccountInformation;

import java.util.ArrayList;

public class GetAccountInfoMenu extends ProductsMenu implements CommandProcess {
    public static ArrayList<Command> getInfoCommands = new ArrayList<>();
    private String username;
    private String role;

    public GetAccountInfoMenu(String username, String role) {
        this.username = username;
        this.role = role;
        getInfoCommands.add(new GetAccountInformation(this.username, this.role));
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command getInfoCommand : getInfoCommands) {
            if (getInfoCommand.checkCommand(command))
                return getInfoCommand.runCommand(command);
        }
        return "invalid command";
    }
}
