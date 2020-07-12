package common.model.log;

import server.controller.Statistics;
import common.model.commodity.Commodity;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class TransactionLog implements Serializable {
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

    public String getCommoditiesNamesString() {
        StringBuilder result = new StringBuilder();
        for (Commodity commodity : commodities) {
            result.append(commodity.getName());
        }
        return result.toString();
    }
}
