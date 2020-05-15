package controller.reseller;

import controller.Menu;
import model.Commodity;
import model.Session;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.log.SellLog;
import view.View;

import java.util.ArrayList;

public class ManageResellerProductsMenu extends Menu {

    public Commodity getCommodityById(int productId) throws Exception {
        Commodity commodity = View.resellerMenu.getBusinessAccount().getCommodityById(productId);
        if (commodity == null) {
            throw new Exception("Commodity not found");
        }
        return commodity;
    }

    public ArrayList<SimpleAccount> getBuyersById(int productId) throws Exception {
        ArrayList<SimpleAccount> simpleAccountArrayList = new ArrayList<>();
        Commodity commodity = getCommodityById(productId);
        BusinessAccount businessAccount = (BusinessAccount) Session.getOnlineAccount();
        for (SellLog sellLog : businessAccount.getSellLogs()) {
            if (sellLog.getCommodities().contains(commodity)) {
                simpleAccountArrayList.add(sellLog.getBuyer());
            }
        }
        return simpleAccountArrayList;
    }

    /*
        viewProduct
        viewBuyers
        editProduct
     */
}
