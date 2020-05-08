package commands;

import controller.CreateManagerMenu;
import controller.HandleMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateManager extends Command {
    public CreateManager() {
        this.regex = "create manager profile";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new CreateManagerMenu());
        return "please enter username";
    }

    public boolean checkCommand(String command) {
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        return matcher.matches();
    }
}
