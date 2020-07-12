package server.controller.manager;

import client.Session;
import client.view.commandline.View;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import common.model.commodity.Category;
import common.model.commodity.DiscountCode;
import common.model.share.Request;
import server.controller.share.Menu;
import server.controller.share.MenuHandler;
import server.data.YaDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ManagerMenu extends Menu {

    public void manageUsers(){
        MenuHandler.getInstance().setCurrentMenu(View.manageUsersMenu);
        View.manageUsersMenu.setPreviousMenu(View.managerMenu);
    }

    public void viewPersonalInfo(){
        View.viewPersonalInfoMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.viewPersonalInfoMenu);
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
        if (finish.compareTo(start) < 0) {
            return false;
        }
        if (discountPercentage <= 0)
            return false;
        if (maximumDiscountPrice <= 0)
            return false;
        return maximumNumberOfUse <= 0;
    }

    public void addDiscountCode(String code, Date start, Date finish, int discountPercentage, int maximumDiscountPrice,
                                int maximumNumberOfUse, ArrayList<PersonalAccount> accountArrayList) throws Exception {
        ArrayList<String> userNames = new ArrayList<>();
        for (PersonalAccount personalAccount : accountArrayList) {
            userNames.add(personalAccount.getUsername());
        }
        DiscountCode discountCode = new DiscountCode(code, start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse, userNames);
        for (PersonalAccount account : accountArrayList) {
            account.addDiscountCode(discountCode);
        }
        YaDataManager.addDiscountCode(discountCode);

        View.getDiscountCode.updateAccounts(discountCode);
    }

    public ArrayList<Category> manageCategory() throws IOException {
        MenuHandler.getInstance().setCurrentMenu(View.manageCategoryMenu);
        View.manageCategoryMenu.setPreviousMenu(View.managerMenu);
        return getAllCategories();
    }

    public ArrayList<Category> getAllCategories() throws IOException {
        return YaDataManager.getCategories();
    }

    public ArrayList<Request> manageRequest() throws IOException {
        MenuHandler.getInstance().setCurrentMenu(View.manageRequestMenu);
        View.manageRequestMenu.setPreviousMenu(View.managerMenu);
        return getAllRequests();
    }

    public ArrayList<Request> getAllRequests() throws IOException {
        return YaDataManager.getRequests();
    }

    public ManagerMenu() {
        fxmlFileAddress = "../../../fxml/HolyManager/HolyManager.fxml";
    }

    public SimpleAccount getOnlineAccount() {
        return Session.getOnlineAccount();
    }

    public ArrayList<DiscountCode> viewDiscountCodesCommand() throws Exception {
        MenuHandler.getInstance().setCurrentMenu(View.getDiscountCode);
        View.getDiscountCode.setPreviousMenu(View.managerMenu);
        return getAllDiscountCodes();
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() throws Exception {
        return YaDataManager.getDiscountCodes();
    }


}
