package old;

import model.Commodity;
import model.DataManager;
import model.filter.Filter;
import old.commands.Command;

import java.util.ArrayList;
import java.util.Arrays;

import static model.Commodity.Comparators.*;

public class ProductsMenu implements CommandProcess {
    public CommandProcess commandProcess;

    private static ArrayList<Commodity> filteredCommodities;

    static {
        try {
            filteredCommodities = (ArrayList<Commodity>) Arrays.asList(DataManager.getAllCommodities().clone());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Filter> currentFilters = new ArrayList<Filter>();
    private String currentSort;


    public static ArrayList<Commodity> getFilteredCommodities() {
        return filteredCommodities;
    }

    public static ArrayList<Filter> getCurrentFilters() {
        return currentFilters;
    }

    public static void addFilteredCommodities(Commodity commodity) {
        filteredCommodities.add(commodity);
    }

    public static void removeFromFilteredCommodities(Commodity commodity) {
        filteredCommodities.remove(commodity);
    }

    public static void filter(Filter filter) throws Exception {
        currentFilters.add(filter);
        updateFilteredCommodities();
    }

    public static void updateFilteredCommodities() throws Exception {
        filteredCommodities = new ArrayList<Commodity>();
        for (Commodity commodity : DataManager.getAllCommodities()) {
            if (canCommodityPassFilter(commodity))
                filteredCommodities.add(commodity);
        }
    }

    public static boolean canCommodityPassFilter(Commodity commodity) {
        for (Filter filter : currentFilters) {
            if (!filter.isCommodityMatches(commodity))
                return false;
        }
        return true;
    }

    public String getCurrentSort() {
        return currentSort;
    }

    public static void disableFilter(Filter filter) throws Exception {
        currentFilters.remove(filter);
        updateFilteredCommodities();
    }

    public void sort(String sort) throws Exception {
        this.currentSort = sort;
        if (sort.equals("price")) {
            Arrays.sort(DataManager.getAllCommodities(), price);
            return;
        }
        if (sort.equals("Number of visits")) {
            Arrays.sort(DataManager.getAllCommodities(), numberOfVisits);
            return;
        }
        if (sort.equals("Average score")) {
            Arrays.sort(DataManager.getAllCommodities(), score);
            return;
        }
    }

    public void disableSort() throws Exception {
        this.currentSort = "Number of visits";
        Arrays.sort(DataManager.getAllCommodities(), numberOfVisits);
    }


    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command productsMenuCommand : productsMenuCommands) {
            if (productsMenuCommand.checkCommand(command))
                return productsMenuCommand.runCommand(command);
        }
        return "invalid command";

    }
}