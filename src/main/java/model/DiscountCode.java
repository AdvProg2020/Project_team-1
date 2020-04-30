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
                        int maximumNumberOfUses, ArrayList<SimpleAccount> accounts) throws Exception {
        if (finishDate.compareTo(startDate) < 0) {
            throw new Exception("dates are not valid");
        }
        setMaximumNumberOfUses(maximumNumberOfUses);
        setDiscountPercentage(discountPercentage);
        setMaximumDiscountPrice(maximumDiscountPrice);
        this.code = code;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.accounts = accounts;
    }

    public String getCode() {
        return code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) throws Exception {
        if (finishDate.compareTo(startDate) < 0) {
            throw new Exception("dates are not valid");
        }
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) throws Exception {
        if (finishDate.compareTo(startDate) < 0) {
            throw new Exception("dates are not valid");
        }
        this.finishDate = finishDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) throws Exception {
        if (discountPercentage < 1) {
            throw new Exception("not a valid percentage");
        }
        this.discountPercentage = discountPercentage;
    }

    public int getMaximumDiscountPrice() {
        return maximumDiscountPrice;
    }

    public void setMaximumDiscountPrice(int maximumDiscountPrice) throws Exception {
        if (maximumDiscountPrice <= 0) {
            throw new Exception("not a valid price");
        }
        this.maximumDiscountPrice = maximumDiscountPrice;
    }

    public int getMaximumNumberOfUses() {
        return maximumNumberOfUses;
    }

    public void setMaximumNumberOfUses(int maximumNumberOfUses) throws Exception {
        if (maximumNumberOfUses <= 0) {
            throw new Exception("not a valid number");
        }
        this.maximumNumberOfUses = maximumNumberOfUses;
    }

    public ArrayList<SimpleAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<SimpleAccount> accounts) {
        this.accounts = accounts;
    }
}
