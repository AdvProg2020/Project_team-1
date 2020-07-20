package common.model.account;

import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.exception.InvalidAccountInfoException;
import common.model.log.SellLog;
import common.model.share.Requestable;
import common.model.share.Status;
import server.dataManager.YaDataManager;

import java.io.IOException;
import java.util.ArrayList;

public class BusinessAccount extends SimpleAccount implements Requestable {
    private final transient String VALID_BUSINESS_NAME = "^\\w{4,20}$";
    private String businessName;
    private ArrayList<SellLog> sellLogs;
    private ArrayList<Integer> commoditiesId;
    private ArrayList<Integer> offsId;
    private double credit;
    private Status status;

    public BusinessAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password, String businessName) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "reseller");
        changeBusinessName(businessName);
        sellLogs = new ArrayList<>();
        commoditiesId = new ArrayList<>();
        offsId = new ArrayList<>();
        credit = 0.0;
        status = Status.UNDER_CHECKING_FOR_CREATE;
    }

    public BusinessAccount(String username, String firstName, String lastName, String email, String phoneNumber,
                           String password, String businessName, String imagePath) throws InvalidAccountInfoException {
        super(username, firstName, lastName, email, phoneNumber, password, "reseller", imagePath);
        changeBusinessName(businessName);
        sellLogs = new ArrayList<>();
        commoditiesId = new ArrayList<>();
        offsId = new ArrayList<>();
        credit = 0.0;
        status = Status.UNDER_CHECKING_FOR_CREATE;
    }

    public ArrayList<SellLog> getSellLogs() {
        return sellLogs;
    }

    public void addSellLog(SellLog sellLog) {
        sellLogs.add(sellLog);
    }

    public ArrayList<Integer> getCommoditiesId() {
        return commoditiesId;
    }

    public Commodity getCommodityById(int productId) throws Exception {
        if (commoditiesId.contains(productId)) {
            return YaDataManager.getCommodityById(productId);
        }
        return null;
    }

    public void removeCommodity(Commodity commodity) {
        commoditiesId.remove(commodity.getCommodityId());
    }

    public void addCommodity(Commodity commodity) {
        commoditiesId.add(commodity.getCommodityId());
        System.out.println("mikhad mano add kone");
    }

    public ArrayList<Integer> getOffsId() {
        return offsId;
    }

    public Off getOffById(int id) throws IOException {
        if (offsId.contains(id)) {
            return YaDataManager.getOffWithId(id);
        }
        return null;
    }

    public void addSale(Off off) {
        offsId.add(off.getOffID());
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
