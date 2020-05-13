package old.commands;

import old.HandleMenu;
import old.ManageCategoryMenu;
import model.Category;
import model.DataManager;

public class ManageCategoryCommand extends Command {
    public ManageCategoryCommand() {
        this.regex = "^manage category$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String respond = "";
        for (Category category : DataManager.getAllCategory()) {
            respond += "["  + category.toString() + "]" ;
        }
        HandleMenu.setMenu(new ManageCategoryMenu());
        return respond;

    }


}
