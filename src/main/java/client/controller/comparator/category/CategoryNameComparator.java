package client.controller.comparator.category;

import common.model.commodity.Category;

import java.util.Comparator;

public class CategoryNameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Category)o1).getName().compareTo(((Category)o2).getName());
    }
}
