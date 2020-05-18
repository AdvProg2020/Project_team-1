package controller.comparator.buy_log;

import model.log.BuyLog;

import java.util.Comparator;

public class DeductedMoneyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) (((BuyLog) o1).getDeductedMoney() - ((BuyLog) o2).getDeductedMoney());
    }
}
