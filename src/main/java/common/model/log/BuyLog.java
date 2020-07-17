package common.model.log;

import server.dataManager.YaDataManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BuyLog extends TransactionLog {
    private double payedMoney;
    private double deductedMoney;
    private String discountCode;
    private Set<String> sellersUsername;
    private boolean isCommodityDelivered;

    public BuyLog(Date date, Set<Integer> commodities, double payedMoney, double deductedMoney,
                  String discountCode) throws Exception {
        super(date, commodities);
        this.payedMoney = payedMoney;
        this.deductedMoney = deductedMoney;
        this.discountCode = discountCode;
        this.sellersUsername = new HashSet<>();
        for (int commodityId : commodities) {
            sellersUsername.add(YaDataManager.getCommodityById(commodityId).getSellerUsername());
        }
        isCommodityDelivered = false;
    }

    public double getDeductedMoney() {
        return deductedMoney;
    }

    public Set<String> getSellersUsername() {
        return sellersUsername;
    }

    public double getPayedMoney() {
        return payedMoney;
    }

    public String getDiscountCode() {
        return discountCode;
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
        return "BuyLog{" +
                "payedMoney=" + payedMoney +
                ", deductedMoney=" + deductedMoney +
                ", sellers=" + sellersUsername +
                ", isCommodityDelivered=" + isCommodityDelivered +
                ", logId=" + logId +
                ", date=" + date +
                ", commodities=" + commoditiesNames.toString() +
                '}';
    }
}
