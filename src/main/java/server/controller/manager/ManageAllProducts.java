package server.controller.manager;

import common.model.commodity.Category;
import common.model.commodity.Commodity;
import server.controller.share.Menu;
import server.data.YaDataManager;

public class ManageAllProducts extends Menu {
    public void removeCommodity(int commodityId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(commodityId);
        YaDataManager.removeCommodity(commodity);
        for (Category category : YaDataManager.getCategories()) {
            if (category.getCommoditiesId() != null)
                for (int categoryCommodityId : category.getCommoditiesId()) {
                    if (categoryCommodityId == commodityId) {
                        category.getCommoditiesId().remove(categoryCommodityId);
                        YaDataManager.removeCategory(category);
                        YaDataManager.addCategory(category);
                    }
                }
        }
    }
}
