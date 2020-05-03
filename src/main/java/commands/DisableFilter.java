package commands;

import controller.ProductsMenu;
import model.filter.Filter;

import java.util.regex.Matcher;

public class DisableFilter extends Command {
    Matcher matcher;

    public DisableFilter() {
        super.regex = "$disable filter (?<filter name>\\S+)";
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
