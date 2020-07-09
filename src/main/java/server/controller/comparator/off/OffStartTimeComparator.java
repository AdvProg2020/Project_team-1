package server.controller.comparator.off;

import common.model.commodity.Off;

import java.util.Comparator;

public class OffStartTimeComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Off)o1).getStartTime().compareTo(((Off)o2).getStartTime());
    }
}
