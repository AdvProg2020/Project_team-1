package commands;

import model.Category;
import model.SuperMarket;

public class ViewCategoryCommand extends Command {


    public ViewCategoryCommand() {
        super.regex = "$view categories^";
    }

    @Override
    public String runCommand(String command) {
        String respond = "";
        for (Category category : SuperMarket.getAllCategory()) {
            respond += category.getName() + "\n";
        }
        return respond;
    }
}
