package server.controller.reseller;

import client.Main;
import client.Session;
import client.controller.reseller.ClientResellerMenu;
import client.view.commandline.View;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.BusinessAccount;
import common.model.account.SimpleAccount;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.field.Field;
import common.model.log.SellLog;
import common.model.share.Request;
import server.controller.comparator.Sort;
import server.controller.share.Menu;
import server.dataManager.YaDataManager;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class ManageResellerProductsMenu extends Menu {

    public ManageResellerProductsMenu() {
        fxmlFileAddress = "../../../fxml/reseller/ManageProducts.fxml";
        stageTitle = "Reseller products";
    }

    public ArrayList<Commodity> sort(String field) throws Exception {
        ArrayList<Commodity> commodities = ClientResellerMenu.manageCommodities();
        Sort.sortProductArrayList(commodities, field);
        return commodities;
    }

    public Commodity getCommodityById(int productId) throws Exception {
        Commodity commodity = ClientResellerMenu.getBusinessAccount().getCommodityById(productId);
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
            if (sellLog.getCommoditiesId().contains(commodity)) {
                simpleAccountArrayList.add(YaDataManager.getPersonWithUserName(sellLog.getBuyerUsername()));
            }
        }
        return simpleAccountArrayList;
    }

    public void editProduct(Commodity oldProduct, String brand, String name, int price,boolean availability, Category category,
                            ArrayList<Field> categorySpecifications, String description, int amount) throws Exception {
        BusinessAccount businessAccount = ClientResellerMenu.getBusinessAccount();
        Commodity editedProduct = new Commodity((brand.equals("-")) ? (oldProduct.getBrand()) : (brand),
                (name.equals("-")) ? (oldProduct.getName()) : (name),
                (price == -1) ? (oldProduct.getPrice()) : (price),
                businessAccount.getUsername(),
                availability,
                category.getName(),
                categorySpecifications,
                (description.equals("-")) ? (oldProduct.getDescription()) : (description),
                (amount == -1) ? (oldProduct.getInventory()) : (amount), oldProduct.getImagePath());
        editedProduct.setCommodityId(oldProduct.getCommodityId());
        Request request = new Request(editedProduct, businessAccount.getUsername());
        Main.outputStream.writeUTF("add request " + new YaGsonBuilder().setPrettyPrinting().create().toJson(request,
                new TypeToken<Request>(){}.getType()));
        // Todo delete old product
    }

}
