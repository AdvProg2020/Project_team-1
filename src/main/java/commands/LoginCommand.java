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
    public boolean checkCommand(String command) {
        matcher = pattern.matcher(command);
        return matcher.matches();
    }

    @Override
    public void runCommand(String command) {
        menu = (LoginRegisterMenu)HandleMenu.getMenu();
        checkErrors();
        String password = Main.getConsoleScanner().nextLine();
        menu.login(matcher.group("username"), password);
    }


    public String checkErrors(){
        if (!menu.isUserNameValid(matcher.group("username"))){
            return "Valid nist :)";
        }
        return null;
    }
}
