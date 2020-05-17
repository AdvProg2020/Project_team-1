package model.account;

import model.Commodity;
import model.Off;
import model.log.SellLog;

import java.util.ArrayList;

public class BusinessAccount extends SimpleAccount {
    private final String VALID_BUSINESS_NAME = "^\\w{4,20}$";
    private String businessName;
    private ArrayList<SellLog> sellLogs;
    private ArrayList<Commodity> commodities;
    private ArrayList<Off> offs;
    private double credit;

    public BusinessAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password, String businessName) throws Exception {
        super(username, firstName, lastName, email, phoneNumber, password, "reseller");
        changeBusinessName(businessName);
        sellLogs = new ArrayList<SellLog>();
        commodities = new ArrayList<Commodity>();
        offs = new ArrayList<Off>();
        credit = 0;
    }

    public ArrayList<SellLog> getSellLogs() {
        return sellLogs;
    }

    public void addSellLog(SellLog sellLog) {
        sellLogs.add(sellLog);
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public Commodity getCommodityById (int productId) {
        for (Commodity commodity : commodities) {
            if (commodity.getCommodityId() == productId) {
                return commodity;
            }
        }
        return null;
    }

    public void removeCommodity(Commodity commodity) {
        commodities.remove(commodity);
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public ArrayList<Off> getOffs() {
        return offs;
    }

    public Off getOffById(int id) {
        for (Off off : offs) {
            if (off.getOffID() == id) {
                return off;
            }
        }
        return null;
    }

    public void addSale(Off off) {
        offs.add(off);
    }

    public String getBusinessName() {
        return businessName;
    }

    public void changeBusinessName(String businessName) throws Exception {
        if (businessName.matches(VALID_BUSINESS_NAME)) {
            this.businessName = businessName;
        } else {
            throw new Exception("Invalid business name. Business name just contain 4 to 20 alphanumerical characters.");
        }
    }

    public double getCredit() {
        return credit;
    }

    public void addToCredit(double amount) {
        credit += amount;
    }

    @Override
    public String toString() {
        return "businessName=" + businessName + "\n" +
                super.toString();

    }

    @Override
    public String getInformation() {
        return super.getInformation() + "\ncompany name: " + this.businessName;
    }
}
