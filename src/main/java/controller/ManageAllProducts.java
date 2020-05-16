package controller;

import model.Commodity;
import model.DataManager;

public class ManageAllProducts extends Menu {
    public void removeCommodity(int commodityId) throws Exception {
        Commodity commodity = DataManager.getCommodityById(commodityId);
        DataManager.deleteCommodity(commodity);
    }
}
