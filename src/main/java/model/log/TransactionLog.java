package model.log;

import model.commodity.Commodity;

import java.util.Date;
import java.util.Set;

public abstract class TransactionLog {
    protected String logId;
    protected Date date;

    @Override
    public String toString() {
        return "TransactionLog{" +
                "logId='" + logId + '\'' +
                ", date=" + date +
                ", commodities=" + commodities +
                '}';
    }

    protected Set<Commodity> commodities;

    public TransactionLog(Date date, Set<Commodity> commodities) {
        this.date = date;
        this.commodities = commodities;
    }

    public String getLogId() {
        return logId;
    }

    public Date getDate() {
        return date;
    }

    public Set<Commodity> getCommodities() {
        return commodities;
    }

    protected String generateLogID() {
        return date.toString();
    }
}
