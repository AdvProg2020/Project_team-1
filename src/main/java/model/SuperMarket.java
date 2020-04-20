package model;

import model.log.TransactionLog;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class SuperMarket {
    private static ArrayList<TransactionLog> allLogs = new ArrayList<TransactionLog>();
    private static ArrayList<Category> allCategory = new ArrayList<Category>();
    private static ArrayList<Commodity> allCommodities = new ArrayList<Commodity>();
    private static ArrayList<Score> allScores = new ArrayList<Score>();
    private static ArrayList<Request> allRequests= new ArrayList<Request>();
    private static ArrayList<SimpleAccount> allAccounts = new ArrayList<SimpleAccount>();

    public static ArrayList<SimpleAccount> getAllAccounts(){
        return  allAccounts;
    }
    public static ArrayList<TransactionLog> getAllLogs(){
        return allLogs;
    }

    public static ArrayList<Category> getAllCategory(){
        return allCategory;
    }

    public static ArrayList<Commodity> getAllCommodities(){
        return allCommodities;
    }


    public static ArrayList<Score> getAllScores(){
        return allScores;
    }

    public static ArrayList<Request> getAllRequests(){
        return allRequests;
    }



    public static void addRequest(Request request){
        allRequests.add(request);
    }

    public static void addLog(TransactionLog transactionLog){
        allLogs.add(transactionLog);
    }
    public static void addCategory(Category category){
        allCategory.add(category);
    }
    public static void addCommodities(Commodity commodity){
        allCommodities.add(commodity);
    }
    public static void addScore(Score score){
        allScores.add(score);
    }
    public static Commodity getCommodityByName(String name){
        for (Commodity commodity : allCommodities) {
            if (commodity.getName().equals(name))
                return commodity;
        }
        return null;
    }
    public static Commodity getCommodityById(int commodityId){
        for (Commodity commodity : allCommodities) {
            if (commodity.getCommodityId() == commodityId)
                return commodity;
        }
        return null;
    }
    public static TransactionLog getLogByLogId(int logId){
        for (TransactionLog log : allLogs) {
            if (log.getLogId() == logId)
                return log;
        }
        return null;
    }

    public static boolean isThereAnyAccountWithThisUserAndPassword(String user, String password){
        for (SimpleAccount account : allAccounts) {
            if (checkPassword(account,user) && checkUser(account , user)){
                return true;
            }
        }
        return false;
    }

    public static SimpleAccount getAccountByUserAndPassword(String user, String password){
        for (SimpleAccount account : allAccounts) {
            if (checkPassword(account,user) && checkUser(account , user)){
                return account;
            }
        }
        return null;
    }

    public static Boolean checkPassword(SimpleAccount account, String user){
        return account.getUsername().equals(user);
    }

    public static Boolean checkUser(SimpleAccount account, String password){
        return account.isPasswordCorrect(password);
    }



}

