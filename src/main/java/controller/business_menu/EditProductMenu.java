package controller.business_menu;

import controller.CommandProcess;
import model.Category;
import model.Comment;
import model.Commodity;
import model.Status;
import model.account.SimpleAccount;
import model.field.Field;

import java.util.ArrayList;

public class EditProductMenu implements CommandProcess {
    Commodity commodity;

    public EditProductMenu(Commodity commodity) {
        this.commodity = new Commodity(commodity);
        commodity.setStatus(Status.UNDER_CHECKING_FOR_EDIT);
    }

    public void editBrand(String newBrand) {
        commodity.setBrand(newBrand);
    }

    public void editName(String newName) {
        commodity.setName(newName);
    }

    public void editPrice(int newPrice) {
        commodity.setPrice(newPrice);
    }

    public void editInventory(int newInventory) {
        commodity.setInventory(newInventory);
    }

    public void editIsCommodityAvailable(boolean newIsCommodityAvailable) {
        commodity.setCommodityAvailable(newIsCommodityAvailable);
    }

    public void editCategory(Category newCategory) {
        commodity.setCategory(newCategory);
    }

    public void editCategorySpecifications(ArrayList<Field> newCategorySpecs){
        commodity.setCategorySpecifications(newCategorySpecs);
    }

    public void editDescription(String newDescription) {
        commodity.setDescription(newDescription);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        return null;
    }
}
