package controller;

import model.DataManager;
import model.DiscountCode;
import model.Request;
import model.Status;

public class ManageRequestMenu extends Menu {
    public Request getRequestById(int id) throws Exception {
            return DataManager.getRequest(id);
    }

    public void accept(int id) throws Exception {
        Request request = getRequestById(id);
        request.getObj().setStatus(Status.VERIFIED);
        request.getObj().addObj();
        DataManager.deleteRequest(request);
    }

   public void decline(int id) throws Exception {
        Request request = getRequestById(id);
        DataManager.deleteRequest(request);
    }
}
