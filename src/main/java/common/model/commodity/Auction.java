package common.model.commodity;

import java.util.Date;

public class Auction {
    private String ownerUsername;
    private int commodityId;
    private Date deadline;
    private String topBidder;
    private int topBid;

    public Auction(String ownerUsername, int commodityId, Date deadline, int topBid) throws Exception {
        this.ownerUsername = ownerUsername;
        this.commodityId = commodityId;
        this.deadline = deadline;
        this.topBid = topBid;
        this.topBidder = null;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void newBid(String username, int offer) {
        this.topBid = offer;
        topBidder = username;
    }

    public String getTopBidder() {
        return topBidder;
    }

    public int getTopBid() {
        return topBid;
    }
}
