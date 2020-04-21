package controller;

import model.Commodity;
import model.SuperMarket;
import model.filter.Filter;

import java.util.ArrayList;

public class ProductsMenu implements CommandProcess {

    private ArrayList<Filter> currentFilters = new ArrayList<Filter>();
    private static ArrayList<Commodity> filteredCommodities = (ArrayList<Commodity>) SuperMarket.getAllCommodities().clone();


    public static void addFilteredCommodities(Commodity commodity) {
        filteredCommodities.add(commodity);
    }

    public static void removeFromFilteredCommodities(Commodity commodity){
        filteredCommodities.remove(commodity);
    }

    public void filter(Filter filter) {
        currentFilters.add(filter);
        updateFilteredCommodities();
    }

    public void updateFilteredCommodities(){
        for (Commodity commodity : SuperMarket.getAllCommodities()) {
            if (canCommodityPassFilter(commodity))
                filteredCommodities.add(commodity);
        }
    }

    public boolean canCommodityPassFilter(Commodity commodity){
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
    }

    public void disableSort() {
    }

    public void currentSort() {
    }

    private void sortByPrice() {

    }

    private void sortByScore() {

    }

    @Override
    public void commandProcessor(String command) {

    }
}