package controller.manager;

import controller.share.Menu;
import model.commodity.Commodity;
import controller.data.YaDataManager;

public class ManageAllProducts extends Menu {
    public void removeCommodity(int commodityId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(commodityId);
        YaDataManager.removeCommodity(commodity);
    }
}
