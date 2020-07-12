package server.controller.manager;

import server.controller.share.Menu;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import server.data.YaDataManager;

public class ManageAllProducts extends Menu {
    public void removeCommodity(int commodityId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(commodityId);
        YaDataManager.removeCommodity(commodity);
        for (Category category : YaDataManager.getCategories()) {
            if (category.getCommodities() != null)
                for (Commodity categoryCommodity : category.getCommodities()) {
                    if (categoryCommodity.getCommodityId() == commodityId) {
                        category.getCommodities().remove(categoryCommodity);
                        YaDataManager.removeCategory(category);
                        YaDataManager.addCategory(category);
                    }
                }
        }
    }
}
