package model.Log;

import model.Commodity;

import java.util.ArrayList;
import java.util.Date;

public abstract class TransactionLog {
    private int logId;
    private Date date;
    private ArrayList<Commodity> commodities;

    public TransactionLog(int logId, Date date, ArrayList<Commodity> commodities) {
        this.logId = logId;
        this.date = date;
        this.commodities = commodities;
    }

    public int getLogId() {
        return logId;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }
}
