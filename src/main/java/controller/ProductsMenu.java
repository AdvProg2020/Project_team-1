package controller;

import model.Category;
import model.Commodity;
import model.DataManager;
import view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static model.Commodity.Comparators.*;

public class ProductsMenu extends Menu {
    public Category[] getAllCategories() throws IOException {
        return DataManager.getAllCategories();
    }


    public void filtering(){
        MenuHandler.getInstance().setCurrentMenu(View.filteringMenu);
        View.filteringMenu.setPreviousMenu(View.productsMenu);
    }

    public void sorting(){
        MenuHandler.getInstance().setCurrentMenu(View.sortingMenu);
        View.sortingMenu.setPreviousMenu(View.productsMenu);
    }

    public ArrayList<Commodity> getProducts() {
            if (SortingMenu.getCurrentSort().equals("price")) {
                    Collections.sort(FilteringMenu.getFilteredCommodities(), price);
            }
            if (SortingMenu.getCurrentSort().equals("Number of visits")) {
                    Collections.sort(FilteringMenu.getFilteredCommodities(), numberOfVisits);
            }
            if (SortingMenu.getCurrentSort().equals("Average score")) {
                    Collections.sort(FilteringMenu.getFilteredCommodities(), score);
            }
            return FilteringMenu.getFilteredCommodities();
    }

    public Commodity getProducts(int id) throws Exception {
            if (!DataManager.isCommodityExist(id)){
                 throw new Exception("invalid id");
            }
            return DataManager.getCommodityById(id);
    }


}
