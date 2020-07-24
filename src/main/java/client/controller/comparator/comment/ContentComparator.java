package client.controller.comparator.comment;

import common.model.commodity.Comment;

import java.util.Comparator;

public class ContentComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Comment) o1).getContent().compareTo(((Comment) o2).getContent());
    }
}
