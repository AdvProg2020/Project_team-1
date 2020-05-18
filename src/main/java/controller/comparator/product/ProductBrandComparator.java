package controller.comparator.product;

import model.commodity.Commodity;

import java.util.Comparator;

public class ProductBrandComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Commodity)o1).getBrand().compareTo(((Commodity)o2).getBrand());
    }
}

