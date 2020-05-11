package model.log;

import model.Commodity;
import model.DiscountCode;
import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends TransactionLog {
    private double payedMoney;
    private DiscountCode discountByCode;
    private SimpleAccount seller;
    private Boolean isCommodityDelivered;
    private String address;
    private String phone;
    private String postalCode;

    public BuyLog(Date date, ArrayList<Commodity> commodities, double payedMoney, DiscountCode discountByCode,
                  SimpleAccount sellerو, String address, String phone, String postalCode) {
        super(date, commodities);
        this.payedMoney = payedMoney;
        this.discountByCode = discountByCode;
        this.seller = seller;
        logId = generateLogID();
        isCommodityDelivered = false;
        this.phone = phone;
        this.postalCode = postalCode;
        this.address = address;
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
