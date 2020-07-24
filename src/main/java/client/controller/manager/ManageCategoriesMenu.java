package client.controller.manager;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Category;
import common.model.commodity.CategorySpecification;
import common.model.commodity.Commodity;
import client.controller.share.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class ManageCategoriesMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public ManageCategoriesMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/ManageCategories.fxml";
    }

    public boolean checkCategorySpecificationTitle(ArrayList<CategorySpecification> categorySpecifications, String title) {
        for (CategorySpecification categorySpecification : categorySpecifications) {
            if (categorySpecification.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }

    public void removeCategorySpecification(String title, Category category) throws Exception {
        for (CategorySpecification fieldOption : category.getFieldOptions()) {
            if (fieldOption.getTitle().equals(title)) {
                for (CategorySpecification option : category.getFieldOptions()) {
                    if (option.getTitle().equalsIgnoreCase(title)) {
                        category.getFieldOptions().remove(option);
                        for (Integer id : category.getCommoditiesId()) {
                            outputStream.writeUTF("send commodity with id " + id);
                            outputStream.flush();
                            Commodity commodity = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Commodity>() {
                            }.getType());
                            commodity.getCategorySpecifications().removeIf(field -> field.getTitle().equalsIgnoreCase(title));
                            outputStream.writeUTF("update commodity " + yaGson.toJson(commodity, new TypeToken<Commodity>() {
                            }.getType()));
                            outputStream.flush();
                        }
                        return;
                    }
                }
            }
        }
        throw new Exception("there is no category specification with this title");
    }

    public CategorySpecification createCategorySpecification(String title, HashSet<String> options) {
        return new CategorySpecification(title, options);
    }

    public void addCategory(String name, ArrayList<Commodity> commodities
            , ArrayList<CategorySpecification> categorySpecifications) throws Exception {
        ArrayList<Integer> commoditiesId = new ArrayList<>();
        for (Commodity commodity : commodities) {
            commoditiesId.add(commodity.getCommodityId());
        }
        Category category = new Category(name, commoditiesId, categorySpecifications);
        outputStream.writeUTF("new category " + yaGson.toJson(category, new TypeToken<Category>() {
        }.getType()));
        outputStream.flush();
        inputStream.readUTF();
        for (Commodity commodity : commodities) {
            outputStream.writeUTF("change category for commodity " + commodity.getCommodityId());
            outputStream.flush();
            inputStream.readUTF();
        }
        outputStream.writeUTF("done");
        outputStream.flush();
    }


    public Category getCategory(String name) throws IOException {
        outputStream.writeUTF("send categories");
        outputStream.flush();
        ArrayList<Category> categories = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Category>>() {
        }.getType());
        for (Category allCategory : categories) {
            if (allCategory.getName().equalsIgnoreCase(name))
                return allCategory;
        }
        return null;
    }
}
