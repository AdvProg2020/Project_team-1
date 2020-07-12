package server.controller.manager;

import server.data.YaDataManager;
import server.controller.share.Menu;
import common.model.commodity.Category;
import common.model.commodity.CategorySpecification;
import common.model.commodity.Commodity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class ManageCategoryMenu extends Menu {
    public ManageCategoryMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/ManageCategories.fxml";
    }

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean checkCategorySpecificationTitle(ArrayList<CategorySpecification> categorySpecifications , String title){
        for (CategorySpecification categorySpecification : categorySpecifications) {
            if (categorySpecification.getTitle().equals(title)){
                return false;
            }
        }
        return true;
    }


    public boolean checkCategoryName(String name) throws IOException {
        return YaDataManager.isCategoryExist(name);
    }

    public String changeName(String name, Category category) throws IOException {
        category.setName(name);
        YaDataManager.removeCategory(category);
        YaDataManager.addCategory(category);
        updateCommodities(category);
        return "Category name successfully changed";
    }

    public void addCategorySpecification(HashSet<String> options, String title, Category category) throws IOException {
        CategorySpecification categorySpecification = new CategorySpecification(title, options);
        category.getFieldOptions().add(categorySpecification);
        updateCommodities(category);
    }

    public void removeCategorySpecification(String title, Category category) throws Exception {
        for (CategorySpecification fieldOption : category.getFieldOptions()) {
            if (fieldOption.getTitle().equals(title)) {
                for (CategorySpecification option : category.getFieldOptions()) {
                    if (option.getTitle().equalsIgnoreCase(title)) {
                        category.getFieldOptions().remove(option);
                        updateCommodities(category);
                        return;
                    }
                }
            }
        }
        throw new Exception("there is no category specification with this title");
    }

    public void addCommodity(int commodityId, Category category) throws Exception {
        for (Commodity commodity : YaDataManager.getCommodities()) {
            if (commodity.getCommodityId() == commodityId) {
                category.getCommodities().add(commodity);
                YaDataManager.removeCommodity(commodity);
                commodity.setCategory(category);
                updateFile(category);
                YaDataManager.addCommodity(commodity);
                return;
            }
        }
        throw new Exception("invalid commodity ID");
    }

    public void removeCommodity(int commodityId, Category category) throws Exception {
        for (Commodity commodity : YaDataManager.getCommodities()) {
            if (commodity.getCommodityId() == commodityId) {
                for (Commodity categoryCommodity : category.getCommodities()) {
                    if (categoryCommodity.getCommodityId() == commodityId) {
                        YaDataManager.removeCommodity(commodity);
                        category.getCommodities().remove(categoryCommodity);
                        commodity.setCategory(null);
                        updateCommodities(category);
                        YaDataManager.addCommodity(commodity);
                        return;
                    }
                }
                throw new Exception("This category doesn't have this commodity");
            }
        }
        throw new Exception("invalid commodity ID");
    }

    private void updateCommodities(Category category) throws IOException {
        for (Commodity commodity1 : category.getCommodities()) {
            YaDataManager.removeCommodity(commodity1);
            YaDataManager.addCommodity(commodity1);
        }
    }

    public CategorySpecification createCategorySpecification(String title, HashSet<String> options) {
        return new CategorySpecification(title, options);
    }

    public void removeCategory(String categoryName) throws IOException {
        Category category = getCategory(categoryName);
        YaDataManager.removeCategory(category);
        for (Commodity commodity : YaDataManager.getCommodities()) {
            if (commodity.getCategory() != null)
                if (commodity.getCategory().getName().equalsIgnoreCase(categoryName)) {
                    commodity.setCategory(null);
                    YaDataManager.removeCommodity(commodity);
                    YaDataManager.addCommodity(commodity);
                }
        }
    }


    public boolean checkCommoditiesId(ArrayList<Integer> commoditiesId) throws Exception {
        for (Integer integer : commoditiesId) {
            if (!YaDataManager.isCommodityExist(integer))
                return false;
        }
        return true;
    }

    public void addCategory(String
                                    name, ArrayList<Commodity> commodities , ArrayList<CategorySpecification> categorySpecifications) throws
            Exception {

        Category category = new Category(name, commodities, categorySpecifications);
        for (Commodity commodity : commodities) {
            commodity.setCategory(category);
            YaDataManager.removeCommodity(commodity);
            YaDataManager.addCommodity(commodity);
        }
        YaDataManager.addCategory(category);
    }


    public Category getCategory(String name) throws IOException {
        for (Category allCategory : YaDataManager.getCategories()) {
            if (allCategory.getName().equalsIgnoreCase(name))
                return allCategory;
        }
        return null;
    }

    public void updateFile(Category category) throws IOException {
        YaDataManager.addCategory(category);
        for (Commodity commodity : category.getCommodities()) {
            commodity.setCategory(category);
            YaDataManager.removeCommodity(commodity);
            YaDataManager.addCommodity(commodity);
        }
    }
}
