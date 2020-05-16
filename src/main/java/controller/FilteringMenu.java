package controller;

import model.Commodity;
import model.DataManager;
import model.filter.Filter;

import java.util.ArrayList;

public class FilteringMenu extends Menu {
    public void disableFilter(String filterName) throws Exception {
        currentFilters.remove(getFilterByName(filterName));
        updateFilteredCommodities();
    }

    private Filter getFilterByName(String name) throws Exception {
        for (Filter filter : currentFilters) {
            if (filter.getFilterName().equalsIgnoreCase(name))
                return filter;
        }
        throw  new Exception("invalid filter name");

    }

    private static ArrayList<Commodity> filteredCommodities = new ArrayList<Commodity>();

    public FilteringMenu() {
        try {
          //  filteredCommodities = (ArrayList<Commodity>) asList(DataManager.getAllCommodities());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    static {

    }

    public static ArrayList<Filter> currentFilters = new ArrayList<Filter>();

    public static ArrayList<Commodity> getFilteredCommodities() {
        return filteredCommodities;
    }

    public static ArrayList<Filter> getCurrentFilters() {
        return currentFilters;
    }

    public  void filter(Filter filter) throws Exception {
        currentFilters.add(filter);
        updateFilteredCommodities();
    }

    public  void updateFilteredCommodities() throws Exception {
        filteredCommodities = new ArrayList<Commodity>();
        for (Commodity commodity : DataManager.getAllCommodities()) {
            if (canCommodityPassFilter(commodity))
                filteredCommodities.add(commodity);
        }
    }

    public boolean canCommodityPassFilter(Commodity commodity) {
        for (Filter filter : currentFilters) {
            if (!filter.isCommodityMatches(commodity))
                return false;
        }
        return true;
    }
}
