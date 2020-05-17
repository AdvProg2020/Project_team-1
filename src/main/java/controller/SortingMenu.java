package controller;

import model.DataManager;

import java.util.Arrays;

import static model.Commodity.Comparators.*;
import static model.Commodity.Comparators.numberOfVisits;

public class SortingMenu extends Menu {
    private static String currentSort = "number of visits";



    public static String getCurrentSort() {
        return currentSort;
    }



    public void sort(String sort) throws Exception {
        currentSort = sort;
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
        throw new Exception("invalid sort");
    }

    public void disableSort() throws Exception {
        currentSort = "Number of visits";
        Arrays.sort(DataManager.getAllCommodities(), numberOfVisits);
    }
}
