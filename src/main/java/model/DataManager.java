package model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataManager {



    private static File allManagersJson;

    private static File allResellersJson;

    private static File allPersonalAccountsJson;

    private static File allDiscountCodeJson;

    private static File allCommoditiesJson;

    private static File allRequestsJson;

    static {
        initializeDataDirectory();
        allManagersJson = new File("data/accounts/allManagers.json");
        allResellersJson = new File("data/accounts/allResellers.json");
        allPersonalAccountsJson = new File("data/accounts/allPersonalAccounts.json");
        allDiscountCodeJson = new File("data/allDiscountCodes.json");
        allRequestsJson = new File("data/allRequests.json");
        allCommoditiesJson = new File("data/allCommodities.json");
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
        } catch (IOException e) {

        }
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

    public static Request[] getAllRequests() throws IOException{
        FileReader fileReader = new FileReader(allRequestsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, Request.class);
    }

    public static ManagerAccount[] getAllManagers() throws IOException{
        FileReader fileReader = new FileReader(allManagersJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, ManagerAccount.class);
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
        return gson.fromJson(jsonReader, BusinessAccount.class);
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
        return gson.fromJson(jsonReader, PersonalAccount.class);
    }

    public static DiscountCode[] getAllDiscountCodes() throws Exception{
        FileReader fileReader = new FileReader(allDiscountCodeJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        return gson.fromJson(jsonReader, DiscountCode.class);
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
        return gson.fromJson(jsonReader, Commodity.class);
    }

    public static Commodity getCommodityById(int id) throws Exception {
        for (Commodity commodity : getAllCommodities()) {
            if (commodity.getCommodityId() == id) {
                return commodity;
            }
        }
        throw new Exception("there is no product with this ID");
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
    //    public static Object getNearHand() {
//        return nearHand;
//    }
//
//    private static Object nearHand = new Object();
//
//    private static SimpleAccount onlineAccount;
//
//    public static void setNearHand(Object nearHand) {
//        DataManager.nearHand = nearHand;
//    }
//
//    private static ArrayList<TransactionLog> allLogs = new ArrayList<TransactionLog>();
//
//    private static ArrayList<Category> allCategory = new ArrayList<Category>();
//
//    private static ArrayList<Commodity> allCommodities = new ArrayList<Commodity>();
//
//    private static ArrayList<Score> allScores = new ArrayList<Score>();
//
//    private static ArrayList<Request> allRequests = new ArrayList<Request>();
//
//    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
//
//    public static ArrayList<DiscountCode> getAllDiscountCodes() {
//        return allDiscountCodes;
//    }
//
//    public static void addToDiscounts(DiscountCode discountCode) {
//        allDiscountCodes.add(discountCode);
//    }
//
//
//    public static ArrayList<TransactionLog> getAllLogs() {
//        return allLogs;
//    }
//
//    public static ArrayList<Category> getAllCategory() {
//        return allCategory;
//    }
//
//    public static ArrayList<Commodity> getAllCommodities() {
//        return allCommodities;
//    }
//
//    public static ArrayList<Score> getAllScores() {
//        return allScores;
//    }
//
//    public static ArrayList<Request> getAllRequests() {
//        return allRequests;
//    }
//
//    public static void addRequest(Request request) {
//        allRequests.add(request);
//    }
//
//    public static void addLog(TransactionLog transactionLog) {
//        allLogs.add(transactionLog);
//    }
//
//    public static void addCategory(Category category) {
//        allCategory.add(category);
//    }
//
//    public static void addCommodities(Commodity commodity) {
//        allCommodities.add(commodity);
//    }
//
//    public static void addScore(Score score) {
//        allScores.add(score);
//    }
//
//    public static Commodity getCommodityByName(String name) {
//        for (Commodity commodity : allCommodities) {
//            if (commodity.getName().equals(name))
//                return commodity;
//        }
//        return null;
//    }
//
//    public static Commodity getCommodityById(int commodityId) throws Exception {
//        for (Commodity commodity : allCommodities) {
//            if (commodity.getCommodityId() == commodityId)
//                return commodity;
//        }
//        throw new Exception("no product with this ID");
//    }
//
//    public static DiscountCode getDiscountWithCode(String code) throws Exception {
//        for (DiscountCode discountCode : allDiscountCodes) {
//            if (discountCode.getCode().equals(code)) {
//                return discountCode;
//            }
//        }
//        throw new Exception("no discount with this code");
//    }
//
//    public static TransactionLog getLogByLogId(int logId) {
//        for (TransactionLog log : allLogs) {
//            if (log.getLogId().equals(logId))
//                return log;
//        }
//        return null;
//    }
//
//    public static Boolean checkUsername(SimpleAccount account, String user) {
//        return account.getUsername().equals(user);
//    }
//
//    public static Boolean checkPassword(SimpleAccount account, String password) {
//        return account.isPasswordCorrect(password);
//    }
//
//    public static SimpleAccount getOnlineAccount() {
//        return onlineAccount;
//    }
//
//    public static void setOnlineAccount(SimpleAccount onlineAccount) {
//        DataManager.onlineAccount = onlineAccount;
//    }
//
//    public static Category getCategoryByName(String name) {
//        for (Category category : allCategory) {
//            if (category.getName().equals(name))
//                return category;
//        }
//        return null;
//    }
//
//    public static boolean isThereAnyRequestWithThisId(int id) {
//        for (Request request : allRequests) {
//            if (request.getId() == id)
//                return true;
//        }
//        return false;
//    }
//
//    public static Request getRequestWithId(int id) {
//        for (Request request : allRequests) {
//            if (request.getId() == id)
//                return request;
//        }
//        return null;
//    }
//
//    public static DiscountCode getDiscountCodeWithCode(String code) {
//        for (DiscountCode discountCode : allDiscountCodes) {
//            if (discountCode.getCode().equals(code))
//                return discountCode;
//        }
//        return null;
//    }
//
//    public static boolean isThereAnyDiscountCodeWithThisCode(String code) {
//        for (DiscountCode discountCode : allDiscountCodes) {
//            if (discountCode.getCode().equals(code))
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean isThereAnyCategoryWithThisName(String name) {
//        for (Category category : allCategory) {
//            if (category.getName().equals(name))
//                return true;
//        }
//        return false;
//    }
}

