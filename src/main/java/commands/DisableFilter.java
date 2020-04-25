package commands;

import controller.ProductsMenu;
import model.filter.Filter;

public class DisableFilter extends Command {
    public DisableFilter(String regex) {
        super(regex);
    }

    public String checkErrors(){
        if (matcher.group("filter name").startsWith("filter by category"))
            return "you cant disable this filter";
        return null;
    }

    @Override
    public String runCommand(String command) {
        if (checkErrors() != null)
            return checkErrors();
        for (Filter currentFilter : ProductsMenu.getCurrentFilters()) {
            if (currentFilter.getFilterName().equals(matcher.group("filter name"))) {
                ProductsMenu.disableFilter(currentFilter);
                return "filter disabled";
            }
        }
        return null;
    }
}
