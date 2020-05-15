package controller.reseller;

import controller.Menu;
import controller.MenuHandler;
import model.*;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.field.Field;
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

    public Category getCategoryByName(String categoryString) throws Exception {
        for (Category category : DataManager.getAllCategories()) {
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
        DataManager.addRequest(request);
    }

    public void removeProduct(int productId) throws Exception {
        Commodity commodity = DataManager.getCommodityById(productId);
        getBusinessAccount().removeCommodity(commodity);
        DataManager.deleteCommodity(commodity);
    }
}
