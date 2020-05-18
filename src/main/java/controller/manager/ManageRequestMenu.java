package controller.manager;

import controller.data.YaDataManager;
import controller.share.Menu;
import model.account.BusinessAccount;
import model.commodity.Commodity;
import model.commodity.Off;
import model.share.Request;
import model.share.Status;

public class ManageRequestMenu extends Menu {
    public Request getRequestById(int id) throws Exception {
            return YaDataManager.getRequest(id);
    }

    public void accept(int id) throws Exception {
        Request request = getRequestById(id);
        request.getObj().setStatus(Status.VERIFIED);
        request.getObj().addObj();
        YaDataManager.removeRequest(request);
    }

    public void deleteLastObjInDataManager(Request request) throws Exception {
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
    }



   public void decline(int id) throws Exception {
        Request request = getRequestById(id);
       YaDataManager.removeRequest(request);
    }
}
