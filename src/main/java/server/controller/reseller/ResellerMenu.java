package server.controller.reseller;

import client.Session;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.BusinessAccount;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.field.Field;
import common.model.log.SellLog;
import common.model.share.Request;
import server.controller.comparator.Sort;
import server.controller.share.Menu;
import server.controller.share.MenuHandler;
import server.dataManager.YaDataManager;

import java.io.*;
import java.util.ArrayList;

import static client.Main.*;

public class ResellerMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public ResellerMenu() {
        fxmlFileAddress = "../../../fxml/Reseller.fxml";
        stageTitle = "Reseller panel";
    }

    public BusinessAccount getBusinessAccount() {
        return (BusinessAccount) Session.getOnlineAccount();
    }

    public ArrayList<Commodity> manageCommodities() throws Exception {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF("send seller commodities");
        dos.flush();
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        ArrayList<Commodity> commodityArrayList = yaGson.fromJson(dis.readUTF(), new TypeToken<ArrayList<Commodity>>() {
        }
                .getType());
        if (MenuHandler.getInstance().getCurrentMenu() != View.manageResellerProductsMenu) {
            View.manageResellerProductsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.manageResellerProductsMenu);
        }
        return commodityArrayList;
    }

    public ArrayList<SellLog> sortSalesHistory(String field) throws Exception {
        ArrayList<SellLog> sellLogs = getBusinessAccount().getSellLogs();
        Sort.sortSellLogArrayList(sellLogs, field);
        return sellLogs;
    }

    public ArrayList<Off> manageOffs() throws IOException {
        ArrayList<Off> offArrayList = new ArrayList<>();
        for (Integer offId : getBusinessAccount().getOffsId()) {
            offArrayList.add(YaDataManager.getOffWithId(offId));
        }
        if (MenuHandler.getInstance().getCurrentMenu() != View.manageResellerOffMenu) {
            View.manageResellerOffMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.manageResellerOffMenu);
        }
        return offArrayList;
    }

    public Category getCategoryByName(String categoryString) throws Exception {
        outputStream.writeUTF("name of category is " + categoryString);
        outputStream.flush();
        return yaGson.fromJson(inputStream.readUTF(), new TypeToken<Category>() {
        }.getType());
    }

    public ArrayList<String> getCategoriesName() {
        ArrayList<String> categoriesName = null;
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dos.writeUTF("categories name");
            dos.flush();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            categoriesName = yaGson.fromJson(dis.readUTF(), new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoriesName;
    }

    public ArrayList<Category> sortCategories(String field) throws Exception {
        ArrayList<Category> categories = YaDataManager.getCategories();
        Sort.sortCategoryArrayList(categories, field);
        return categories;
    }

    public void addProduct(String brand, String name, int price, Category category,
                           ArrayList<Field> categorySpecifications, String description, int amount,
                           String productFilePath) throws Exception {
        BusinessAccount businessAccount = getBusinessAccount();
        Commodity newCommodity = new Commodity(brand, name, price, businessAccount.getUsername(), true,
                category.getName(), categorySpecifications, description, amount);
        newCommodity.setProductFilePathOnSellerClient(productFilePath);
        Request request = new Request(newCommodity, businessAccount.getUsername());
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos.writeUTF("New Commodity");
        dos.flush();
        dis.readUTF();
        dos.writeUTF(yaGson.toJson(request, new TypeToken<Request>() {
        }.getType()));
        dos.flush();
    }

    public void removeProduct(int productId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(productId);
        BusinessAccount businessAccount = getBusinessAccount();
        YaDataManager.removeBusiness(businessAccount);
        businessAccount.removeCommodity(commodity);
        YaDataManager.addBusiness(businessAccount);
        YaDataManager.removeCommodity(commodity);
    }

    public void walletTransaction(double amount, BusinessAccount businessAccount) throws IOException {
        businessAccount.addToCredit(amount);
        YaDataManager.removeBusiness(businessAccount);
        YaDataManager.addBusiness(businessAccount);
    }
}
