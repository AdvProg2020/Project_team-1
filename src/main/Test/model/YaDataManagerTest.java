package model;

import controller.data.YaDataManager;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.commodity.Category;
import model.commodity.Commodity;
import model.field.Field;
import model.field.OptionalField;
import model.share.Request;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class YaDataManagerTest {

    @Test
    public void getManagers() {
    }

    @Test
    public void addManager() {
    }

    @Test
    public void removeManager() {
    }

    @Test
    public void getBusinesses() {
    }

    @Test
    public void addBusiness() {
    }

    @Test
    public void removeBusiness() {
    }

    @Test
    public void getPersons() {
    }

    @Test
    public void addPerson() {
    }

    @Test
    public void removePerson() {
    }

    @Test
    public void getDiscountCodes() {
    }

    @Test
    public void addDiscountCode() {
    }

    @Test
    public void removeDiscountCode() {
    }

    @Test
    public void getCommodities() {
    }

    @Test
    public void addCommodity() {
    }

    @Test
    public void removeCommodity() {
    }

    @Test
    public void getRequests() throws Exception {
        SimpleAccount simpleAccount = new BusinessAccount("username", "f", "l", "email@email.com",
                "09122222222", "12345", "business");
        Category category = new Category("n", 1, null);
        ArrayList<Field> fields = new ArrayList<>();
        fields.add(new OptionalField("t", "v"));
        Commodity commodity = new Commodity("b", "n", 1, simpleAccount, true,
                category, fields, "d", 1);
        Request request = new Request(commodity, simpleAccount);
        YaDataManager.addRequest(request);
        Assert.assertFalse(YaDataManager.getRequests().size() != 1);
    }

    @Test
    public void addRequest() {
    }

    @Test
    public void removeRequest() {
    }

    @Test
    public void getOffs() {
    }

    @Test
    public void addOff() {
    }

    @Test
    public void removeOff() {
    }

    @Test
    public void getCategories() {
    }

    @Test
    public void addCategory() {
    }

    @Test
    public void removeCategory() {
    }
}