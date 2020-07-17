package common.model.log;

import server.dataManager.YaDataManager;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

public class SellLog extends TransactionLog {
    private double receivedMoney;
    private double deductedMoneyOnSale;
    private String buyerUsername;
    private Boolean isCommodityDelivered;

    public SellLog(Date date, int receivedMoney, int deductedMoneyOnSale, Set<Integer> soledCommodities,
                   String buyerUsername) throws IOException {
        super(date, soledCommodities);
        this.receivedMoney = receivedMoney;
        this.deductedMoneyOnSale = deductedMoneyOnSale;
        this.buyerUsername = buyerUsername;
        isCommodityDelivered = false;
    }

    public double getReceivedMoney() {
        return receivedMoney;
    }

    public double getDeductedMoneyOnSale() {
        return deductedMoneyOnSale;
    }

    public String getBuyerUsername() {
        return buyerUsername;
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
        for (int commodityId : commoditiesId) {
            try {
                commoditiesNames.append(YaDataManager.getCommodityById(commodityId).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            commoditiesNames.append("-");
        }
        return "SellLog{" +
                "receivedMoney=" + receivedMoney +
                ", deductedMoneyOnSale=" + deductedMoneyOnSale +
                ", buyer=" + buyerUsername +
                ", isCommodityDelivered=" + isCommodityDelivered +
                ", logId='" + logId + '\'' +
                ", date=" + date.toString() +
                ", commodities=" + commoditiesNames.toString() +
                "}";

    }
}
