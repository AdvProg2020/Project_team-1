package model;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class DataManager {

    private static final File allManagersJson;

    private static final File allResellersJson;

    private static final File allPersonalAccountsJson;

    private static final File allDiscountCodeJson;

    private static final File allCommoditiesJson;

    private static final File allRequestsJson;

    private static final File allOffsJson;

    private static final File allCategoryJson;

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
            if (!allManagersJson.exists()) {
                FileWriter allManagersFileWriter = new FileWriter(allManagersJson);
                gson.toJson(new ArrayList<ManagerAccount>(), allManagersFileWriter);
                allManagersFileWriter.close();
            }
            if (!allResellersJson.exists()) {
                FileWriter allResellerFileWriter = new FileWriter(allResellersJson);
                gson.toJson(new ArrayList<BusinessAccount>(), allResellerFileWriter);
                allResellerFileWriter.close();
            }
            if (!allPersonalAccountsJson.exists()) {
                FileWriter allPersonalAccountFileWriter = new FileWriter(allPersonalAccountsJson);
                gson.toJson(new ArrayList<PersonalAccount>(), allPersonalAccountFileWriter);
                allPersonalAccountFileWriter.close();
            }
            if (!allDiscountCodeJson.exists()) {
                FileWriter allDiscountCodeFileWriter = new FileWriter(allDiscountCodeJson);
                gson.toJson(new ArrayList<DiscountCode>(), allDiscountCodeFileWriter);
                allDiscountCodeFileWriter.close();
            }
            if (!allResellersJson.exists()) {
                FileWriter allRequestsFileWriter = new FileWriter(allRequestsJson);
                gson.toJson(new ArrayList<Request>(), allRequestsFileWriter);
                allRequestsFileWriter.close();
            }
            if (!allOffsJson.exists()) {
                FileWriter allOffsFileWriter = new FileWriter(allOffsJson);
                gson.toJson(new ArrayList<Off>(), allOffsFileWriter);
                allOffsFileWriter.close();
            }
            if (!allCategoryJson.exists()) {
                FileWriter allCategoriesFileWriter = new FileWriter(allCategoryJson);
                gson.toJson(new ArrayList<Category>(), allCategoriesFileWriter);
                allCategoriesFileWriter.close();
            }
            if (!allCommoditiesJson.exists()) {
                FileWriter allCommoditiesFileWriter = new FileWriter(allCommoditiesJson);
                gson.toJson(new ArrayList<Commodity>(), allCommoditiesFileWriter);
                allCommoditiesFileWriter.close();
            }
            if (!allRequestsJson.exists()) {
                FileWriter allRequestFileWriter = new FileWriter(allRequestsJson);
                gson.toJson(new ArrayList<Request>(), allRequestFileWriter);
                allRequestFileWriter.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCategory(Category category) throws IOException {
        ArrayList<Category> allCategories = new ArrayList<>(Arrays.asList(getAllCategories()));
        allCategories.add(category);
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(allCategoryJson);
        gson.toJson(allCategories, writer);
        writer.close();
    }

    public static void removeCategory(Category category) throws IOException {
        ArrayList<Category> allCategories = new ArrayList<>(Arrays.asList(getAllCategories()));
        for (Category allCategory : allCategories) {
            if (allCategory.getName().equals(category.getName())) {
                allCategories.remove(allCategory);
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(allCategoryJson);
                gson.toJson(allCategories, writer);
                writer.close();
                return;
            }
        }

    }

    public static boolean isCategoryExist(String name) throws IOException {
        for (Category category : getAllCategories()) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public static Category[] getAllCategories() throws IOException {
        FileReader fileReader = new FileReader(allCategoryJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        Category[] categories = gson.fromJson(jsonReader, Category[].class);
        jsonReader.close();
        fileReader.close();
        return categories;
    }

    public static void deleteRequest(Request request) throws IOException {
        ArrayList<Request> allRequests = new ArrayList<>(Arrays.asList(getAllRequests()));
        for (Request allRequest : allRequests) {
            if (allRequest.getId() == request.getId()) {
                allRequests.remove(allRequest);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Requestable.class, new InterfaceAdapter<Requestable>());
                Gson gson = gsonBuilder.create();
                FileWriter writer = new FileWriter(allRequestsJson);
                gson.toJson(allRequests, writer);
                writer.close();
                return;
            }
        }

    }

    public static void deleteOff(Off off) throws Exception {
        ArrayList<Off> allOffs = new ArrayList<>(Arrays.asList(getAllOffs()));
        for (Off allOff : allOffs) {
            if (allOff.getOffID() == off.getOffID()) {
                allOffs.remove(allOff);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Requestable.class, new InterfaceAdapter<Requestable>());
                Gson gson = gsonBuilder.create();
                FileWriter writer = new FileWriter(allOffsJson);
                gson.toJson(allOffs, writer);
                writer.close();
                return;
            }
        }
    }

    public static Request getRequest(int id) throws Exception {
        for (Request request : getAllRequests()) {
            if (request.getId() == id) {
                return request;
            }
        }
        throw new Exception("Request doesn't exist");
    }

    public static void addRequest(Request request) throws Exception {
        ArrayList<Request> allRequests = new ArrayList<>(Arrays.asList(getAllRequests()));
        allRequests.add(request);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Requestable.class, new InterfaceAdapter<Requestable>());
        Gson gson = gsonBuilder.create();
        FileWriter writer = new FileWriter(allRequestsJson);
        gson.toJson(allRequests, writer);
        writer.close();
    }

    public static boolean isCommodityExist(int id) throws Exception {
        for (Commodity commodity : getAllCommodities()) {
            if (commodity.getCommodityId() == id)
                return true;
        }
        return false;
    }

    public static Request[] getAllRequests() throws IOException {
        FileReader fileReader = new FileReader(allRequestsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Requestable.class, new InterfaceAdapter<Requestable>());
        Gson gson = gsonBuilder.create();
        Request[] requests = gson.fromJson(jsonReader, Request[].class);
        jsonReader.close();
        fileReader.close();
        return requests;
    }

    public static ManagerAccount[] getAllManagers() throws IOException {
        FileReader fileReader = new FileReader(allManagersJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        ManagerAccount[] managerAccounts = gson.fromJson(jsonReader, ManagerAccount[].class);
        jsonReader.close();
        fileReader.close();
        return managerAccounts;
    }

    public static void addManagerAccount(ManagerAccount managerAccount) throws Exception {
        ArrayList<ManagerAccount> allManagerAccounts = new ArrayList<>(Arrays.asList(getAllManagers()));
        allManagerAccounts.add(managerAccount);
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(allManagersJson);
        gson.toJson(allManagerAccounts, writer);
        writer.close();
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

    public static BusinessAccount[] getAllResellers() throws IOException {
        FileReader fileReader = new FileReader(allResellersJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        BusinessAccount[] businessAccounts = gson.fromJson(jsonReader, BusinessAccount[].class);
        jsonReader.close();
        fileReader.close();
        return businessAccounts;
    }

    public static boolean deleteManagerAccount(String username) throws IOException {
        ManagerAccount managerAccount = null;
        for (ManagerAccount manager : getAllManagers()) {
            if (manager.getUsername().equalsIgnoreCase(username)) {
                managerAccount = manager;
                ArrayList<ManagerAccount> allManagerAccounts = new ArrayList<>(Arrays.asList(getAllManagers()));
                allManagerAccounts.remove(managerAccount);
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(allManagersJson);
                gson.toJson(allManagerAccounts, writer);
                writer.close();
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
                FileWriter writer = new FileWriter(allPersonalAccountsJson);
                gson.toJson(allPersonalAccounts, writer);
                writer.close();
                return true;
            }
        }
        return false;
    }

    public static void addDiscountCode(DiscountCode discountCode) throws Exception {
        ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>(Arrays.asList(getAllDiscountCodes()));
        allDiscountCodes.add(discountCode);
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(allDiscountCodeJson);
        gson.toJson(allDiscountCodes, writer);
        writer.close();
    }

    public static void deleteDiscountCode(DiscountCode discountCode) throws Exception {
        ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>(Arrays.asList(getAllDiscountCodes()));
        for (DiscountCode code : allDiscountCodes) {
            if (code.getCode() == discountCode.getCode()) {
                allDiscountCodes.remove(code);
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(allDiscountCodeJson);
                gson.toJson(allDiscountCodes, writer);
                writer.close();
                return;
            }
        }

    }

    public static boolean deleteBusinessAccount(String username) throws IOException {
        BusinessAccount businessAccount = null;
        for (BusinessAccount reseller : getAllResellers()) {
            if (reseller.getUsername().equalsIgnoreCase(username)) {
                businessAccount = reseller;
                ArrayList<BusinessAccount> allResellersAccounts = new ArrayList<>(Arrays.asList(getAllResellers()));
                allResellersAccounts.remove(businessAccount);
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(allResellersJson);
                gson.toJson(allResellersAccounts, writer);
                writer.close();
                return true;
            }
        }
        return false;
    }


    public static void deleteAccountWithUserName(String username) throws Exception {
        if (deleteBusinessAccount(username)) {
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
        FileWriter writer = new FileWriter(allResellersJson);
        gson.toJson(allResellersAccounts, writer);
        writer.close();
    }

    public static PersonalAccount[] getAllPersonalAccounts() throws IOException {
        FileReader fileReader = new FileReader(allPersonalAccountsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        PersonalAccount[] personalAccounts = gson.fromJson(jsonReader, PersonalAccount[].class);
        jsonReader.close();
        fileReader.close();
        return personalAccounts;
    }

    public static DiscountCode[] getAllDiscountCodes() throws Exception {
        FileReader fileReader = new FileReader(allDiscountCodeJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        DiscountCode[] discountCodes = gson.fromJson(jsonReader, DiscountCode[].class);
        jsonReader.close();
        fileReader.close();
        return discountCodes;
    }


    public static void addPersonalAccount(PersonalAccount personalAccount) throws Exception {
        ArrayList<PersonalAccount> allPersonalAccounts = new ArrayList<>(Arrays.asList(getAllPersonalAccounts()));
        allPersonalAccounts.add(personalAccount);
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(allPersonalAccountsJson);
        gson.toJson(allPersonalAccounts, writer);
        writer.close();
    }

    public static Commodity[] getAllCommodities() throws IOException {
        FileReader fileReader = new FileReader(allCommoditiesJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        Commodity[] commodities = gson.fromJson(jsonReader, Commodity[].class);
        jsonReader.close();
        fileReader.close();
        return commodities;
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
        FileWriter writer = new FileWriter(allCommoditiesJson);
        gson.toJson(allCommodities, writer);
        writer.close();
    }

    public static void deleteCommodity(Commodity commodity) throws Exception {
        ArrayList<Commodity> allCommodities = new ArrayList<>(Arrays.asList(getAllCommodities()));
        for (Commodity allCommodity : allCommodities) {
            if (allCommodity.getCommodityId() == commodity.getCommodityId()) {
                allCommodities.remove(allCommodity);
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(allCommoditiesJson);
                gson.toJson(allCommodities, writer);
                writer.close();
                return;
            }
        }

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

    public static Off[] getAllOffs() throws IOException {
        FileReader fileReader = new FileReader(allOffsJson);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        Off[] offs = gson.fromJson(jsonReader, Off[].class);
        jsonReader.close();
        fileReader.close();
        return offs;
    }

    public static void addOff(Off off) throws Exception {
        ArrayList<Off> allOffs = new ArrayList<>(Arrays.asList(getAllOffs()));
        allOffs.add(off);
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(allOffsJson);
        gson.toJson(allOffs, writer);
        writer.close();
    }
}

class InterfaceAdapter<T> implements JsonSerializer, JsonDeserializer {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    public T deserialize(JsonElement jsonElement, Type type,
                         JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }
    public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }
    /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}