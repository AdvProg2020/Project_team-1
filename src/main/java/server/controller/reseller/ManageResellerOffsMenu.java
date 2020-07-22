package server.controller.reseller;

import client.controller.reseller.ClientResellerMenu;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.BusinessAccount;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.share.Request;
import server.controller.comparator.Sort;
import server.controller.share.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class ManageResellerOffsMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d yyyy");

    public ManageResellerOffsMenu() {
        fxmlFileAddress = "../../../fxml/reseller/ManageOffs.fxml";
        stageTitle = "Reseller offs";
    }

    public ArrayList<Off> sort(String field) throws Exception {
        ArrayList<Off> offs = ClientResellerMenu.manageOffs();
        Sort.sortOffArrayList(offs, field);
        return offs;
    }

    public Off getOffById(int offId) throws Exception {
        Off off = ClientResellerMenu.getBusinessAccount().getOffById(offId);
        if (off == null) {
            throw new Exception("Off ID not found");
        }
        return off;
    }

    public void addOff(ArrayList<Commodity> commodities, String startTime,
                       String endTime, int discountPercent) throws Exception {
        BusinessAccount businessAccount = ClientResellerMenu.getBusinessAccount();
        ArrayList<Integer> commoditiesId = new ArrayList<>();
        for (Commodity commodity : commodities) {
            commoditiesId.add(commodity.getCommodityId());
        }
        Off newOff = new Off(businessAccount.getUsername(), commoditiesId, simpleDateFormat.parse(startTime),
                simpleDateFormat.parse(endTime), discountPercent);
        Request request = new Request(newOff, businessAccount.getUsername());
        outputStream.writeUTF("add request " + yaGson.toJson(request, new TypeToken<Request>() {
        }.getType()));
        outputStream.flush();
    }

    public void editOff(Off oldOff, ArrayList<Commodity> removedProduct, ArrayList<Commodity> addedProduct, String startTime,
                        String endTime, int discountPercent) throws Exception {
        ArrayList<Integer> commoditiesIdInOff = new ArrayList<>();
        for (Commodity commodity : addedProduct) {
            commoditiesIdInOff.add(commodity.getCommodityId());
        }
        for (int commodityId : oldOff.getCommoditiesId()) {
            outputStream.writeUTF("send commodity with id " + commodityId);
            outputStream.flush();
            Commodity commodity = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Commodity>() {
            }.getType());
            if (!removedProduct.contains(commodity)) {
                commoditiesIdInOff.add(commodityId);
            }
        }
        BusinessAccount businessAccount = ClientResellerMenu.getBusinessAccount();
        Off newOff = new Off(businessAccount.getUsername(), commoditiesIdInOff,
                (startTime.equals("-")) ? (oldOff.getStartTime()) : (simpleDateFormat.parse(startTime)),
                (endTime.equals("-")) ? (oldOff.getEndTime()) : (simpleDateFormat.parse(endTime)),
                (discountPercent == -1) ? (oldOff.getDiscountPercent()) : (discountPercent));
        Request request = new Request(newOff, businessAccount.getUsername());
        outputStream.writeUTF("add request " + yaGson.toJson(request, new TypeToken<Request>() {
        }.getType()));
        outputStream.flush();
    }
}
