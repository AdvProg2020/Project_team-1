package model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataManager {



    private static File allManagersJson;

    private static File allResellersJson;

    private static File allPersonalAccountsJson;

    private static File allDiscountCodeJson;

    private static File allCommoditiesJson;

    private static File allRequestsJson;

    private static File allOffsJson;

    private static File allCategoryJson;

    static {
        initializeDataDirectory();
        allManagersJson = new File("data/accounts/allManagers.json");
        allResellersJson = new File("data/accounts/allResellers.json");
        allPersonalAccountsJson = new File("data/accounts/allPersonalAccounts.json");
        allDiscountCodeJson = new File("data/allDiscountCodes.json");
        allRequestsJson = new File("data/allRequests.json");
        allCommoditiesJson = new File("data/allCommodities.json");
        allOffsJson = new File("data/allOffs.json");
        allCategoryJson = new File("data/allCategory.json");
        initializeDataFiles();
    }

    private static void initializeDataDirectory() {
        new File("data").mkdir();
        new File("data/accounts").mkdir();
    }

    private static void initializeDataFiles() {
        try {
            Gson gson = new Gson();
            gson.toJson(new ArrayList<ManagerAccount>(), new FileWriter(allManagersJson));
            gson.toJson(new ArrayList<BusinessAccount>(), new FileWriter(allResellersJson));
            gson.toJson(new ArrayList<PersonalAccount>(), new FileWriter(allPersonalAccountsJson));
            gson.toJson(new ArrayList<DiscountCode>() , new FileWriter(allDiscountCodeJson));
            gson.toJson(new ArrayList<Request>(), new FileWriter(allRequestsJson));
            gson.toJson(new ArrayList<Off>(), new FileWriter(allOffsJson));
            gson.toJson(new ArrayList<Category>() , new FileWriter(allCategoryJson));
        } catch (IOException e) {

        }
    }

    public static void addCategory(Category category) throws IOException {
        ArrayList<Category> allCategories = new ArrayList<>(Arrays.asList(getAllCategories()));
        allCategories.add(category);
        Gson gson = new Gson();
        gson.toJson(allCategories, new FileWriter(allCategoryJson));
    }

    public static void removeCategory(Category category) throws IOException {
        ArrayList<Category> allCategories = new ArrayList<>(Arrays.asList(getAllCategories()));
        allCategories.remove(category);
        Gson gson = new Gson();
        gson.toJson(allCategories, new FileWriter(allCategoryJson));
    }

    public static boolean isCategoryExist(String name) throws FileNotFoundException {
        for (Category category : getAllCategories()) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public static Category[] getAllCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(allCategoryJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, Category[].class);
    }

    public static void deleteRequest(Request request) throws IOException {
        ArrayList<Request> allRequests = new ArrayList<>(Arrays.asList(getAllRequests()));
        allRequests.remove(request);
        Gson gson = new Gson();
        gson.toJson(allRequests, new FileWriter(allRequestsJson));
    }

    public static Request getRequest(int id) throws Exception {
        for (Request request : getAllRequests()) {
            if (request.getId() == id ){
                return request;
            }
        }
        Exception e  = new Exception("Request doesn't exist");
        throw e;
    }

    public static boolean isCommodityExist(int id) throws Exception {
        for (Commodity commodity : getAllCommodities()) {
            if (commodity.getCommodityId() == id)
                return true;
        }
        return false;
    }

    public static Request[] getAllRequests() throws IOException{
        FileReader fileReader = new FileReader(allRequestsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, Request[].class);
    }

    public static ManagerAccount[] getAllManagers() throws IOException{
        FileReader fileReader = new FileReader(allManagersJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, ManagerAccount[].class);
    }



    public static void addManagerAccount(ManagerAccount managerAccount) throws Exception{
        ArrayList<ManagerAccount> allManagerAccounts = new ArrayList<>(Arrays.asList(getAllManagers()));
        allManagerAccounts.add(managerAccount);
        Gson gson = new Gson();
        gson.toJson(allManagerAccounts, new FileWriter(allManagersJson));
    }

    public static SimpleAccount getAccountWithUserName(String username) throws IOException {
        for (ManagerAccount managerAccount : getAllManagers()) {
            if (managerAccount.getUsername().equals(username))
                return managerAccount;
        }
        for (PersonalAccount personalAccount : getAllPersonalAccounts()) {
            if (personalAccount.getUsername().equals(username))
                return personalAccount;
        }
        for (BusinessAccount reseller : getAllResellers()) {
            if (reseller.getUsername().equals(username))
                return reseller;
        }
        return null;
    }

    public static BusinessAccount[] getAllResellers() throws IOException{
        FileReader fileReader = new FileReader(allResellersJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, BusinessAccount[].class);
    }

    public static boolean deleteManagerAccount(String username) throws IOException {
        ManagerAccount managerAccount = null;
        for (ManagerAccount manager : getAllManagers()) {
            if (manager.getUsername().equalsIgnoreCase(username)) {
                managerAccount = manager;
                ArrayList<ManagerAccount> allManagerAccounts = new ArrayList<>(Arrays.asList(getAllManagers()));
                allManagerAccounts.remove(managerAccount);
                Gson gson = new Gson();
                gson.toJson(allManagerAccounts, new FileWriter(allManagersJson));
                return true;
            }
        }
        return false;
    }

    public static boolean deletePersonalAccount(String username) throws IOException {
        PersonalAccount personalAccount = null;
        for (PersonalAccount personalAcc : getAllPersonalAccounts()) {
            if (personalAccount.getUsername().equalsIgnoreCase(username)) {
                personalAccount = personalAcc;
                ArrayList<PersonalAccount> allPersonalAccounts = new ArrayList<>(Arrays.asList(getAllPersonalAccounts()));
                allPersonalAccounts.remove(personalAccount);
                Gson gson = new Gson();
                gson.toJson(allPersonalAccounts, new FileWriter(allPersonalAccountsJson));
                return true;
            }
        }
        return false;
    }

    public static void addDiscountCode(DiscountCode discountCode) throws Exception {
        ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>(Arrays.asList(getAllDiscountCodes()));
        allDiscountCodes.add(discountCode);
        Gson gson = new Gson();
        gson.toJson(allDiscountCodes, new FileWriter(allDiscountCodeJson));
    }

    public static void deleteDiscountCode(DiscountCode discountCode) throws Exception {
        ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>(Arrays.asList(getAllDiscountCodes()));
        allDiscountCodes.remove(discountCode);
        Gson gson = new Gson();
        gson.toJson(allDiscountCodes, new FileWriter(allDiscountCodeJson));
    }

    public static boolean deleteBusinessAccount(String username) throws IOException {
        BusinessAccount businessAccount = null;
        for (BusinessAccount reseller : getAllResellers()) {
            if (reseller.getUsername().equalsIgnoreCase(username)) {
                businessAccount = reseller;
                ArrayList<BusinessAccount> allResellersAccounts = new ArrayList<>(Arrays.asList(getAllResellers()));
                allResellersAccounts.remove(businessAccount);
                Gson gson = new Gson();
                gson.toJson(allResellersAccounts, new FileWriter(allResellersJson));
                return true;
            }
        }
        return false;
    }


    public static void deleteAccountWithUserName(String username) throws Exception {
        if (deleteBusinessAccount(username)){
            return;
        }
        if (deletePersonalAccount(username)) {
            return;
        }
        if (deleteManagerAccount(username)) {
            return;
        }
        throw new Exception();
    }

    public static DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        for (DiscountCode discountCode : getAllDiscountCodes()) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        throw new Exception();
    }


    public static void addResellerAccount(BusinessAccount businessAccount) throws Exception {
        ArrayList<BusinessAccount> allResellersAccounts = new ArrayList<>(Arrays.asList(getAllResellers()));
        allResellersAccounts.add(businessAccount);
        Gson gson = new Gson();
        gson.toJson(allResellersAccounts, new FileWriter(allResellersJson));
    }

    public static PersonalAccount[] getAllPersonalAccounts() throws IOException {
        FileReader fileReader = new FileReader(allPersonalAccountsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, PersonalAccount[].class);
    }

    public static DiscountCode[] getAllDiscountCodes() throws Exception{
        FileReader fileReader = new FileReader(allDiscountCodeJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, DiscountCode[].class);
    }


    public static void addPersonalAccount(PersonalAccount personalAccount) throws Exception {
        ArrayList<PersonalAccount> allPersonalAccounts = new ArrayList<>(Arrays.asList(getAllPersonalAccounts()));
        allPersonalAccounts.add(personalAccount);
        Gson gson = new Gson();
        gson.toJson(allPersonalAccounts, new FileWriter(allPersonalAccountsJson));
    }

    public static Commodity[] getAllCommodities() throws Exception {
        FileReader fileReader = new FileReader(allCommoditiesJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, Commodity[].class);
    }

    public static Commodity getCommodityById(int id) throws Exception {
        for (Commodity commodity : getAllCommodities()) {
            if (commodity.getCommodityId() == id) {
                return commodity;
            }
        }
        throw new Exception("there is no product with this ID");
    }

    public static void addCommodity(Commodity commodity) throws Exception {
        ArrayList<Commodity> allCommodities = new ArrayList<>(Arrays.asList(getAllCommodities()));
        allCommodities.add(commodity);
        Gson gson = new Gson();
        gson.toJson(allCommodities, new FileWriter(allCommoditiesJson));
    }

    public static void deleteCommodity(Commodity commodity) throws Exception {
        ArrayList<Commodity> allCommodities = new ArrayList<>(Arrays.asList(getAllCommodities()));
        allCommodities.remove(commodity);
        Gson gson = new Gson();
        gson.toJson(allCommodities, new FileWriter(allCommoditiesJson));
    }

    public static boolean isUsernameExist(String username) throws IOException {
        for (ManagerAccount manager : getAllManagers()) {
            if (manager.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        for (BusinessAccount reseller : getAllResellers()) {
            if (reseller.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        for (PersonalAccount personalAccount : getAllPersonalAccounts()) {
            if (personalAccount.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
    private static SimpleAccount onlineAccount;

    public static void setOnlineAccount(SimpleAccount onlineAccount) {
        DataManager.onlineAccount = onlineAccount;
    }

    public static SimpleAccount getOnlineAccount() {
        return onlineAccount;
    }

    public static Off[] getAllOffs() throws IOException {
        FileReader fileReader = new FileReader(allOffsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, Off[].class);
    }


    public static void addOff(Off off) throws Exception {
        ArrayList<Off> allOffs = new ArrayList<>(Arrays.asList(getAllOffs()));
        allOffs.add(off);
        Gson gson = new Gson();
        gson.toJson(allOffs, new FileWriter(allOffsJson));
    }

    

}

