package controller.get_info_to_purchase;

import commands.Command;
import controller.CommandProcess;

import java.util.ArrayList;

public class GetDiscountCodeMenu implements CommandProcess {
    public static ArrayList<Command> commands = new ArrayList<Command>();
    private String address;
    private String phoneNumber;
    private String postalCode;

    public GetDiscountCodeMenu(String address, String phoneNumber, String postalCode) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
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
