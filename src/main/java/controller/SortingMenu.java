package controller;

import model.YaDataManager;

import java.util.Collections;

import static model.Commodity.Comparators.*;

public class SortingMenu extends Menu {
    private static String currentSort = "number of visits";



    public static String getCurrentSort() {
        return currentSort;
    }



    public void sort(String sort) throws Exception {
        currentSort = sort;
        if (sort.equals("price")) {
            Collections.sort(YaDataManager.getCommodities(), price);
            return;
        }
        if (sort.equals("Number of visits")) {
            Collections.sort(YaDataManager.getCommodities(), numberOfVisits);
            return;
        }
        if (sort.equals("Average score")) {
            Collections.sort(YaDataManager.getCommodities(), score);
            return;
        }
        throw new Exception("invalid sort");
    }

    public void disableSort() throws Exception {
        currentSort = "Number of visits";
        Collections.sort(YaDataManager.getCommodities(), numberOfVisits);
    }
}
