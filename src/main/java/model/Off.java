package model;

import model.account.BusinessAccount;

import java.util.ArrayList;
import java.util.Date;

public class Off implements Requestable {

    private static int lastId = 0;
    private int offID;
    private BusinessAccount owner;
    private ArrayList<Commodity> commodities;
    private Status status;
    private Date startTime;
    private Date endTime;
    private int discountPercent;

    public Off(BusinessAccount owner, ArrayList<Commodity> commodities, Date startTime, Date endTime, int discountPercent) throws Exception {
        this.owner = owner;
        this.startTime = startTime;
        this.endTime = endTime;
        setDiscountPercent(discountPercent);
        status = Status.UNDER_CHECKING_FOR_CREATE;
        this.commodities = commodities;
        offID = lastId++;
    }

    public Off(Off off) {
        offID = off.offID;
        owner = off.owner;
        commodities = new ArrayList<>(off.commodities);
        status = off.status;
        startTime = new Date(off.startTime.getTime());
        endTime = new Date(off.endTime.getTime());
        discountPercent = off.discountPercent;
    }

    public int getOffID() {
        return offID;
    }

    public void setCommodities(ArrayList<Commodity> commodities) {
        this.commodities = commodities;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDiscountPercent(int discountPercent) throws Exception{
        if (0 < discountPercent && discountPercent < 100) {
            this.discountPercent = discountPercent;
        } else {
            throw new Exception("Invalid discount percent. Discount percent should be in range 1 to 99.");
        }
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

    @Override
    public void addObj() throws Exception {
        DataManager.addOff(this);
    }

    public boolean isActive() {
        Date now = new Date();
        return this.startTime.compareTo(now) < 0 && now.compareTo(this.endTime) < 0;
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

    @Override
    public String toString() {
        return "Off{" +
                "offID='" + offID + '\'' +
                ", owner=" + owner +
                ", commodities=" + commodities +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
