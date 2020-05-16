package controller;

import model.*;
import model.account.BusinessAccount;

public class ManageRequestMenu extends Menu {
    public Request getRequestById(int id) throws Exception {
            return DataManager.getRequest(id);
    }

    public void accept(int id) throws Exception {
        Request request = getRequestById(id);
        request.getObj().setStatus(Status.VERIFIED);
        deleteLastObjInDataManager(request);
        request.getObj().addObj();
        DataManager.deleteRequest(request);
    }

    public void deleteLastObjInDataManager(Request request) throws Exception {
        if (request.getObj() instanceof Off){
            Off off = (Off) request.getObj();
            DataManager.deleteOff(off);
        }
        if (request.getObj() instanceof Commodity){
            Commodity commodity = (Commodity) request.getObj();
            DataManager.deleteCommodity(commodity);
        }
        if (request.getObj() instanceof BusinessAccount){
            BusinessAccount businessAccount = (BusinessAccount) request.getObj();
            DataManager.deleteBusinessAccount(businessAccount.getUsername());
        }
    }



   public void decline(int id) throws Exception {
        Request request = getRequestById(id);
        DataManager.deleteRequest(request);
    }
}
