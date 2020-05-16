package controller;

import model.*;
import model.account.SimpleAccount;
import view.View;

import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ManagerMenu extends Menu {

    public void manageUsers(){
        MenuHandler.getInstance().setCurrentMenu(View.manageUsersMenu);
        View.managerMenu.setPreviousMenu(View.managerMenu);
    }

    public void viewPrsonalInfo(){
        MenuHandler.getInstance().setCurrentMenu(View.viewPersonalInfoMenu);
        View.viewPersonalInfoMenu.setPreviousMenu(View.managerMenu);
    }

    public void manageAllProducts(){
        MenuHandler.getInstance().setCurrentMenu(View.manageAllProducts);
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

    public Category[] manageCategory() throws IOException {
        MenuHandler.getInstance().setCurrentMenu(View.manageCategoryMenu);
        View.manageCategoryMenu.setPreviousMenu(View.managerMenu);
        return getAllCategories();
    }

    public Category[] getAllCategories() throws IOException {
        return DataManager.getAllCategories();
    }

    public Request[] manageRequest() throws IOException {
        MenuHandler.getInstance().setCurrentMenu(View.manageRequestMenu);
        View.manageRequestMenu.setPreviousMenu(View.managerMenu);
        return getAllRequests();
    }

    public Request[] getAllRequests() throws IOException {
        return DataManager.getAllRequests();
    }

    public SimpleAccount getOnlineAccount() {
        return Session.getOnlineAccount();
    }

    public DiscountCode[] viewDiscountCodesCommand() throws Exception {
        MenuHandler.getInstance().setCurrentMenu(View.getDiscountCode);
        View.getDiscountCode.setPreviousMenu(View.managerMenu);
        return getAllDiscountCodes();
    }

    public DiscountCode[] getAllDiscountCodes() throws Exception {
        return DataManager.getAllDiscountCodes();
    }


}
