package server.controller.manager;

import common.model.commodity.Category;
import common.model.commodity.Commodity;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class ManageCategoryMenu {
    private Category getCategory(String name) throws IOException {
        for (Category category : YaDataManager.getCategories()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public void removeCategory(String categoryName) throws IOException {
        Category category = getCategory(categoryName);
        YaDataManager.removeCategory(category);
        for (Commodity commodity : YaDataManager.getCommodities()) {
            if (!commodity.getCategoryName().equals(""))
                if (commodity.getCategoryName().equalsIgnoreCase(categoryName)) {
                    commodity.setCategoryName("");
                    YaDataManager.removeCommodity(commodity);
                    YaDataManager.addCommodity(commodity);
                }
        }
    }
}
