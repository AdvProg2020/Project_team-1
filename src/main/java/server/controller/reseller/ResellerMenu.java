package server.controller.reseller;

import client.Session;
import client.view.commandline.View;
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
import server.data.YaDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ResellerMenu extends Menu {
    public ResellerMenu() {
        fxmlFileAddress = "../../../fxml/Reseller.fxml";
        stageTitle = "Reseller panel";
    }

    public BusinessAccount getBusinessAccount() {
        return (BusinessAccount) Session.getOnlineAccount();
    }

    public ArrayList<Commodity> manageCommodities() throws Exception {
        ArrayList<Commodity> commodityArrayList = new ArrayList<>();
        for (Integer commodityId : getBusinessAccount().getCommoditiesId()) {
            commodityArrayList.add(YaDataManager.getCommodityById(commodityId));
        }
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
        for (Category category : YaDataManager.getCategories()) {
            if (category.getName().equalsIgnoreCase(categoryString)) {
                return category;
            }
        }
        throw new Exception("Category not found");
    }

    public ArrayList<String> getCategoriesName() {
        ArrayList<String> categoriesName = null;
        try {
            categoriesName = YaDataManager.getCategories().stream()
                    .map(Category::getName).collect(Collectors.toCollection(ArrayList::new));
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
                           ArrayList<Field> categorySpecifications, String description, int amount , String path) throws Exception {
        BusinessAccount businessAccount = getBusinessAccount();
        Commodity newCommodity = new Commodity(brand, name, price, businessAccount.getUsername(), true,
                category.getName(), categorySpecifications, description, amount, path);
        Request request = new Request(newCommodity, businessAccount.getUsername());
        YaDataManager.addRequest(request);
    }

    public void removeProduct(int productId) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(productId);
        BusinessAccount businessAccount = getBusinessAccount();
        YaDataManager.removeBusiness(businessAccount);
        businessAccount.removeCommodity(commodity);
        YaDataManager.addBusiness(businessAccount);
        YaDataManager.removeCommodity(commodity);
    }
}
