package server.controller.comparator.comment;

import common.model.commodity.Comment;

import java.util.Comparator;

public class TitleComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Comment) o1).getTitle().compareTo(((Comment) o2).getTitle());
    }
}
