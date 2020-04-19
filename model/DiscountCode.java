package model;

import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.Date;

public class DiscountCode {
    private String code;
    private Date startDate;
    private Date finishDate;
    private int discountPercentage;
    private int maximumDiscountPrice;
    private int maximumNumberOfUses;
    private ArrayList<SimpleAccount> accounts;

    public DiscountCode(String code, Date startDate, Date finishDate, int discountPercentage, int maximumDiscountPrice,
                        int maximumNumberOfUses, ArrayList<SimpleAccount> accounts) {
        this.code = code;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.discountPercentage = discountPercentage;
        this.maximumDiscountPrice = maximumDiscountPrice;
        this.maximumNumberOfUses = maximumNumberOfUses;
        this.accounts = accounts;
    }

    public String getCode() {
        return code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getMaximumDiscountPrice() {
        return maximumDiscountPrice;
    }

    public void setMaximumDiscountPrice(int maximumDiscountPrice) {
        this.maximumDiscountPrice = maximumDiscountPrice;
    }

    public int getMaximumNumberOfUses() {
        return maximumNumberOfUses;
    }

    public void setMaximumNumberOfUses(int maximumNumberOfUses) {
        this.maximumNumberOfUses = maximumNumberOfUses;
    }

    public ArrayList<SimpleAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<SimpleAccount> accounts) {
        this.accounts = accounts;
    }
}
