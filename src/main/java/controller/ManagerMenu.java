package controller;

import model.Category;
import model.DataManager;
import model.DiscountCode;
import model.Request;
import model.account.SimpleAccount;
import view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ManagerMenu extends Menu {

    public void manageAllProducts(){
        HandleMenu.setMenu(View.manageAllProducts);
        View.manageAllProducts.setPreviousMenu(View.managerMenu);
    }

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
        return maximumNumberOfUse > 0;
    }

    public void addDiscountCode(String code, Date start, Date finish, int discountPercentage, int maximumDiscountPrice,
                                int maximumNumberOfUse, ArrayList<SimpleAccount> accountArrayList) throws Exception {
        DataManager.addDiscountCode(new DiscountCode(code, start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse, accountArrayList));

    }

    public Category[] manageCategory() throws FileNotFoundException {
        HandleMenu.setMenu(View.manageCategoryMenu);
        View.manageCategoryMenu.setPreviousMenu(View.managerMenu);
        return getAllCategories();
    }

    public Category[] getAllCategories() throws FileNotFoundException {
        return DataManager.getAllCategories();
    }

    public Request[] manageRequest() throws IOException {
        HandleMenu.setMenu(View.manageRequestMenu);
        View.manageRequestMenu.setPreviousMenu(View.managerMenu);
        return getAllRequests();
    }

    public Request[] getAllRequests() throws IOException {
        return DataManager.getAllRequests();
    }

    public SimpleAccount getOnlineAccount() {
        return DataManager.getOnlineAccount();
    }

    public DiscountCode[] viewDiscountCodesCommand() throws Exception {
        HandleMenu.setMenu(View.getDiscountCode);
        View.getDiscountCode.setPreviousMenu(View.managerMenu);
        return getAllDiscountCodes();
    }

    public DiscountCode[] getAllDiscountCodes() throws Exception {
        return DataManager.getAllDiscountCodes();
    }


}
