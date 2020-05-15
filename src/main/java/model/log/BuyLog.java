package model.log;

import model.Commodity;
import model.DiscountCode;
import model.account.BusinessAccount;
import model.account.SimpleAccount;

import java.util.Date;
import java.util.Set;

public class BuyLog extends TransactionLog {
    private double payedMoney;
    private DiscountCode discountByCode;
    private Set<BusinessAccount> sellers;
    private Boolean isCommodityDelivered;
    private String address;
    private String phone;
    private String postalCode;

    public BuyLog(Date date, Set<Commodity> commodities, double payedMoney, DiscountCode discountByCode,
                  String address, String phone, String postalCode) {
        super(date, commodities);
        this.payedMoney = payedMoney;
        this.discountByCode = discountByCode;
        this.sellers = null;
        for (Commodity commodity : commodities) {
            sellers.add((BusinessAccount) commodity.getSeller());
        }
        logId = generateLogID();
        isCommodityDelivered = false;
        this.phone = phone;
        this.postalCode = postalCode;
        this.address = address;
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
    protected String generateLogID() {
        String output = "BuyLog" + super.generateLogID();
        for (SimpleAccount seller : sellers) {
            output += seller.getUsername();
        }
        return output;
    }
}
