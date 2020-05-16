package controller.reseller;

import controller.Menu;
import model.*;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.field.Field;
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

    public void editProduct(Commodity oldProduct, String brand, String name, int price,boolean availability, Category category,
                            ArrayList<Field> categorySpecifications, String description, int amount) throws Exception {
        BusinessAccount businessAccount = View.resellerMenu.getBusinessAccount();
        Commodity editedProduct = new Commodity((brand.equals("-"))?(oldProduct.getBrand()):(brand),
                (name.equals("-"))?(oldProduct.getName()):(name),
                (price == -1)?(oldProduct.getPrice()):(price),
                businessAccount,
                availability,
                category,
                categorySpecifications,
                (description.equals("-"))?(oldProduct.getDescription()):(description),
                (amount == -1)?(oldProduct.getInventory()):(amount));
        Request request = new Request(editedProduct, businessAccount);
        DataManager.addRequest(request);
        // Todo delete old product
    }

}
