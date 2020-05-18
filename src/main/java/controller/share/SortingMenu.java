package controller.share;

import controller.data.YaDataManager;

import java.util.Collections;

import static model.commodity.Commodity.Comparators.*;

public class SortingMenu extends Menu {
    private static String currentSort = "number of visits";



    public static String getCurrentSort() {
        return currentSort;
    }



    public void sort(String sort) throws Exception {
        currentSort = sort;
        if (sort.equalsIgnoreCase("price")) {
            Collections.sort(YaDataManager.getCommodities(), price);
            return;
        }
        if (sort.equalsIgnoreCase("Number of visits")) {
            Collections.sort(YaDataManager.getCommodities(), numberOfVisits);
            return;
        }
        if (sort.equalsIgnoreCase("Average score")) {
            Collections.sort(YaDataManager.getCommodities(), score);
            return;
        }
        if (sort.equalsIgnoreCase("Date")){
            Collections.sort(YaDataManager.getCommodities(), date);
            return;
        }
        throw new Exception("invalid sort");
    }

    public void disableSort() throws Exception {
        currentSort = "Number of visits";
        Collections.sort(YaDataManager.getCommodities(), numberOfVisits);
    }
}
