package model;

import Model.Account.SimpleAccount;

import java.util.ArrayList;

public class SuperMarket {
    private static ArrayList<TransactionLog> allLogs = new ArrayList<>();
    private static ArrayList<Category> allCategory = new ArrayList<>();
    private static ArrayList<Commodity> allCommodities = new ArrayList<>();
    private static ArrayList<Score> allScores = new ArrayList<>();
    private static ArrayList<String> allRequests= new ArrayList<>();
    private static ArrayList<SimpleAccount> allAccounts = new ArrayList<>();

    public static ArrayList<SimpleAccount> getAllAccounts(){
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
    }

    public static ArrayList<String> getAllRequests(){
    }



    public static void updateAllRequest(){
    }

    public static void updateAllLogs(){
    }
    public static void updateAllCategory(){
    }
    public static void updateAllCommodities(){
    }
    public static void updateAlLScore(){
    }
    public static Commodity getCommodityByName(String name){
    }
    public static Commodity getCommodityById(int commodityId){
    }
    public static TransactionLog getLogByLogId(int logId){
    }
    public static void getAccountByUserAndPassword(String user, String password){
    }



}

