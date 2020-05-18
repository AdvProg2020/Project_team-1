package model.log;

import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.commodity.Commodity;
import model.commodity.DiscountCode;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

public class BuyLog extends TransactionLog {
    private double payedMoney;
    private double deductedMoney;
    private DiscountCode discountByCode;
    private Set<BusinessAccount> sellers;
    private boolean isCommodityDelivered;

    public BuyLog(Date date, Set<Commodity> commodities, double payedMoney, double deductedMoney,
                  DiscountCode discountByCode) throws IOException {
        super(date, commodities);
        this.payedMoney = payedMoney;
        this.deductedMoney = deductedMoney;
        this.discountByCode = discountByCode;
        this.sellers = null;
        for (Commodity commodity : commodities) {
            sellers.add((BusinessAccount) commodity.getSeller());
        }
        isCommodityDelivered = false;
    }

    public double getDeductedMoney() {
        return deductedMoney;
    }

    public Set<BusinessAccount> getSellers() {
        return sellers;
    }

    public double getPayedMoney() {
        return payedMoney;
    }

    public DiscountCode getDiscountByCode() {
        return discountByCode;
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
        return "BuyLog{" +
                "payedMoney=" + payedMoney +
                ", deductedMoney=" + deductedMoney +
                ", sellers=" + sellers +
                ", isCommodityDelivered=" + isCommodityDelivered +
                ", logId=" + logId +
                ", date=" + date +
                ", commodities=" + commoditiesNames.toString() +
                '}';
    }
}
