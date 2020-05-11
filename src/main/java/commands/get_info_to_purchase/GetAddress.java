package commands.get_info_to_purchase;

import commands.Command;
import controller.HandleMenu;
import controller.get_info_to_purchase.GetPhoneMenu;

public class GetAddress extends Command {
    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new GetPhoneMenu(command));
        return "please enter your phone number";
    }
}
