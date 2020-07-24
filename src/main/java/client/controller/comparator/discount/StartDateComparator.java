package client.controller.comparator.discount;

import common.model.commodity.DiscountCode;

import java.util.Comparator;

public class StartDateComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((DiscountCode) o1).getStartDate().compareTo(((DiscountCode) o2).getStartDate());
    }
}
