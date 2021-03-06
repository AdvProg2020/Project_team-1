package client.controller.comparator.discount;

import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;

import java.util.Comparator;

public class UsedComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        DiscountCode code1 = (DiscountCode) o1;
        DiscountCode code2 = (DiscountCode) o2;
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.discountCodeIntegerHashMap().get(code1.getCode()) -
                account.discountCodeIntegerHashMap().get(code2.getCode());
    }
}
