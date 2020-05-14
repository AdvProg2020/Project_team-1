package controller;

import model.DataManager;
import model.Request;

public class ManageRequestMenu extends Menu {
    private Request getDiscountCode(int id) throws Exception {
        return DataManager.getRequest(id);
    }
}
