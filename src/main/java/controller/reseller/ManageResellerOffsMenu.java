package controller.reseller;

import controller.Menu;
import model.Commodity;
import model.DataManager;
import model.Off;
import model.Request;
import model.account.BusinessAccount;
import view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ManageResellerOffsMenu extends Menu {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d HH:mm:ss yyyy");

    public Off getOffById(int offId) throws Exception {
        Off off = View.resellerMenu.getBusinessAccount().getOffById(offId);
        if (off == null) {
            throw new Exception("Off ID not found");
        }
        return off;
    }

    public void addOff(ArrayList<Commodity> commodities, String startTime,
                       String endTime, int discountPercent) throws Exception {
        BusinessAccount businessAccount = View.resellerMenu.getBusinessAccount();
        Off newOff = new Off(businessAccount, commodities, simpleDateFormat.parse(startTime),
                simpleDateFormat.parse(endTime), discountPercent);
        Request request = new Request(newOff, businessAccount);
        DataManager.addRequest(request);
    }

    public void editOff(Off oldOff, ArrayList<Commodity> removedProduct, ArrayList<Commodity> addedProduct, String startTime,
                        String endTime, int discountPercent) throws Exception {
        ArrayList<Commodity> commoditiesInOff = new ArrayList<>(addedProduct);

        for (Commodity commodity : oldOff.getCommodities()) {
            if (!removedProduct.contains(commodity)) {
                commoditiesInOff.add(commodity);
            }
        }
        BusinessAccount businessAccount = View.resellerMenu.getBusinessAccount();
        Off newOff = new Off(businessAccount, commoditiesInOff,
                (startTime.equals("-"))?(oldOff.getStartTime()):(simpleDateFormat.parse(startTime)),
                (endTime.equals("-"))?(oldOff.getEndTime()):(simpleDateFormat.parse(endTime)),
                (discountPercent == -1)?(oldOff.getDiscountPercent()):(discountPercent));
        Request request = new Request(newOff, businessAccount);
        DataManager.addRequest(request);
        // Todo remove old off????????????
    }
}
