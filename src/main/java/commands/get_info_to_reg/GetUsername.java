package commands.get_info_to_reg;

import commands.Command;
import controller.GetAccountInfoMenu;
import controller.HandleMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUsername extends Command {
    public GetUsername() {
        this.regex = "\\S+";
    }

    @Override
    public String runCommand(String command) {
        HandleMenu.setMenu(new GetAccountInfoMenu(command, "admin"));
        return "please enter your information in this format:\n" +
                "password first_name last_name email phone_number";
    }

    public boolean checkCommand(String command) {
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        return matcher.matches();
    }
}
