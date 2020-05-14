package controller;

import model.DataManager;
import model.DiscountCode;
import model.account.SimpleAccount;

import java.util.Date;

public class GetDiscountCode extends Menu {
    public DiscountCode getDiscountCode(String code) throws Exception {
        return DataManager.getDiscountCodeWithCode(code);
    }

    public void changeCode(String code, DiscountCode discountCode) {
        discountCode.setCode(code);
    }

    public void changeStartDate(Date startDate, DiscountCode discountCode) throws Exception {
        discountCode.setStartDate(startDate);
    }

    public void changeFinishDate(Date finishDate, DiscountCode discountCode) throws Exception {
        discountCode.setFinishDate(finishDate);
    }

    public void changeMaximumDiscountPrice(int maximumDiscountPrice, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumDiscountPrice(maximumDiscountPrice);
    }

    public void changeMaximumNumberOfUses(int maximumNumberOfUses, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumNumberOfUses(maximumNumberOfUses);
    }

    public void deleteAccount(SimpleAccount simpleAccount, DiscountCode discountCode) throws Exception {
        discountCode.deleteAccount(simpleAccount);
    }


}
