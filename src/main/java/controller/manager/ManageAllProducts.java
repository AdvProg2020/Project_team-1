package controller.manager;

import controller.share.Menu;
import model.commodity.Category;
import model.commodity.Commodity;
import controller.data.YaDataManager;

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
