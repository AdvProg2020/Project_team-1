package commands;

import controller.ProductsMenu;
import model.filter.Filter;

public class CurrentFilter extends Command {
    public CurrentFilter(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        String respond = "";
        for (Filter filter : ProductsMenu.getCurrentFilters()) {
            respond += filter.getFilterName() + "\n";
        }
        return respond;
    }
}
