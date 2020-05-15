package controller.customer;

import controller.Menu;
import model.Session;
import model.account.PersonalAccount;
import model.log.BuyLog;

public class OrderMenu extends Menu {
    public BuyLog getOrderWithId(String id) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            if (log.getLogId().equals(id)) {
                return log;
            }
        }
        throw new Exception("you don't have any order with this ID");
    }
}
