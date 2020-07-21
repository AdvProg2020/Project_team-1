package server.controller;

import server.dataManager.YaDataManager;

import java.io.IOException;

public class Statistics {

    private int lastCommodityId;
    private int lastOffId;
    private int lastRequestId;
    private int lastTransactionLogId;
    private double wage;
    private double minimumCurrency;

    public static Statistics updatedStats = new Statistics();

    public static void setUpdatedStats(Statistics statistics) {
        updatedStats = statistics;
    }

    public int commodityId() throws IOException {
        ++lastCommodityId;
        YaDataManager.updateStats();
        return lastCommodityId;
    }

    public int offId() throws IOException {
        ++lastOffId;
        YaDataManager.updateStats();
        return lastOffId;
    }

    public int requestId() throws IOException {
        ++lastRequestId;
        YaDataManager.updateStats();
        return lastRequestId;
    }

    public int transactionLogId() throws IOException {
        ++lastTransactionLogId;
        YaDataManager.updateStats();
        return lastTransactionLogId;
    }

    public void setMinimumCurrency(Double minimumCurrency) throws IOException{
        this.minimumCurrency = minimumCurrency;
        YaDataManager.updateStats();
    }

    public double getWage() {
        return wage;
    }

    public double getMinimumCurrency() {
        return minimumCurrency;
    }

    public void setWage(Double wage) throws IOException {
        this.wage = wage;
        YaDataManager.updateStats();
    }
}
