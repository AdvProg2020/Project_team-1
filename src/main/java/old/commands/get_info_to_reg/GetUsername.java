package old.commands.get_info_to_reg;

import old.commands.Command;
import old.GetAccountInfoMenu;
import old.HandleMenu;

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
}
