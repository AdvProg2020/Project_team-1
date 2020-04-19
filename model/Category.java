package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Category {
    private String name;
    private ArrayList<Category> subCategories;
    private Category father;
    private ArrayList<Commodity> commodities;
    private ArrayList<HashSet<String>> fieldOptions;

    public Category(String name, int numberOfFields, Category father) {
        this.name = name;
        this.father = father;
        this.commodities = new ArrayList<Commodity>();
        this.fieldOptions = new ArrayList<HashSet<String>>();
        this.subCategories = new ArrayList<Category>();
    }

    public Category(String name, int numberOfFields) {
        this.name = name;
        this.father = null;
        this.commodities = new ArrayList<Commodity>();
        this.fieldOptions = new ArrayList<HashSet<String>>();
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

    public ArrayList<HashSet<String>> getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(ArrayList<HashSet<String>> fieldOptions) {
        this.fieldOptions = fieldOptions;
    }
}
