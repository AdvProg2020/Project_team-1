package commands;

import controller.HandleMenu;
import controller.LoginRegisterMenu;
import main.Main;

import java.util.regex.Matcher;

public class LoginCommand extends Command{

    LoginRegisterMenu menu;

    public LoginCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        menu = (LoginRegisterMenu)HandleMenu.getMenu();
        if (checkErrors() != null)
            return checkErrors();
        String password = Main.getConsoleScanner().nextLine();
        menu.login(matcher.group("username"), password);
        return "you have successfully logged in";
    }


    public String checkErrors(){
        if (!menu.isUserNameValid(matcher.group("username"))){
            return "Valid nist :)";
        }
        return null;
    }
}
