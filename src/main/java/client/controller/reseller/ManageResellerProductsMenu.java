package client.controller.reseller;

import client.Main;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.BusinessAccount;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.field.Field;
import common.model.share.Request;
import client.controller.comparator.Sort;
import client.controller.share.Menu;

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

    public void editProduct(Commodity oldProduct, String brand, String name, int price, boolean availability, Category category,
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
                new TypeToken<Request>() {
                }.getType()));
        // Todo delete old product
    }
}
