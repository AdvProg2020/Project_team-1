package old.business_menu;

import old.CommandProcess;
import model.*;
import model.account.SimpleAccount;
import model.field.Field;

import java.util.ArrayList;

public class AddProductMenu implements CommandProcess{
    CommandProcess commandProcess;
    public void addProduct(int commodityId, Status status, String brand, String name, int price,
                           SimpleAccount seller, Boolean isCommodityAvailable, Category category,
                           ArrayList<Field> categorySpecifications, String description,
                           int averageScore, int amount) throws Exception {
        Commodity commodity = new Commodity(commodityId, status, brand, name, price, seller, isCommodityAvailable,
                category, categorySpecifications, description, averageScore, amount);
        DataManager.addRequest(new Request(commodity, DataManager.getOnlineAccount()));
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        return null;
    }
}
