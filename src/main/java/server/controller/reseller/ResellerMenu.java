package server.controller.reseller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import common.model.account.BusinessAccount;
import common.model.commodity.Category;
import server.controller.comparator.Sort;
import server.controller.share.Menu;
import server.dataManager.YaDataManager;

import java.io.IOException;
import java.util.ArrayList;

public class ResellerMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public ResellerMenu() {
        fxmlFileAddress = "../../../fxml/Reseller.fxml";
        stageTitle = "Reseller panel";
    }

    public ArrayList<Category> sortCategories(String field) throws Exception {
        ArrayList<Category> categories = YaDataManager.getCategories();
        Sort.sortCategoryArrayList(categories, field);
        return categories;
    }

    public void walletTransaction(double amount, BusinessAccount businessAccount) throws IOException {
        businessAccount.addToCredit(amount);
        YaDataManager.removeBusiness(businessAccount);
        YaDataManager.addBusiness(businessAccount);
    }
}
