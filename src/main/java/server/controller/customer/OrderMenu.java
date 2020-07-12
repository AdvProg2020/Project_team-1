package server.controller.customer;

import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import common.model.commodity.Score;
import common.model.log.BuyLog;
import server.controller.share.Menu;
import server.data.YaDataManager;

public class OrderMenu extends Menu {
    public BuyLog getOrderWithId(int id) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            if (log.getLogId() == id) {
                return log;
            }
        }
        throw new Exception("you don't have any order with this ID");
    }

    public void rateProduct(int id, int rate) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            for (int commodityId : log.getCommoditiesId()) {
                if (commodityId == id) {
                    Commodity commodity = YaDataManager.getCommodityById(commodityId);
                    new Score(account, rate, commodity);
                    YaDataManager.removeCommodity(commodity);
                    commodity.updateAverageScore(new Score(account, rate, commodity));
                    YaDataManager.addCommodity(commodity);
                    return;
                }
            }
        }
        throw new Exception("you didn't buy this product, so you can't rate it");
    }

    public boolean canRateProduct(int id) throws Exception {
        if (Session.getOnlineAccount() == null || !Session.getOnlineAccount().getAccountType().equalsIgnoreCase("personal")) {
            return false;
        }
        try {
            PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
            for (BuyLog log : account.getBuyLogs()) {
                for (int commodityId : log.getCommoditiesId()) {
                    if (commodityId == id) {
                        Commodity commodity = YaDataManager.getCommodityById(commodityId);
                        for (Score score : commodity.getScores()) {
                            if (score.getAccount().getUsername().equals(account.getUsername())) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
        return false;
    }
}
