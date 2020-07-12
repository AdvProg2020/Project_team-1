package server.controller.comparator.buy_log;

import common.model.log.BuyLog;

import java.util.Comparator;

public class PayedMoneyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) (((BuyLog) o1).getPayedMoney() - ((BuyLog) o2).getPayedMoney());
    }
}
