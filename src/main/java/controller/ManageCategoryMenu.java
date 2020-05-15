package controller;

import model.Category;
import model.CategorySpecification;
import model.Commodity;
import model.DataManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class ManageCategoryMenu extends Menu {

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean checkCategoryName(String name) throws FileNotFoundException {
        return DataManager.isCategoryExist(name);
    }

    public String changeName(String name , Category category){
        category.setName(name);
        return "Category name successfully changed";
    }

    public void addCategorySpecification(HashSet<String> options,String title ,  Category category){
        CategorySpecification categorySpecification = new CategorySpecification(title, options);
        category.getFieldOptions().add(categorySpecification);
    }

    public void removeCategorySpecification(String title ,  Category category){
        for (CategorySpecification fieldOption : category.getFieldOptions()) {
            if (fieldOption.getTitle().equals(title)){
                category.getFieldOptions().remove(fieldOption);
            }
        }
    }

    public void addCommodity(int commodityId , Category category) throws Exception {
        for (Commodity commodity : DataManager.getAllCommodities()) {
            if (commodity.getCommodityId() == commodityId)
                category.getCommodities().add(commodity);
        }
        throw new Exception("invalid commodity ID");
    }

    public void removeCommodity(int commodityId , Category category) throws Exception {
        for (Commodity commodity : DataManager.getAllCommodities()) {
            if (commodity.getCommodityId() == commodityId)
                category.getCommodities().remove(commodity);
        }
        throw new Exception("invalid commodity ID");
    }

    public CategorySpecification createCategorySpecification(String title , HashSet<String> options){
        return new CategorySpecification(title, options);
    }

    public void removeCategory(String categoryName) throws IOException {
        Category category = getCategory(categoryName);
        DataManager.removeCategory(category);

    }

    public void addCategory(String name , ArrayList<Integer> commodityIds , ArrayList<CategorySpecification> categorySpecifications) throws Exception {
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        for (Commodity commodity : DataManager.getAllCommodities()) {
            for (Integer commodityId : commodityIds) {
                if (commodityId == commodity.getCommodityId())
                  commodities.add(commodity);
            }
        }
        Category category = new Category(name,commodities,categorySpecifications);
        DataManager.addCategory(category);
    }
    public Category getCategory(String name) throws FileNotFoundException {
        for (Category allCategory : DataManager.getAllCategories()) {
            if (allCategory.getName().equalsIgnoreCase(name))
                return allCategory;
        }
        return null;
    }
}
