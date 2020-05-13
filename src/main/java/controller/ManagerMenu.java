package controller;

import model.Commodity;
import model.DiscountCode;
import model.Request;
import model.SuperMarket;
import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.Date;

public class ManagerMenu extends Menu {

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean checkCreatDiscountCodeErrors(Date start, Date finish, int discountPercentage, int maximumDiscountPrice,
                              int maximumNumberOfUse) {
        if (finish.compareTo(start) > 0)
            return false;
        if (discountPercentage <= 0)
            return false
        if (maximumDiscountPrice <= 0)
            return false;
        if (maximumNumberOfUse <= 0)
            return false;

        return true;
    }

    public ArrayList<SimpleAccount> getAllAccounts() {
        return SuperMarket.getAllAccounts();
    }

    public ArrayList<Commodity> getAllCommodities() {
        return SuperMarket.getAllCommodities();
    }

    public ArrayList<DiscountCode> getAllDiscounts() {
        return SuperMarket.getAllDiscountCodes();
    }

    public ArrayList<Request> getAllRequests() {
        return SuperMarket.getAllRequests();
    }

    public void createNewDiscount(DiscountCode discountCode) {
        SuperMarket.addToDiscounts(discountCode);
    }
}
