package model.log;

import model.Commodity;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends TransactionLog {
    private int receivedMoney;
    private int deductedMoneyOnSale;
    private String buyerName;
    private Boolean isCommodityDelivered;

    public SellLog(int logId, Date date, int receivedMoney, int deductedMoneyOnSale, ArrayList<Commodity> soledCommodities, String buyerName, Boolean isCommodityDelivered) {
        super(logId, date , soledCommodities);
        this.receivedMoney = receivedMoney;
        this.deductedMoneyOnSale = deductedMoneyOnSale;
        this.buyerName = buyerName;
        this.isCommodityDelivered = isCommodityDelivered;
    }

    public int getReceivedMoney() {
        return receivedMoney;
    }

    public int getDeductedMoneyOnSale() {
        return deductedMoneyOnSale;
    }


    public String getBuyerName() {
        return buyerName;
    }

    public Boolean getCommodityDelivered() {
        return isCommodityDelivered;
    }

    public void setCommodityDelivered(Boolean commodityDelivered) {
        isCommodityDelivered = commodityDelivered;
    }
}
