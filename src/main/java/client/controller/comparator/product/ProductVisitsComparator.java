package client.controller.comparator.product;

import common.model.commodity.Commodity;

import java.util.Comparator;

public class ProductVisitsComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Commodity) o1).getNumberOfVisits() - ((Commodity) o2).getNumberOfVisits();
    }
}
