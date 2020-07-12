package server.controller.manager;

import server.data.YaDataManager;
import server.controller.share.Menu;
import common.model.share.Request;
import common.model.share.Status;

import java.io.IOException;

public class ManageRequestMenu extends Menu {
    public Request getRequestById(int id) throws Exception {
            return YaDataManager.getRequest(id);
    }

    public ManageRequestMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/ManageRequests.fxml";
    }

    public void accept(int id) throws Exception {
        Request request = getRequestById(id);
        request.getObj().setStatus(Status.VERIFIED);
        request.getObj().addObj();
        updateRequests(request);
        return;
    }

    public void updateRequests(Request request) throws IOException {
        YaDataManager.removeRequest(request);
        YaDataManager.addRequest(request);
        /*if (request.getObj() instanceof Commodity){
            YaDataManager.removeCommodity((Commodity)request.getObj());
            YaDataManager.addCommodity((Commodity)request.getObj());
        }
        if (request.getObj() instanceof Comment){
            YaDataManager.removeCommodity(((Comment)request.getObj()).getCommodity());
            YaDataManager.addCommodity(((Comment)request.getObj()).getCommodity());
        }
        if (request.getObj() instanceof Off){
            YaDataManager.removeOff((Off) request.getObj());
            YaDataManager.addOff((Off) request.getObj());
        }*/
    }

    public void deleteRequest(int id) throws Exception {
        Request request = YaDataManager.getRequest(id);
        YaDataManager.removeRequest(request);
    }

   /* public void deleteLastObjInDataManager(Request request) throws Exception {
        if (request.getObj() instanceof Off){
            Off off = (Off) request.getObj();
            YaDataManager.removeOff(off);
        }
        if (request.getObj() instanceof Commodity){
            Commodity commodity = (Commodity) request.getObj();
            YaDataManager.removeCommodity(commodity);
        }
        if (request.getObj() instanceof BusinessAccount){
            BusinessAccount businessAccount = (BusinessAccount) request.getObj();
            YaDataManager.deleteBusinessAccount(businessAccount.getUsername());
        }
    }*/



   public void decline(int id) throws Exception {
        Request request = getRequestById(id);
        request.getObj().setStatus(Status.DECLINED);
        updateRequests(request);
    }
}
