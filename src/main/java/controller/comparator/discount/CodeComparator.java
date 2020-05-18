package controller.comparator.discount;

import model.commodity.DiscountCode;

import java.util.Comparator;

public class CodeComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((DiscountCode) o1).getCode().compareTo(((DiscountCode) o2).getCode());
    }
}
