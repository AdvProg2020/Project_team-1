package common.model.log;

import common.model.commodity.Commodity;
import common.model.account.SimpleAccount;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

public class SellLog extends TransactionLog {
    private double receivedMoney;
    private double deductedMoneyOnSale;
    private SimpleAccount buyer;
    private Boolean isCommodityDelivered;

    public SellLog(Date date, int receivedMoney, int deductedMoneyOnSale, Set<Commodity> soledCommodities,
                   SimpleAccount buyer) throws IOException {
        super(date, soledCommodities);
        this.receivedMoney = receivedMoney;
        this.deductedMoneyOnSale = deductedMoneyOnSale;
        this.buyer = buyer;
        isCommodityDelivered = false;
    }

    public String getBuyerUsername() {
        return buyer.getUsername();
    }

    public double getReceivedMoney() {
        return receivedMoney;
    }

    public double getDeductedMoneyOnSale() {
        return deductedMoneyOnSale;
    }

    public SimpleAccount getBuyer() {
        return buyer;
    }

    public Boolean getCommodityDelivered() {
        return isCommodityDelivered;
    }

    public void setCommodityDelivered(Boolean commodityDelivered) {
        isCommodityDelivered = commodityDelivered;
    }

    @Override
    public String toString() {
        StringBuilder commoditiesNames = new StringBuilder();
        for (Commodity commodity : commodities) {
            commoditiesNames.append(commodity.getName());
            commoditiesNames.append("-");
        }
        return "SellLog{" +
                "receivedMoney=" + receivedMoney +
                ", deductedMoneyOnSale=" + deductedMoneyOnSale +
                ", buyer=" + buyer.getUsername() +
                ", isCommodityDelivered=" + isCommodityDelivered +
                ", logId='" + logId + '\'' +
                ", date=" + date.toString() +
                ", commodities=" + commoditiesNames.toString() +
                "}";

    }
}
