package server.controller.share;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Commodity;

import java.util.ArrayList;

import static client.Main.inputStream;
import static client.Main.outputStream;
import static common.model.commodity.Commodity.Comparators.*;

public class SortingMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private static String currentSort = "number of visits";

    public static String getCurrentSort() {
        return currentSort;
    }


    public void sort(String sort) throws Exception {
        currentSort = sort;
        ArrayList<Commodity> commodities = FilteringMenu.getFilteredCommodities();
        if (sort.equalsIgnoreCase("price")) {
            commodities.sort(price);
        } else if (sort.equalsIgnoreCase("Number of visits")) {
            commodities.sort(numberOfVisits);
        } else if (sort.equalsIgnoreCase("Average score")) {
            commodities.sort(score);
        } else if (sort.equalsIgnoreCase("Date")) {
            commodities.sort(date);
        } else {
            throw new Exception("invalid sort");
        }
    }

    public void disableSort() throws Exception {
        currentSort = "Number of visits" ;
        FilteringMenu.getFilteredCommodities().sort(numberOfVisits);
    }
}
