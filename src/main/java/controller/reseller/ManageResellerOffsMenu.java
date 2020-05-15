package controller.reseller;

import controller.Menu;
import model.Commodity;
import model.Off;
import model.Request;
import model.account.BusinessAccount;
import view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageResellerOffsMenu extends Menu {

    public Off getOffById(int offId) throws Exception {
        Off off = View.resellerMenu.getBusinessAccount().getOffById(offId);
        if (off == null) {
            throw new Exception("Off ID not found");
        }
        return off;
    }

    public void addOff(ArrayList<Commodity> commodities, String startTime,
                       String endTime, int discountPercent) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d HH:mm:ss yyyy");
        BusinessAccount businessAccount = View.resellerMenu.getBusinessAccount();
        Off newOff = new Off(businessAccount, commodities, simpleDateFormat.parse(startTime),
                simpleDateFormat.parse(endTime), discountPercent);
        Request request = new Request(newOff, businessAccount);
    }
}
