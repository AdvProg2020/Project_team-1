package model.log;

import model.Commodity;
import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends TransactionLog {
    private double receivedMoney;
    private double deductedMoneyOnSale;
    private SimpleAccount buyer;
    private Boolean isCommodityDelivered;

    public SellLog(Date date, int receivedMoney, int deductedMoneyOnSale, ArrayList<Commodity> soledCommodities, SimpleAccount buyer) {
        super(date, soledCommodities);
        this.receivedMoney = receivedMoney;
        this.deductedMoneyOnSale = deductedMoneyOnSale;
        this.buyer = buyer;
        logId = generateLogID();
        isCommodityDelivered = false;
    }

    public double getReceivedMoney() {
        return receivedMoney;
    }

    public double getDeductedMoneyOnSale() {
        return deductedMoneyOnSale;
    }

    public SimpleAccount getBuyerName() {
        return buyer;
    }

    public Boolean getCommodityDelivered() {
        return isCommodityDelivered;
    }

    public void setCommodityDelivered(Boolean commodityDelivered) {
        isCommodityDelivered = commodityDelivered;
    }

    @Override
    protected String generateLogID() {
        return "SellLog" + super.generateLogID() + buyer.getUsername();
    }
}
