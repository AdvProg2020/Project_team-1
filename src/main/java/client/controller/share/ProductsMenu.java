package client.controller.share;

import common.model.commodity.Commodity;
import server.dataManager.YaDataManager;

import java.util.ArrayList;

import static common.model.commodity.Commodity.Comparators.*;

public class ProductsMenu extends Menu {
    public ProductsMenu() {
        fxmlFileAddress = "../../Products.fxml";
    }

    public ArrayList<Commodity> getProduct() throws Exception {
        FilteringMenu.updateFilteredCommodities();
        if (SortingMenu.getCurrentSort().equalsIgnoreCase("price")) {
            FilteringMenu.getFilteredCommodities().sort(price);
        }
        if (SortingMenu.getCurrentSort().equalsIgnoreCase("Number of visits")) {
            FilteringMenu.getFilteredCommodities().sort(numberOfVisits);
        }
        if (SortingMenu.getCurrentSort().equalsIgnoreCase("Average score")) {
            FilteringMenu.getFilteredCommodities().sort(score);
        }
        if (SortingMenu.getCurrentSort().equalsIgnoreCase("Date")) {
            FilteringMenu.getFilteredCommodities().sort(date);
        }
        return FilteringMenu.getFilteredCommodities();
    }

    public Commodity getProduct(int id) throws Exception {
        if (!YaDataManager.isCommodityExist(id)) {
            throw new Exception("invalid id");
        }
        return YaDataManager.getCommodityById(id);
    }
}
