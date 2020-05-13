package old.commands.business_panel.manage_off;

import old.commands.Command;
import controller.HandleMenu;
import old.business_menu.AddOffMenu;

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
