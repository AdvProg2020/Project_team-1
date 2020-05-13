package old.get_info_to_purchase;

import old.commands.Command;
import old.CommandProcess;

import java.util.ArrayList;

public class GetPostalCodeMenu implements CommandProcess {

    public static ArrayList<Command> commands = new ArrayList<Command>();
    private String address;
    private String phoneNumber;
    CommandProcess commandProcess;

    public GetPostalCodeMenu(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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
