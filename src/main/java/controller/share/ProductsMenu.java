package controller.share;

import model.commodity.Category;
import model.commodity.Commodity;
import controller.data.YaDataManager;
import view.commandline.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static model.commodity.Commodity.Comparators.*;

public class ProductsMenu extends Menu {
    public ArrayList<Category> getAllCategories() throws IOException {
        return YaDataManager.getCategories();
    }

    public void filtering(){
        MenuHandler.getInstance().setCurrentMenu(View.filteringMenu);
        View.filteringMenu.setPreviousMenu(View.productsMenu);
    }

    public void sorting(){
        MenuHandler.getInstance().setCurrentMenu(View.sortingMenu);
        View.sortingMenu.setPreviousMenu(View.productsMenu);
    }

    public ProductsMenu() {
        fxmlFileAddress = "../../Products.fxml";
    }

    public ArrayList<Commodity> getProducts() throws Exception {
            FilteringMenu.updateFilteredCommodities();
            if (SortingMenu.getCurrentSort().equalsIgnoreCase("price")) {
                    Collections.sort(FilteringMenu.getFilteredCommodities(), price);
            }
            if (SortingMenu.getCurrentSort().equalsIgnoreCase("Number of visits")) {
                    Collections.sort(FilteringMenu.getFilteredCommodities(), numberOfVisits);
            }
            if (SortingMenu.getCurrentSort().equalsIgnoreCase("Average score")) {
                    Collections.sort(FilteringMenu.getFilteredCommodities(), score);
            }
            if (SortingMenu.getCurrentSort().equalsIgnoreCase("Date")){
                Collections.sort(FilteringMenu.getFilteredCommodities() , date);
            }
            return FilteringMenu.getFilteredCommodities();
    }

    public Commodity getProducts(int id) throws Exception {
            if (!YaDataManager.isCommodityExist(id)){
                 throw new Exception("invalid id");
            }
            return YaDataManager.getCommodityById(id);
    }


}
