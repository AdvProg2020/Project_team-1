package controller.comparator.sell_log;

import model.log.SellLog;

import java.util.Comparator;

public class SellLogReceivedMoneyComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return (int) Math.floor(((SellLog)o1).getReceivedMoney() - ((SellLog)o2).getReceivedMoney());
    }
}
