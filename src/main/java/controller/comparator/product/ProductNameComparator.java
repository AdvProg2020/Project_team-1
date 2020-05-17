package controller.comparator.product;

import model.Commodity;

import java.util.Comparator;

public class ProductNameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Commodity)o1).getName().compareTo(((Commodity)o2).getName());
    }
}
