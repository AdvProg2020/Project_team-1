package server.data;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.*;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.commodity.DiscountCode;
import common.model.commodity.Off;
import common.model.share.Request;
import server.controller.Statistics;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class YaDataManager {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    private static final File statisticsJson;

    private static final File supportJson;

    private static final File managersJson;

    private static final File businessesJson;

    private static final File personsJson;

    private static final File discountCodesJson;

    private static final File commoditiesJson;

    private static final File requestsJson;

    private static final File offsJson;

    private static final File categoriesJson;

    static {
        initializeDataDirectories();
        statisticsJson = new File("data/statistics.json");
        managersJson = new File("data/accounts/managers.json");
        businessesJson = new File("data/accounts/businesses.json");
        personsJson = new File("data/accounts/persons.json");
        discountCodesJson = new File("data/discountCodes.json");
        requestsJson = new File("data/requests.json");
        commoditiesJson = new File("data/commodities.json");
        offsJson = new File("data/offs.json");
        categoriesJson = new File("data/categories.json");
        supportJson = new File("data/accounts/supportAccounts.json");
        initializeDataFiles();
    }

    private static void initializeDataDirectories() {
        new File("data").mkdir();
        new File("data/accounts").mkdir();
    }

    private static void initializeDataFiles() {
        try {
            if (statisticsJson.exists()) {
                FileReader fileReader = new FileReader(statisticsJson);
                Statistics.setUpdatedStats(yaGson.fromJson(fileReader, server.controller.Statistics.class));
                fileReader.close();
            } else {
                Statistics.setUpdatedStats(new Statistics());
                FileWriter statisticsFileWriter = new FileWriter(statisticsJson);
                yaGson.toJson(Statistics.updatedStats, statisticsFileWriter);
                statisticsFileWriter.close();
            }
            if (!managersJson.exists()) {
                FileWriter allManagersFileWriter = new FileWriter(managersJson);
                yaGson.toJson(new ArrayList<ManagerAccount>(), allManagersFileWriter);
                allManagersFileWriter.close();
            }
            if (!supportJson.exists()) {
                FileWriter allSupportsFileWriter = new FileWriter(supportJson);
                yaGson.toJson(new ArrayList<SupportAccount>(), allSupportsFileWriter);
                allSupportsFileWriter.close();
            }
            if (!businessesJson.exists()) {
                FileWriter allResellerFileWriter = new FileWriter(businessesJson);
                yaGson.toJson(new ArrayList<BusinessAccount>(), allResellerFileWriter);
                allResellerFileWriter.close();
            }
            if (!personsJson.exists()) {
                FileWriter allPersonalAccountFileWriter = new FileWriter(personsJson);
                yaGson.toJson(new ArrayList<PersonalAccount>(), allPersonalAccountFileWriter);
                allPersonalAccountFileWriter.close();
            }
            if (!discountCodesJson.exists()) {
                FileWriter allDiscountCodeFileWriter = new FileWriter(discountCodesJson);
                yaGson.toJson(new ArrayList<DiscountCode>(), allDiscountCodeFileWriter);
                allDiscountCodeFileWriter.close();
            }
            if (!offsJson.exists()) {
                FileWriter allOffsFileWriter = new FileWriter(offsJson);
                yaGson.toJson(new ArrayList<Off>(), allOffsFileWriter);
                allOffsFileWriter.close();
            }
            if (!categoriesJson.exists()) {
                FileWriter allCategoriesFileWriter = new FileWriter(categoriesJson);
                yaGson.toJson(new ArrayList<Category>(), allCategoriesFileWriter);
                allCategoriesFileWriter.close();
            }
            if (!commoditiesJson.exists()) {
                FileWriter allCommoditiesFileWriter = new FileWriter(commoditiesJson);
                yaGson.toJson(new ArrayList<Commodity>(), allCommoditiesFileWriter);
                allCommoditiesFileWriter.close();
            }
            if (!requestsJson.exists()) {
                FileWriter allRequestFileWriter = new FileWriter(requestsJson);
                yaGson.toJson(new ArrayList<Request>(), allRequestFileWriter);
                allRequestFileWriter.close();
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.exit(1);
        }
    }

    public static ArrayList<ManagerAccount> getManagers() throws IOException {
        FileReader fileReader = new FileReader(managersJson);
        Type type = new TypeToken<ArrayList<ManagerAccount>>() {
        }.getType();
        ArrayList<ManagerAccount> managerAccounts = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return managerAccounts;
    }

    public static void addManager(ManagerAccount managerAccount) throws IOException {
        ArrayList<ManagerAccount> managerAccounts = getManagers();
        managerAccounts.add(managerAccount);
        updateManagers(managerAccounts);
    }

    public static void addSupport(SupportAccount supportAccount) throws IOException {
        ArrayList<SupportAccount> supportAccounts = getSupports();
        supportAccounts.add(supportAccount);
        updateSupports(supportAccounts);
    }

    public static ArrayList<SupportAccount> getSupports() throws IOException {
        FileReader fileReader = new FileReader(supportJson);
        Type type = new TypeToken<ArrayList<SupportAccount>>() {
        }.getType();
        ArrayList<SupportAccount> supportAccounts = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return supportAccounts;
    }

    private static void updateSupports(ArrayList<SupportAccount> supportAccounts) throws IOException {
        FileWriter fileWriter = new FileWriter(supportJson);
        Type type = new TypeToken<ArrayList<SupportAccount>>() {
        }.getType();
        yaGson.toJson(supportAccounts, type, fileWriter);
        fileWriter.close();
    }

    public static void removeSupport(SupportAccount supportAccount) throws IOException {
        ArrayList<SupportAccount> supportAccounts = getSupports();
        for (SupportAccount account : supportAccounts) {
            if (account.getUsername().equals(supportAccount.getUsername())) {
                supportAccounts.remove(account);
                break;
            }
        }
        updateSupports(supportAccounts);
    }

    public static void removeManager(ManagerAccount managerAccount) throws IOException {
        ArrayList<ManagerAccount> managerAccounts = getManagers();
        for (ManagerAccount account : managerAccounts) {
            if (account.getUsername().equals(managerAccount.getUsername())) {
                managerAccounts.remove(account);
                break;
            }
        }
        updateManagers(managerAccounts);
    }

    private static void updateManagers(ArrayList<ManagerAccount> managerAccounts) throws IOException {
        FileWriter fileWriter = new FileWriter(managersJson);
        Type type = new TypeToken<ArrayList<ManagerAccount>>() {
        }.getType();
        yaGson.toJson(managerAccounts, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<BusinessAccount> getBusinesses() throws IOException {
        FileReader fileReader = new FileReader(businessesJson);
        Type type = new TypeToken<ArrayList<BusinessAccount>>() {
        }.getType();
        ArrayList<BusinessAccount> businessAccounts = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return businessAccounts;
    }

    public static void addBusiness(BusinessAccount managerAccount) throws IOException {
        ArrayList<BusinessAccount> businessAccounts = getBusinesses();
        businessAccounts.add(managerAccount);
        updateBusinesses(businessAccounts);
    }

    public static void removeBusiness(BusinessAccount managerAccount) throws IOException {
        ArrayList<BusinessAccount> businessAccounts = getBusinesses();
        for (BusinessAccount account : businessAccounts) {
            if (account.getUsername().equals(managerAccount.getUsername())) {
                businessAccounts.remove(account);
                break;
            }
        }
        updateBusinesses(businessAccounts);
    }

    private static void updateBusinesses(ArrayList<BusinessAccount> businessAccounts) throws IOException {
        FileWriter fileWriter = new FileWriter(businessesJson);
        Type type = new TypeToken<ArrayList<BusinessAccount>>() {
        }.getType();
        yaGson.toJson(businessAccounts, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<PersonalAccount> getPersons() throws IOException {
        FileReader fileReader = new FileReader(personsJson);
        Type type = new TypeToken<ArrayList<PersonalAccount>>() {
        }.getType();
        ArrayList<PersonalAccount> personalAccounts = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return personalAccounts;
    }

    public static void addPerson(PersonalAccount personalAccount) throws IOException {
        ArrayList<PersonalAccount> personalAccounts = getPersons();
        personalAccounts.add(personalAccount);
        updatePersons(personalAccounts);
    }

    public static void removePerson(PersonalAccount personalAccount) throws IOException {
        ArrayList<PersonalAccount> personalAccounts = getPersons();
        for (PersonalAccount account : personalAccounts) {
            if (account.getUsername().equals(personalAccount.getUsername())) {
                personalAccounts.remove(account);
                break;
            }
        }
        updatePersons(personalAccounts);
    }

    private static void updatePersons(ArrayList<PersonalAccount> personalAccounts) throws IOException {
        FileWriter fileWriter = new FileWriter(personsJson);
        Type type = new TypeToken<ArrayList<PersonalAccount>>() {
        }.getType();
        yaGson.toJson(personalAccounts, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<DiscountCode> getDiscountCodes() throws IOException {
        FileReader fileReader = new FileReader(discountCodesJson);
        Type type = new TypeToken<ArrayList<DiscountCode>>() {
        }.getType();
        ArrayList<DiscountCode> discountCodes = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return discountCodes;
    }

    public static void addDiscountCode(DiscountCode discountCode) throws IOException {
        ArrayList<DiscountCode> discountCodes = getDiscountCodes();
        discountCodes.add(discountCode);
        updateDiscountCodes(discountCodes);
    }

    public static void removeDiscountCode(DiscountCode discountCode) throws IOException {
        ArrayList<DiscountCode> discountCodes = getDiscountCodes();
        for (DiscountCode code : discountCodes) {
            if (code.getCode().equals(discountCode.getCode())) {
                discountCodes.remove(code);
                break;
            }
        }
        updateDiscountCodes(discountCodes);
    }

    private static void updateDiscountCodes(ArrayList<DiscountCode> discountCodes) throws IOException {
        FileWriter fileWriter = new FileWriter(discountCodesJson);
        Type type = new TypeToken<ArrayList<DiscountCode>>() {
        }.getType();
        yaGson.toJson(discountCodes, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<Commodity> getCommodities() throws IOException {
        FileReader fileReader = new FileReader(commoditiesJson);
        Type type = new TypeToken<ArrayList<Commodity>>() {
        }.getType();
        ArrayList<Commodity> commodities = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return commodities;
    }

    public static void addCommodity(Commodity commodity) throws IOException {
        ArrayList<Commodity> commodities = getCommodities();
        commodities.add(commodity);
        updateCommodities(commodities);
    }

    public static void removeCommodity(Commodity commodity) throws IOException {
        ArrayList<Commodity> commodities = getCommodities();
        for (Commodity commodityAlt : commodities) {
            if (commodityAlt.getCommodityId() == commodity.getCommodityId()) {
                commodities.remove(commodityAlt);
                break;
            }
        }
        updateCommodities(commodities);
    }

    private static void updateCommodities(ArrayList<Commodity> commodities) throws IOException {
        FileWriter fileWriter = new FileWriter(commoditiesJson);
        Type type = new TypeToken<ArrayList<Commodity>>() {
        }.getType();
        yaGson.toJson(commodities, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<Request> getRequests() throws IOException {
        FileReader fileReader = new FileReader(requestsJson);
        Type type = new TypeToken<ArrayList<Request>>() {
        }.getType();
        ArrayList<Request> requests = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return requests;
    }

    public static void addRequest(Request request) throws IOException {
        ArrayList<Request> requests = getRequests();
        requests.add(request);
        updateRequests(requests);
    }

    public static void removeRequest(Request request) throws IOException {
        ArrayList<Request> requests = getRequests();
        for (Request req : requests) {
            if (req.getId() == request.getId()) {
                requests.remove(req);
                break;
            }
        }
        updateRequests(requests);
    }

    private static void updateRequests(ArrayList<Request> requests) throws IOException {
        FileWriter fileWriter = new FileWriter(requestsJson);
        Type type = new TypeToken<ArrayList<Request>>() {
        }.getType();
        yaGson.toJson(requests, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<Off> getOffs() throws IOException {
        FileReader fileReader = new FileReader(offsJson);
        Type type = new TypeToken<ArrayList<Off>>() {
        }.getType();
        ArrayList<Off> offs = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return offs;
    }

    public static void addOff(Off off) throws IOException {
        ArrayList<Off> offs = getOffs();
        offs.add(off);
        updateOffs(offs);
    }

    public static void removeOff(Off off) throws IOException {
        ArrayList<Off> offs = getOffs();
        for (Off of : offs) {
            if (of.getOffID() == off.getOffID()) {
                offs.remove(of);
                break;
            }
        }
        updateOffs(offs);
    }

    private static void updateOffs(ArrayList<Off> offs) throws IOException {
        FileWriter fileWriter = new FileWriter(offsJson);
        Type type = new TypeToken<ArrayList<Off>>() {
        }.getType();
        yaGson.toJson(offs, type, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<Category> getCategories() throws IOException {
        FileReader fileReader = new FileReader(categoriesJson);
        Type type = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> categories = yaGson.fromJson(fileReader, type);
        fileReader.close();
        return categories;
    }

    public static void addCategory(Category category) throws IOException {
        ArrayList<Category> categories = getCategories();
        categories.add(category);
        updateCategories(categories);
    }

    public static void removeCategory(Category category) throws IOException {
        ArrayList<Category> categories = getCategories();
        for (Category cat : categories) {
            if (cat.getName().equals(category.getName())) {
                categories.remove(cat);
                break;
            }
        }
        updateCategories(categories);
    }

    private static void updateCategories(ArrayList<Category> categories) throws IOException {
        FileWriter fileWriter = new FileWriter(categoriesJson);
        Type type = new TypeToken<ArrayList<Category>>() {
        }.getType();
        yaGson.toJson(categories, type, fileWriter);
        fileWriter.close();
    }


    public static boolean isCategoryExist(String name) throws IOException {
        for (Category category : getCategories()) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public static Request getRequest(int id) throws Exception {
        for (Request request : getRequests()) {
            if (request.getId() == id) {
                return request;
            }
        }
        throw new Exception("Request doesn't exist");
    }

    public static boolean isCommodityExist(int id) throws Exception {
        for (Commodity commodity : getCommodities()) {
            if (commodity.getCommodityId() == id)
                return true;
        }
        return false;
    }

    public static SimpleAccount getAccountWithUserName(String username) throws IOException {
        for (ManagerAccount managerAccount : getManagers()) {
            if (managerAccount.getUsername().equals(username))
                return managerAccount;
        }
        for (PersonalAccount personalAccount : getPersons()) {
            if (personalAccount.getUsername().equals(username))
                return personalAccount;
        }
        for (BusinessAccount reseller : getBusinesses()) {
            if (reseller.getUsername().equals(username))
                return reseller;
        }

        for (SupportAccount support : getSupports()) {
            if (support.getUsername().equals(username))
                return support;
        }
        return null;
    }

    public static boolean deletePersonalAccount(String username) throws IOException {
        for (PersonalAccount person : getPersons()) {
            if (person.getUsername().equals(username)) {
                removePerson(person);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteManagerAccount(String username) throws IOException {
        for (ManagerAccount manager : getManagers()) {
            if (manager.getUsername().equals(username)) {
                removeManager(manager);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteSupportAccount(String username) throws IOException {
        for (SupportAccount support : getSupports()) {
            if (support.getUsername().equals(username)) {
                removeSupport(support);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteBusinessAccount(String username) throws IOException {
        for (BusinessAccount business : getBusinesses()) {
            if (business.getUsername().equals(username)) {
                removeBusiness(business);
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
        if (deleteSupportAccount(username)) {
            return;
        }
        throw new Exception();
    }

    public static DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        for (DiscountCode discountCode : getDiscountCodes()) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        throw new Exception("this code is not valid");
    }

    public static Commodity getCommodityById(int id) throws Exception {
        for (Commodity commodity : getCommodities()) {
            if (commodity.getCommodityId() == id) {
                return commodity;
            }
        }
        throw new Exception("there is no product with this ID");
    }

    public static boolean isUsernameExist(String username) throws IOException {
        for (ManagerAccount manager : getManagers()) {
            if (manager.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        for (BusinessAccount reseller : getBusinesses()) {
            if (reseller.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        for (PersonalAccount personalAccount : getPersons()) {
            if (personalAccount.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        for (SupportAccount support : getSupports()) {
            if (support.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    public static void updateStats() throws IOException {
        FileWriter statisticsFileWriter = new FileWriter(statisticsJson);
        yaGson.toJson(Statistics.updatedStats, statisticsFileWriter);
        statisticsFileWriter.close();
    }

    public static PersonalAccount getPersonWithUserName(String username) throws Exception {
        for (PersonalAccount personalAccount : getPersons()) {
            if (personalAccount.getUsername().equals(username))
                return personalAccount;
        }
        return null;
    }

    public static BusinessAccount getSellerWithUserName(String username) throws Exception {
        for (BusinessAccount businessAccount : getBusinesses()) {
            if (businessAccount.getUsername().equals(username))
                return businessAccount;
        }
        return null;
    }

    public static Category getCategoryWithName(String name) throws IOException {
        for (Category category : getCategories()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}