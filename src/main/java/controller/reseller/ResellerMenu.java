package controller.reseller;

import controller.Menu;
import controller.MenuHandler;
import model.Commodity;
import model.DataManager;
import model.Session;
import model.account.BusinessAccount;
import view.View;

import java.util.ArrayList;

public class ResellerMenu extends Menu {
    public BusinessAccount getBusinessAccount() {
        return (BusinessAccount)Session.getOnlineAccount();
    }

    public ArrayList<Commodity> manageCommodities() throws Exception {
        ArrayList<Commodity> commodityArrayList = getBusinessAccount().getCommodities();
        MenuHandler.getInstance().setCurrentMenu(View.manageResellerProductsMenu);
        return commodityArrayList;
    }

    public void addProduct() throws Exception {
        //create request
    }

    public void removeProduct(int productId) throws Exception {
        Commodity commodity = DataManager.getCommodityById(productId);
        getBusinessAccount().removeCommodity(commodity);
        DataManager.deleteCommodity(commodity);
    }
}
