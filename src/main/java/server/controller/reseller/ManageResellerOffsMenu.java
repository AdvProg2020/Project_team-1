package server.controller.reseller;

import server.controller.share.Menu;
import server.controller.comparator.Sort;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.share.Request;
import server.data.YaDataManager;
import common.model.account.BusinessAccount;
import client.view.commandline.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ManageResellerOffsMenu extends Menu {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d yyyy");

    public ManageResellerOffsMenu() {
        fxmlFileAddress = "../../../fxml/reseller/ManageOffs.fxml";
        stageTitle = "Reseller offs";
    }

    public ArrayList<Off> sort(String field) throws Exception {
        ArrayList<Off> offs = View.resellerMenu.manageOffs();
        Sort.sortOffArrayList(offs, field);
        return offs;
    }

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
        YaDataManager.addRequest(request);
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
        YaDataManager.addRequest(request);
        YaDataManager.removeOff(oldOff);
    }
}
