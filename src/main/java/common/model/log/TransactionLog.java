package common.model.log;

import server.controller.Statistics;
import server.data.YaDataManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class TransactionLog implements Serializable {
    protected int logId;
    protected Date date;
    protected Set<Integer> commoditiesId;

    public TransactionLog(Date date, Set<Integer> commoditiesId) throws IOException {
        this.date = date;
        this.commoditiesId = commoditiesId;
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

    public Set<Integer> getCommoditiesId() {
        return commoditiesId;
    }

    public String getCommoditiesNamesString() throws Exception {
        StringBuilder result = new StringBuilder();
        for (Integer commodityId : commoditiesId) {
            result.append(YaDataManager.getCommodityById(commodityId).getName());
        }
        return result.toString();
    }
}
