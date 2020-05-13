package old.commands.get_info_to_purchase;

import old.commands.Command;
import old.HandleMenu;
import old.get_info_to_purchase.GetPhoneMenu;

public class GetAddress extends Command {
    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new GetPhoneMenu(command));
        return "please enter your phone number";
    }
}
