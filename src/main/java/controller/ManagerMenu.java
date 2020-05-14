package controller;

import model.Commodity;
import model.DataManager;
import model.DiscountCode;
import model.Request;
import model.account.SimpleAccount;

import javax.xml.crypto.Data;
import java.io.IOException;
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

    public boolean checkCreateDiscountCodeErrors(Date start, Date finish, int discountPercentage, int maximumDiscountPrice,
                              int maximumNumberOfUse) {
        if (finish.compareTo(start) > 0)
            return false;
        if (discountPercentage <= 0)
            return false;
        if (maximumDiscountPrice <= 0)
            return false;
        if (maximumNumberOfUse <= 0)
            return false;

        return true;
    }

    public void addDiscountCode(String code,Date start, Date finish, int discountPercentage, int maximumDiscountPrice,
                               int maximumNumberOfUse,ArrayList<SimpleAccount> accountArrayList) throws Exception {
        DataManager.addDiscountCode(new DiscountCode(code,start,finish,discountPercentage,maximumDiscountPrice,maximumNumberOfUse,accountArrayList));

    }

    public Request[] getAllRequests() throws IOException {
        return DataManager.getAllRequests();
    }

    public SimpleAccount getOnlineAccount(){
        return DataManager.getOnlineAccount();
    }

    public DiscountCode[] getAllDiscountCodes() throws Exception {
        return DataManager.getAllDiscountCodes();
    }


}
