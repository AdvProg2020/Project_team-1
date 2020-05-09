package commands;

import controller.CreateManagerMenu;
import controller.HandleMenu;

public class CreateManager extends Command {
    public CreateManager() {
        this.regex = "create manager profile";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new CreateManagerMenu());
        return "please enter username";
    }
}
