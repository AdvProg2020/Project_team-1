package controller.comparator.off;

import model.Off;

import java.util.Comparator;

public class OffEndTimeComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Off)o1).getEndTime().compareTo(((Off)o2).getEndTime());
    }
}
