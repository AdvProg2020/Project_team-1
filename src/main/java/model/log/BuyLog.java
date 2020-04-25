package model.log;

import model.Commodity;
import model.DiscountCode;
import model.account.SimpleAccount;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends TransactionLog {
    private double payedMoney;
    private DiscountCode discountByCode;
    private SimpleAccount seller;
    private Boolean isCommodityDelivered;

    public BuyLog(Date date, ArrayList<Commodity> commodities, double payedMoney, DiscountCode discountByCode, SimpleAccount seller) {
        super(date, commodities);
        this.payedMoney = payedMoney;
        this.discountByCode = discountByCode;
        this.seller = seller;
        logId = generateLogID();
        isCommodityDelivered = false;
    }

    public double getPayedMoney() {
        return payedMoney;
    }

    public DiscountCode getDiscountByCode() {
        return discountByCode;
    }

    public SimpleAccount getSeller() {
        return seller;
    }

    public Boolean getCommodityDelivered() {
        return isCommodityDelivered;
    }

    public void setCommodityDelivered(Boolean commodityDelivered) {
        isCommodityDelivered = commodityDelivered;
    }

    @Override
    protected String generateLogID() {
        return "BuyLog" + super.generateLogID() + seller.getUsername();
    }
}
