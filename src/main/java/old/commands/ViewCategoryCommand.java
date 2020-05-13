package old.commands;

import model.Category;
import model.DataManager;

public class ViewCategoryCommand extends Command {


    public ViewCategoryCommand() {
        super.regex = "$view categories^";
    }

    @Override
    public String runCommand(String command) {
        String respond = "";
        for (Category category : DataManager.getAllCategory()) {
            respond += category.getName() + "\n";
        }
        return respond;
    }
}
