package controller.get_info_to_purchase;

import commands.Command;
import controller.CommandProcess;

import java.util.ArrayList;

public class GetPostalCodeMenu implements CommandProcess {
    public static ArrayList<Command> commands = new ArrayList<Command>();
    private String address;
    private String phoneNumber;

    public GetPostalCodeMenu(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command filteringCommand : commands) {
            if (filteringCommand.checkCommand(command))
                return filteringCommand.runCommand(command);
        }
        return "invalid command";
    }
}
