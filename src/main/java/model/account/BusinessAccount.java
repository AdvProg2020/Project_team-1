package model.account;

import model.Commodity;
import model.Off;
import model.Requestable;
import model.Status;
import model.log.SellLog;

import java.util.ArrayList;

public class BusinessAccount extends SimpleAccount implements Requestable {
    private final String VALID_BUSINESS_NAME = "^\\w{4,20}$";
    private String businessName;
    private ArrayList<SellLog> sellLogs;
    private ArrayList<Commodity> commodities;
    private ArrayList<Off> offs;
    private PersonalAccount personalAccount;
    private Status status;
    private double credit;

    public BusinessAccount(String username, String firstName, String lastName,PersonalAccount personalAccount, String email, String phoneNumber, String password, String businessName) throws Exception {
        super(username, firstName, lastName, email, phoneNumber, password);
        changeBusinessName(businessName);
        sellLogs = new ArrayList<SellLog>();
        commodities = new ArrayList<Commodity>();
        offs = new ArrayList<Off>();
        this.status = Status.UNDER_CHECKING_FOR_CREATE;
        this.personalAccount = personalAccount;
        credit = 0;
    }

    public BusinessAccount(String username, String s, String s1, String s2, String s3, String s4, String scan) {
        super();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void addObj() {
        personalAccount.setBusinessAccount(this);
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

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }

    public ArrayList<Off> getOffs() {
        return offs;
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
