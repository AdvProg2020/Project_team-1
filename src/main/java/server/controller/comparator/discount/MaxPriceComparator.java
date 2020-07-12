package server.controller.comparator.discount;

import common.model.commodity.DiscountCode;

import java.util.Comparator;

public class MaxPriceComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((DiscountCode) o1).getMaximumDiscountPrice() - (((DiscountCode) o2).getMaximumDiscountPrice());
    }
}
