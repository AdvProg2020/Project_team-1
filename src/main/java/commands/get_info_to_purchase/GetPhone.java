package commands.get_info_to_purchase;

import commands.Command;
import controller.HandleMenu;
import controller.get_info_to_purchase.GetPhoneMenu;
import controller.get_info_to_purchase.GetPostalCodeMenu;

public class GetPhone extends Command {
    public GetPhone() {
        this.regex = "0\\d{10}";
    }

    @Override
    public String runCommand(String command) throws Exception {
        GetPhoneMenu menu = (GetPhoneMenu) HandleMenu.getMenu();
        String address = menu.getAddress();
        HandleMenu.setMenu(new GetPostalCodeMenu(address, command));
        return "please enter your postal code";
    }
}
