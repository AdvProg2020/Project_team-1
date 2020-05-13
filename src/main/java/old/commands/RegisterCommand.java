package old.commands;

import old.GetAccountInfoMenu;
import controller.HandleMenu;
import old.LoginRegisterMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCommand extends Command {
    LoginRegisterMenu menu;
    Matcher matcher;

    public RegisterCommand() {
        super.regex = "create account (?<type>customer|seller|admin) (?<username>\\S+)";
    }

    @Override
    public String runCommand(String command) {
        matcher = Pattern.compile(regex).matcher(command);
        this.menu = (LoginRegisterMenu) HandleMenu.getMenu();
        if (checkErrors() != null)
            return checkErrors();
        HandleMenu.setMenu(new GetAccountInfoMenu(matcher.group("username"), matcher.group("type")));
        return "please enter your information in this format: (if you want to be a seller)\n" +
                "password first_name last_name email phone_number( company_name)";
    }

    public String checkErrors() {
        if (menu.isUserNameValid(matcher.group("username"))) {
            return "This username isn't available";
        }
        return null;
    }
}
