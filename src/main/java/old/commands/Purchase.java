package old.commands;

import old.HandleMenu;
import old.get_info_to_purchase.GetAddressMenu;

public class Purchase extends Command {
    public Purchase() {
        this.regex = "purchase";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new GetAddressMenu());
        return "please enter your address";
    }
}
