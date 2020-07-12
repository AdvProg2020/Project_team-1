package common.model.commodity;

import common.model.account.BusinessAccount;
import common.model.share.Requestable;
import common.model.share.Status;
import server.controller.Statistics;
import server.data.YaDataManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Off implements Requestable, Serializable {

    private int offID;
    private String ownerUsername;
    private ArrayList<Integer> commoditiesId;
    private Status status;
    private Date startTime;
    private Date endTime;
    private int discountPercent;

    public Off(String ownerUsername, ArrayList<Integer> commoditiesId, Date startTime, Date endTime, int discountPercent) throws Exception {
        this.ownerUsername = ownerUsername;
        this.startTime = startTime;
        this.endTime = endTime;
        setDiscountPercent(discountPercent);
        status = Status.UNDER_CHECKING_FOR_CREATE;
        this.commoditiesId = commoditiesId;
        offID = Statistics.updatedStats.offId();
    }

    public Off(Off off) {
        offID = off.offID;
        ownerUsername = off.ownerUsername;
        commoditiesId = new ArrayList<>(off.commoditiesId);
        status = off.status;
        startTime = new Date(off.startTime.getTime());
        endTime = new Date(off.endTime.getTime());
        discountPercent = off.discountPercent;
    }

    public int getOffID() {
        return offID;
    }

    public ArrayList<Integer> getCommoditiesId() {
        return commoditiesId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDiscountPercent(int discountPercent) throws Exception {
        if (0 < discountPercent && discountPercent < 100) {
            this.discountPercent = discountPercent;
        } else {
            throw new Exception("Invalid discount percent. Discount percent should be in range 1 to 99.");
        }
    }

    public void setCommoditiesId(ArrayList<Integer> commoditiesId) {
        this.commoditiesId = commoditiesId;
    }

    public void addToCommodities(Commodity commodity) {
        commoditiesId.add(commodity.getCommodityId());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void addObj() throws Exception {
        BusinessAccount owner = YaDataManager.getSellerWithUserName(ownerUsername);
        owner.addSale(this);
        YaDataManager.removeBusiness(owner);
        YaDataManager.addBusiness(owner);
        YaDataManager.addOff(this);
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
        StringBuilder commoditiesNames = new StringBuilder();
        for (int commodityId : commoditiesId) {
            commoditiesNames.append(commodityId);
            commoditiesNames.append("-");
        }
        return "Off{" +
                "offID='" + offID + '\'' +
                ", owner=" + ownerUsername +
                ", commodities=" + commoditiesNames.toString() +
                ", status=" + status +
                ", startTime=" + startTime.toString() +
                ", endTime=" + endTime.toString() +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
