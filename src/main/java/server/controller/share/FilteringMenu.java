package server.controller.share;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Commodity;
import common.model.filter.Filter;
import server.dataManager.YaDataManager;

import java.util.ArrayList;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class FilteringMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public static ArrayList<Filter> currentFilters = new ArrayList<Filter>();
    private static ArrayList<Commodity> filteredCommodities;

    static {
        try {
            outputStream.writeUTF("send all commodities");
            outputStream.flush();
            filteredCommodities = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Commodity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Commodity> getFilteredCommodities() {
        return filteredCommodities;
    }

    public static void setFilteredCommodities(ArrayList<Commodity> filteredCommodities) {
        FilteringMenu.filteredCommodities = filteredCommodities;
    }

    public static ArrayList<Filter> getCurrentFilters() {
        return currentFilters;
    }

    public static void updateFilteredCommodities() throws Exception {
        filteredCommodities = new ArrayList<>();
        outputStream.writeUTF("send all commodities");
        outputStream.flush();
        ArrayList<Commodity> commodities = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Commodity>>() {
        }.getType());
        for (Commodity commodity : commodities) {
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

    public void disableFilter(String filterName) throws Exception {
        currentFilters.remove(getFilterByName(filterName));
        updateFilteredCommodities();
    }

    private Filter getFilterByName(String name) throws Exception {
        for (Filter filter : currentFilters) {
            if (filter.getFilterName().equalsIgnoreCase(name))
                return filter;
        }
        throw new Exception("invalid filter name");

    }

    public void filter(Filter filter) throws Exception {
        currentFilters.add(filter);
        updateFilteredCommodities();
    }
}
