package model.log;

import model.Commodity;

import java.util.ArrayList;
import java.util.Date;

public abstract class TransactionLog {
    protected String logId;
    protected Date date;
    protected ArrayList<Commodity> commodities;

    public TransactionLog(Date date, ArrayList<Commodity> commodities) {
        this.date = date;
        this.commodities = commodities;
    }

    public String getLogId() {
        return logId;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    protected String generateLogID() {
        return date.toString();
    }
}
