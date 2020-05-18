package controller;

import model.Commodity;
import model.YaDataManager;

public class ManageAllProducts extends Menu {
    public void removeCommodity(int commodityId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(commodityId);
        YaDataManager.removeCommodity(commodity);
    }
}
