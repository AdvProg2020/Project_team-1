package controller.comparator.off;

import model.Off;

import java.util.Comparator;

public class OffIdComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Off)o1).getOffID() - ((Off)o2).getOffID();
    }
}
