package model;

import model.account.BusinessAccount;

import java.util.ArrayList;
import java.util.Date;

public class Off {
    private String offID;
    private BusinessAccount owner;
    private ArrayList<Commodity> commodities;
    private Status status;
    private Date startTime;
    private Date endTime;
    private int discountPercent;

    public Off(BusinessAccount owner, Date startTime, Date endTime, int discountPercent) throws Exception {
        this.owner = owner;
        this.startTime = startTime;
        this.endTime = endTime;
        if (0 < discountPercent && discountPercent < 100){
            this.discountPercent = discountPercent;
        } else {
            throw new Exception("Invalid discount percent. Discount percent should be in range 1 to 99.");
        }
        status = Status.UNDER_CHECKING_FOR_CREATE;
        commodities = new ArrayList<Commodity>();
        offID = generateOffID();
    }

    public String getOffID() {
        return offID;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public void addToCommodities(Commodity commodity) {
        commodities.add(commodity);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    private String generateOffID() {
        return owner.getBusinessName() + startTime.toString();
    }
}
