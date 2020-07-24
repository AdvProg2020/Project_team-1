package client.controller.comparator.sell_log;

import common.model.log.SellLog;

import java.util.Comparator;

public class SellLogDeductedMoneyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) Math.floor(((SellLog)o1).getDeductedMoneyOnSale() - ((SellLog)o2).getDeductedMoneyOnSale());
    }
}
