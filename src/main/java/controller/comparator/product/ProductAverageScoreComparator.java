package controller.comparator.product;

import model.Commodity;

import java.util.Comparator;

public class ProductAverageScoreComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) (((Commodity) o1).getAverageScore() - ((Commodity) o2).getAverageScore());
    }
}
