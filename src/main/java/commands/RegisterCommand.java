package commands;

import controller.HandleMenu;
import controller.LoginRegisterMenu;
import main.Main;
import model.account.BusinessAccount;
import model.account.PersonalAccount;

public class RegisterCommand extends Command {
    LoginRegisterMenu menu;

    public RegisterCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) throws Exception {
        this.menu = (LoginRegisterMenu) HandleMenu.getMenu();
        if (checkErrors() != null)
            return checkErrors();
        String[] information = new String[]{"firstName", "lastName", "email", "phoneNumber", "password"};
        String[] input = new String[5];
        for (int i = 0; i < 5; i++) {
            Main.print("please enter your " + information[i]);
            input[i] = Main.scan();
        }
        PersonalAccount account = new PersonalAccount(matcher.group("username"), input[0], input[1], input[2],
                input[3], input[4]);
        menu.registerPersonalAccount(account);
        if (matcher.group("type").equals("seller")) {
            menu.sendBusinessAccountRequest(account, new BusinessAccount(matcher.group("username"), input[0],
                    input[1], input[2], input[3], input[4], Main.scan()));
            return "Customer account is ready and your request is sent";
        }
        return "Customer account is ready";
    }

    public String checkErrors() {
        if (menu.isUserNameValid(matcher.group("username"))) {
            return "This username isn't available";
        }
        return null;
    }
}
