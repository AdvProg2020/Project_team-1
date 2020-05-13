package old.commands;

import old.AccountMenu;
import controller.HandleMenu;
import old.ViewPersonalInfoMenu;
import model.DataManager;

public class ViewPersonalInfoCommand extends Command {
    AccountMenu menu;

    public ViewPersonalInfoCommand() {
        super.regex = "^view personal info$";
    }

    @Override
    public String runCommand(String command) {
        this.menu = (AccountMenu) HandleMenu.getMenu();
        HandleMenu.setMenu(new ViewPersonalInfoMenu(DataManager.getOnlineAccount()));
        return menu.getAccount().getInformation() + "\nand enter the field you want to edit and after that the new" +
                "value of that. For example: phone 09123456789\n" +
                "only password is different and you have to edit it like this:\n" +
                "password old_value new_value repeat_of_new_value";
    }
}
