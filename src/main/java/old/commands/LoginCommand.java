package old.commands;

import controller.HandleMenu;
import old.LoginRegisterMenu;

import java.util.regex.Matcher;

public class LoginCommand extends Command{

    LoginRegisterMenu menu;
    Matcher matcher;

    public LoginCommand() {
        super.regex = "login (?<username>\\S+ ?<password>\\S+)";
    }

    @Override
    public String runCommand(String command) {
        menu = (LoginRegisterMenu)HandleMenu.getMenu();
        if (checkErrors() != null)
            return checkErrors();
        menu.login(matcher.group("username"), matcher.group("password"));
        return "you have successfully logged in";
    }


    public String checkErrors(){
        if (!menu.isUserNameValid(matcher.group("username"))) {
            return "Valid nist :)";
        }
        return null;
    }
}
