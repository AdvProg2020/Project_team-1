package controller.comparator.product;

import model.Commodity;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Commodity)o1).getPrice() - ((Commodity)o2).getPrice();
    }
}
