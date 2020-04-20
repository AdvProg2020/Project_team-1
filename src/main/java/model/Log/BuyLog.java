package model.Log;

import model.Commodity;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends TransactionLog {
    private int payedMoney;
    private int discountByCode;
    private String sellerName;
    private Boolean isCommodityDelivered;

    public BuyLog(int logId, Date date, ArrayList<Commodity> commodities, int payedMoney,
                  int discountByCode, String sellerName, Boolean isCommodityDelivered) {
        super(logId, date, commodities);
        this.payedMoney = payedMoney;
        this.discountByCode = discountByCode;
        this.sellerName = sellerName;
        this.isCommodityDelivered = isCommodityDelivered;
    }

    public void setCommodityDelivered(Boolean commodityDelivered) {
        isCommodityDelivered = commodityDelivered;
    }

    public int getPayedMoney() {
        return payedMoney;
    }

    public int getDiscountByCode() {
        return discountByCode;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Boolean getCommodityDelivered() {
        return isCommodityDelivered;
    }
}
