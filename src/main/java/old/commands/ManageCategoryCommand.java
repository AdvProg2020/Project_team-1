package old.commands;

import controller.HandleMenu;
import old.ManageCategoryMenu;
import model.Category;
import model.SuperMarket;

public class ManageCategoryCommand extends Command {
    public ManageCategoryCommand() {
        this.regex = "^manage category$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String respond = "";
        for (Category category : SuperMarket.getAllCategory()) {
            respond += "["  + category.toString() + "]" ;
        }
        HandleMenu.setMenu(new ManageCategoryMenu());
        return respond;

    }


}
