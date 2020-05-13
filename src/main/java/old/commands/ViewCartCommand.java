package old.commands;

import old.GetCartMenu;
import controller.HandleMenu;

public class ViewCartCommand extends Command {
    public ViewCartCommand() {
        super.regex = "^view cart$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new GetCartMenu());
        return "Enter your next command";
    }
}
