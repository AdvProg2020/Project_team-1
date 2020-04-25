package commands;

import model.SuperMarket;

public class ViewCategoryCommand extends Command{


    public ViewCategoryCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        return SuperMarket.getAllCategory().toString();
    }
}
