package common.model.account;

import server.data.YaDataManager;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.exception.InvalidAccountInfoException;
import common.model.log.SellLog;
import common.model.share.Requestable;
import common.model.share.Status;

import java.util.ArrayList;

public class BusinessAccount extends SimpleAccount implements Requestable {
    private final transient String VALID_BUSINESS_NAME = "^\\w{4,20}$";
    private String businessName;
    private ArrayList<SellLog> sellLogs;
    private ArrayList<Commodity> commodities;
    private ArrayList<Off> offs;
    private double credit;
    private Status status;

    public BusinessAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password, String businessName) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "reseller");
        changeBusinessName(businessName);
        sellLogs = new ArrayList<SellLog>();
        commodities = new ArrayList<Commodity>();
        offs = new ArrayList<Off>();
        credit = 1000;
        status = Status.UNDER_CHECKING_FOR_CREATE;
    }

    public BusinessAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password, String businessName, String imagePath) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "reseller", imagePath);
        changeBusinessName(businessName);
        sellLogs = new ArrayList<SellLog>();
        commodities = new ArrayList<Commodity>();
        offs = new ArrayList<Off>();
        credit = 1000;
        status = Status.UNDER_CHECKING_FOR_CREATE;
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
        System.out.println("mikhad mano add kone");
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

    public void changeBusinessName(String businessName) throws InvalidAccountInfoException {
        if (businessName.matches(VALID_BUSINESS_NAME)) {
            this.businessName = businessName;
        } else {
            throw new InvalidAccountInfoException("Invalid business name. Business name just contain 4 to 20 alphanumerical characters");
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
        return "BusinessAccount{" +
                "businessName='" + businessName + '\'' +
                ", credit=" + credit +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public void addOff(Off off) {
        offs.add(off);
    }

    @Override
    public String getInformation() {
        return super.getInformation() + "\ncompany name: " + this.businessName;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void addObj() throws Exception {
        YaDataManager.addBusiness(this);
    }

    @Override
    public Status getStatus() {
        return status;
    }
}