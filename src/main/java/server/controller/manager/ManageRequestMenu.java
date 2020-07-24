package server.controller.manager;

import common.model.account.BusinessAccount;
import common.model.commodity.Comment;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.share.Request;
import common.model.share.Status;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class ManageRequestMenu {
    public Request getRequestById(int id) throws Exception {
        return YaDataManager.getRequest(id);
    }

    public void accept(int id) throws Exception {
        Request request = getRequestById(id);
        request.getObj().setStatus(Status.VERIFIED);
        request.getObj().addObj();
        updateRequests(request);
    }

    public void updateRequests(Request request) throws Exception {
        YaDataManager.removeRequest(request);
        YaDataManager.addRequest(request);
//        if (request.getObj() instanceof Commodity){
//            YaDataManager.removeCommodity((Commodity)request.getObj());
//            YaDataManager.addCommodity((Commodity)request.getObj());
//        }
//        if (request.getObj() instanceof Comment){
//            YaDataManager.removeCommodity(((Comment)request.getObj()).getCommodity());
//            YaDataManager.addCommodity(((Comment)request.getObj()).getCommodity());
//        }
        if (request.getObj() instanceof Off) {
            Off off = (Off) request.getObj();
            YaDataManager.removeOff(off);
            YaDataManager.addOff(off);
            BusinessAccount owner = YaDataManager.getSellerWithUserName(off.getOwnerUsername());
            if (!owner.getOffsId().contains(off.getOffID())) {
                owner.getOffsId().add(off.getOffID());
            }
        }
        if (request.getObj() instanceof Commodity) {
            Commodity commodity = (Commodity) request.getObj();
            YaDataManager.removeCommodity(commodity);
            YaDataManager.addCommodity(commodity);
            BusinessAccount owner = YaDataManager.getSellerWithUserName(commodity.getSellerUsername());
            if (!owner.getCommoditiesId().contains(commodity.getCommodityId())) {
                owner.getCommoditiesId().add(commodity.getCommodityId());
            }
        }
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
