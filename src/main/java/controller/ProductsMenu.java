package controller;

import commands.Command;
import commands.FilteringCommand;
import commands.ShowAvailableFiltersCommand;
import commands.ViewCategoryCommand;
import model.Commodity;
import model.SuperMarket;
import model.filter.Filter;

import java.util.ArrayList;
import java.util.Collections;

import static model.Commodity.Comparators.*;

public class ProductsMenu implements CommandProcess {

    private static ArrayList <Command> productsMenuCommands = new ArrayList<Command>();
    private static ArrayList<Commodity> filteredCommodities = (ArrayList<Commodity>) SuperMarket.getAllCommodities().clone();

    public static ArrayList<Command> getProductsMenuCommands() {
        return productsMenuCommands;
    }

    public static ArrayList<Commodity> getFilteredCommodities() {
        return filteredCommodities;
    }

    public static ArrayList<Filter> getCurrentFilters() {
        return currentFilters;
    }

    public String getCurrentSort() {
        return currentSort;
    }

    private static ArrayList<Filter> currentFilters = new ArrayList<Filter>();
    private String currentSort;

    public static void addFilteredCommodities(Commodity commodity) {
        filteredCommodities.add(commodity);
    }

    public static void removeFromFilteredCommodities(Commodity commodity) {
        filteredCommodities.remove(commodity);
    }

    public static void filter(Filter filter) {
        currentFilters.add(filter);
        updateFilteredCommodities();
    }

    public static void updateFilteredCommodities() {
        filteredCommodities = new ArrayList<Commodity>();
        for (Commodity commodity : SuperMarket.getAllCommodities()) {
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


    public void disableFilter(Filter filter) {
        currentFilters.remove(filter);
        updateFilteredCommodities();
    }

    public void sort(String sort) {
        this.currentSort = sort;
        if (sort.equals("price")) {
            Collections.sort(SuperMarket.getAllCommodities(), price);
            return;
        }
        if (sort.equals("Number of visits")) {
            Collections.sort(SuperMarket.getAllCommodities(), numberOfVisits);
            return;
        }
        if (sort.equals("Average score")) {
            Collections.sort(SuperMarket.getAllCommodities(), score);
            return;
        }
    }

    public void disableSort() {
        this.currentSort = "Number of visits";
        Collections.sort(SuperMarket.getAllCommodities(), numberOfVisits);
    }


    @Override
    public String commandProcessor(String command) {
        productsMenuCommands.add(new ViewCategoryCommand("$view categories^"));
        productsMenuCommands.add(new FilteringCommand("$filtering^"));
        productsMenuCommands.add(new ShowAvailableFiltersCommand("$show available filters^"));
        for (Command productsMenuCommand : productsMenuCommands) {
            if (productsMenuCommand.checkCommand(command))
                return productsMenuCommand.runCommand(command);
        }
        return "invalid command";

    }
}