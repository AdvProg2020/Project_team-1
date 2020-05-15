package model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Category> subCategories;
    private Category father;
    private ArrayList<Commodity> commodities;
    private ArrayList<CategorySpecification> fieldOptions;

    public Category(String name, int numberOfFields, Category father) {
        this.name = name;
        this.father = father;
        this.commodities = new ArrayList<Commodity>();
        this.fieldOptions = new ArrayList<CategorySpecification>();
        this.subCategories = new ArrayList<Category>();
    }

    public Category(String name, ArrayList<Commodity> commodities , ArrayList<CategorySpecification> categorySpecifications) {
        this.name = name;
        this.father = null;
        this.commodities = commodities;
        this.fieldOptions = categorySpecifications;
        this.subCategories = new ArrayList<Category>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getFather() {
        return father;
    }

    public void setFather(Category father) {
        this.father = father;
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
        return "Category{" +
                "name='" + name + '\'' +
                ", subCategories=" + subCategories +
                ", father=" + father +
                ", commodities=" + commodities +
                ", fieldOptions=" + fieldOptions +
                '}';
    }
}
