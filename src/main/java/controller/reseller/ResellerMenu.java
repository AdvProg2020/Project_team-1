package controller.reseller;

import controller.Menu;
import controller.MenuHandler;
import model.*;
import model.account.BusinessAccount;
import model.field.Field;
import view.View;

import java.util.ArrayList;

public class ResellerMenu extends Menu {
    public BusinessAccount getBusinessAccount() {
        return (BusinessAccount)Session.getOnlineAccount();
    }

    public ArrayList<Commodity> manageCommodities() throws Exception {
        ArrayList<Commodity> commodityArrayList = getBusinessAccount().getCommodities();
        View.manageResellerProductsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.manageResellerProductsMenu);
        return commodityArrayList;
    }

    public ArrayList<Off> manageOffs() throws Exception {
        ArrayList<Off> offArrayList = getBusinessAccount().getOffs();
        View.manageResellerOffMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.manageResellerOffMenu);
        return offArrayList;
    }

    public Category getCategoryByName(String categoryString) throws Exception {
        for (Category category : YaDataManager.getCategories()) {
            if (category.getName().equalsIgnoreCase(categoryString)) {
                return category;
            }
        }
        throw new Exception("Category not found");
    }

    public void addProduct(String brand, String name, int price, Category category,
                           ArrayList<Field> categorySpecifications, String description, int amount) throws Exception {
        BusinessAccount businessAccount = getBusinessAccount();
        Commodity newCommodity = new Commodity(brand, name, price, businessAccount, true,
                category, categorySpecifications, description, amount);
        Request request = new Request(newCommodity, businessAccount);
        YaDataManager.addRequest(request);
    }

    public void removeProduct(int productId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(productId);
        getBusinessAccount().removeCommodity(commodity);
        YaDataManager.removeCommodity(commodity);
    }
}
