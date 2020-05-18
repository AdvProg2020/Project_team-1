package controller.comparator.account;

import model.account.SimpleAccount;

import java.util.Comparator;

public class UsernameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((SimpleAccount)o1).getUsername().compareTo(((SimpleAccount)o2).getUsername());
    }
}
