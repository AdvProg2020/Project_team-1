package server.controller.share;

import common.model.commodity.Commodity;
import server.dataManager.YaDataManager;
import common.model.filter.Filter;

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

    private static ArrayList<Commodity> filteredCommodities;

    public static void setFilteredCommodities(ArrayList<Commodity> filteredCommodities) {
        FilteringMenu.filteredCommodities = filteredCommodities;
    }

    static {
        try {
            filteredCommodities = new ArrayList<Commodity>(YaDataManager.getCommodities());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void updateFilteredCommodities() throws Exception {
        filteredCommodities = new ArrayList<Commodity>();
        for (Commodity commodity : YaDataManager.getCommodities()) {
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
}
