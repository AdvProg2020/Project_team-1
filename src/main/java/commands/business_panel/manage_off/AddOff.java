package commands.business_panel.manage_off;

import commands.Command;
import controller.HandleMenu;
import controller.business_menu.AddOffMenu;

public class AddOff extends Command {

    public AddOff() {
        super.regex = "^add off$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new AddOffMenu());
        return "";
    }
}
