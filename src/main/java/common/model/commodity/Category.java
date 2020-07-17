package common.model.commodity;

import server.dataManager.YaDataManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String name;
    private ArrayList<Integer> commoditiesId;
    private ArrayList<CategorySpecification> fieldOptions;

    public Category(String name, ArrayList<Integer> commoditiesId, ArrayList<CategorySpecification> categorySpecifications) {
        this.name = name;
        this.commoditiesId = commoditiesId;
        this.fieldOptions = categorySpecifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getCommoditiesId() {
        return commoditiesId;
    }

    public void setCommoditiesId(ArrayList<Integer> commoditiesId) {
        this.commoditiesId = commoditiesId;
    }

    public ArrayList<CategorySpecification> getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(ArrayList<CategorySpecification> fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    @Override
    public String toString() {
        StringBuilder commoditiesNames = new StringBuilder();
        for (int commodityId : commoditiesId) {
            try {
                commoditiesNames.append(YaDataManager.getCommodityById(commodityId).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            commoditiesNames.append("-");
        }
        StringBuilder options = new StringBuilder();
        for (CategorySpecification fieldOption : fieldOptions) {
            options.append(fieldOption.getTitle());
            options.append("-");
        }
        return "Category{" +
                "name='" + name + '\'' +
                ", commodities=" + commoditiesNames.toString() +
                ", fieldOptions=" + options.toString() +
                '}';
    }
}
