package commands;

import controller.ProductsMenu;

public class CurrentFilter extends Command {
    public CurrentFilter(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        return ProductsMenu.getCurrentFilters().toString();
    }
}
