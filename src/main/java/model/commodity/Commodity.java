package model.commodity;

import com.gilecode.yagson.YaGson;
import controller.data.YaDataManager;
import model.Statistics;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.field.Field;
import model.share.Requestable;
import model.share.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Commodity implements Requestable {

    private int commodityId;
    private Status status;
    private String brand;
    private String name;
    private int price;
    private int inventory;
    private BusinessAccount seller;
    private Boolean isCommodityAvailable;
    private Category category;
    private ArrayList<Field> categorySpecifications;
    private String description;
    private ArrayList<Comment> allComments;
    private double averageScore;
    private double totalScores;
    private int numberOfScores;
    private int numberOfVisits;

    public Commodity(String brand, String name, int price,
                     BusinessAccount seller, Boolean isCommodityAvailable, Category category,
                     ArrayList<Field> categorySpecifications, String description, int amount) throws IOException {
        this.commodityId = Statistics.updatedStats.commodityId();
        status = Status.UNDER_CHECKING_FOR_CREATE;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.isCommodityAvailable = isCommodityAvailable;
        this.category = category;
        this.categorySpecifications = categorySpecifications;
        this.description = description;
        this.allComments = new ArrayList<>();
        this.averageScore = 0;
        this.inventory = amount;
        this.numberOfVisits = 0;
        this.numberOfScores = 0;
        this.totalScores = 0;
    }

    public Commodity(Commodity commodity) {
        commodityId= commodity.commodityId;
        status = commodity.status;
        brand = commodity.brand;
        name = commodity.name;
        price = commodity.price;
        inventory = commodity.inventory;
        seller = commodity.seller;
        isCommodityAvailable = commodity.isCommodityAvailable;
        category = commodity.category;
        categorySpecifications = new ArrayList<>(commodity.categorySpecifications);
        description = commodity.description;
        allComments = new ArrayList<>(commodity.allComments);
        averageScore = commodity.averageScore;
        totalScores = commodity.totalScores;
        numberOfScores = commodity.numberOfScores;
        numberOfVisits = commodity.numberOfVisits;
    }

    public int getNumberOfScores() {
        return numberOfScores;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void updateAverageScore(double score) {
        this.numberOfScores++;
        this.totalScores += score;
        this.averageScore = totalScores / numberOfScores;

    }

    public void setTotalScores(int totalScores) {
        this.totalScores = totalScores;
    }

    public void setNumberOfScores(int numberOfScores) {
        this.numberOfScores = numberOfScores;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void addObj() throws Exception {
        YaDataManager.addCommodity(this);
        YaDataManager.removeBusiness(seller);
        seller.addCommodity(this);
        YaDataManager.addBusiness(seller);
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public SimpleAccount getSeller() {
        return seller;
    }

    public Boolean getCommodityAvailable() {
        return isCommodityAvailable;
    }

    public void setCommodityAvailable(Boolean commodityAvailable) {
        isCommodityAvailable = commodityAvailable;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<Field> getCategorySpecifications() {
        return categorySpecifications;
    }

    public void setCategorySpecifications(ArrayList<Field> categorySpecifications) {
        this.categorySpecifications = categorySpecifications;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityId=" + commodityId +
                ", status=" + status +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inventory=" + inventory +
                ", seller=" + seller +
                ", isCommodityAvailable=" + isCommodityAvailable +
                ", category=" + category.getName() +
                ", description='" + description + '\'' +
                ", allComments=" + allComments.size() +
                ", averageScore=" + averageScore +
                ", numberOfScores=" + numberOfScores +
                ", numberOfVisits=" + numberOfVisits +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSeller(BusinessAccount seller) {
        this.seller = seller;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAllComments(ArrayList<Comment> allComments) {
        this.allComments = allComments;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setTotalScores(double totalScores) {
        this.totalScores = totalScores;
    }

    public static class Comparators {

        public static Comparator<Commodity> date = new Comparator<Commodity>() {
            @Override
            public int compare(Commodity o1, Commodity o2) {
                return o1.getCommodityId() - o2.getCommodityId();
            }
        };

        public static Comparator<Commodity> price = new Comparator<Commodity>() {
            @Override
            public int compare(Commodity o1, Commodity o2) {
                return o1.price - o2.price;
            }
        };

        public static Comparator<Commodity> score = new Comparator<Commodity>() {
            @Override
            public int compare(Commodity o1, Commodity o2) {
                if (o1.getAverageScore() > o2.getAverageScore())
                    return 1;
                else
                    return -1;
            }
        };
        public static Comparator<Commodity> numberOfVisits = new Comparator<Commodity>() {
            @Override
            public int compare(Commodity o1, Commodity o2) {
                return o1.numberOfVisits - o2.numberOfVisits;
            }
        };
    }

    public String getInformation() {
        return "commodity ID: " + this.commodityId + "\n" +
                "commodity name: " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commodity)) return false;
        Commodity commodity = (Commodity) o;
        return getCommodityId() == commodity.getCommodityId();
    }
}
