package model;

import model.account.SimpleAccount;

import java.util.ArrayList;

public class Commodity implements Requestable{
    private int commodityId;
    private Status status;
    private String brand;
    private String name;
    private int price;
    private int inventory;
    private SimpleAccount seller;
    private Boolean isCommodityAvailable;
    private Category category;
    private ArrayList<String> categorySpecifications;
    private String description;
    private ArrayList<Comment> allComments;
    private int averageScore;
    private int totalScores;
    private int numberOfScores;

    public Commodity(int commodityId,Status status, String brand, String name, int price,
                     SimpleAccount seller, Boolean isCommodityAvailable, Category category, ArrayList<String> categorySpecifications,
                     String description, int averageScore, int amount) {
        this.commodityId = commodityId;
        this.status = status;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.isCommodityAvailable = isCommodityAvailable;
        this.category = category;
        this.categorySpecifications = categorySpecifications;
        this.description = description;
        this.allComments = new ArrayList<Comment>();
        this.averageScore = averageScore;
        this.inventory = amount;
        SuperMarket.getAllCommodities().add(this);
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityAvailable(Boolean commodityAvailable) {
        isCommodityAvailable = commodityAvailable;
    }

    public void setCategorySpecifications(ArrayList<String> categorySpecifications) {
        this.categorySpecifications = categorySpecifications;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public void setTotalScores(int totalScores) {
        this.totalScores = totalScores;
    }

    public void setNumberOfScores(int numberOfScores) {
        this.numberOfScores = numberOfScores;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public Status getStatus() {
        return status;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public SimpleAccount getSeller() {
        return seller;
    }

    public Boolean getCommodityAvailable() {
        return isCommodityAvailable;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<String> getCategorySpecifications() {
        return categorySpecifications;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public int getAverageScore() {
        return averageScore;
    }

}
