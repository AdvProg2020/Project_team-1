package controller.reseller;

import controller.Menu;
import model.Commodity;
import model.Session;
import model.account.BusinessAccount;

public class ResellerMenu extends Menu {
    public BusinessAccount getBusinessAccount() {
        return (BusinessAccount)Session.getOnlineAccount();
    }

    public void addProduct() throws Exception {
        //create request
    }
    /*        viewPersonalInfo
              viewCompanyInfo
              viewSalesHistory
              manageProducts
              addProduct
              removeProduct
              showCategories
              viewOffs
              viewBalance
     */
}
