package common.model.commodity;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Commodity> commodities;
    private ArrayList<CategorySpecification> fieldOptions;

    public Category(String name, int numberOfFields) {
        this.name = name;
        this.commodities = new ArrayList<>();
        this.fieldOptions = new ArrayList<>();
    }

    public Category(String name, ArrayList<Commodity> commodities , ArrayList<CategorySpecification> categorySpecifications) {
        this.name = name;
        this.commodities = commodities;
        this.fieldOptions = categorySpecifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<Commodity> commodities) {
        this.commodities = commodities;
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
        for (Commodity commodity : commodities) {
            commoditiesNames.append(commodity.getName());
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
