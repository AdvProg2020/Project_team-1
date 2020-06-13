package model.log;

import model.Statistics;
import model.commodity.Commodity;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

public abstract class TransactionLog {
    protected int logId;
    protected Date date;
    protected Set<Commodity> commodities;

    public TransactionLog(Date date, Set<Commodity> commodities) throws IOException {
        this.date = date;
        this.commodities = commodities;
        logId = Statistics.updatedStats.transactionLogId();
    }

    public int getLogId() {
        return logId;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return date.toString();
    }

    public Set<Commodity> getCommodities() {
        return commodities;
    }
}
