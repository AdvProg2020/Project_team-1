package controller.customer;

import controller.data.YaDataManager;
import controller.share.Menu;
import model.Session;
import model.account.PersonalAccount;
import model.commodity.Commodity;
import model.commodity.Score;
import model.log.BuyLog;

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
            for (Commodity commodity : log.getCommodities()) {
                if (commodity.getCommodityId() == id) {
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

    public boolean canRateProduct(int id) {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            for (Commodity commodity : log.getCommodities()) {
                if (commodity.getCommodityId() == id) {
                    for (Score score : commodity.getScores()) {
                        if (score.getAccount().getUsername().equals(account.getUsername())) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
