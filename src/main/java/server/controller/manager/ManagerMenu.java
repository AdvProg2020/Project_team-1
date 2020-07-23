package server.controller.manager;

import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;
import server.dataManager.YaDataManager;

import java.util.ArrayList;
import java.util.Date;

public class ManagerMenu {
    public void addDiscountCode(String code, Date start, Date finish, int discountPercentage, int maximumDiscountPrice,
                                int maximumNumberOfUse, ArrayList<PersonalAccount> accountArrayList) throws Exception {
        ArrayList<String> userNames = new ArrayList<>();
        for (PersonalAccount personalAccount : accountArrayList) {
            userNames.add(personalAccount.getUsername());
        }
        DiscountCode discountCode = new DiscountCode(code, start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse, userNames);
        YaDataManager.addDiscountCode(discountCode);
        for (PersonalAccount account : accountArrayList) {
            account.addDiscountCode(discountCode);
        }
    }
}
