package controller.customer;

import controller.share.Menu;
import model.commodity.Commodity;
import model.commodity.Score;
import model.Session;
import model.account.PersonalAccount;
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
                    commodity.updateAverageScore(rate);
                    return;
                }
            }
        }
        throw new Exception("you didn't buy this product, so you can't rate it");
    }
}
