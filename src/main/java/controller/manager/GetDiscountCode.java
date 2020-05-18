package controller.manager;

import controller.share.Menu;
import model.commodity.DiscountCode;
import controller.data.YaDataManager;
import model.account.SimpleAccount;

import java.io.IOException;
import java.util.Date;

public class GetDiscountCode extends Menu {
    public DiscountCode getDiscountCode(String code) throws Exception {
        return YaDataManager.getDiscountCodeWithCode(code);
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

    public void deleteAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName))
            discountCode.deleteAccount(getAccountWithUserNameFromDatabase(userName));
        else
            throw new Exception();
    }

    public void addAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName))
            discountCode.addAccount(getAccountWithUserNameFromDatabase(userName));
        else
            throw new Exception();
    }


    public void deleteDiscountCode(DiscountCode discountCode) throws Exception {
        YaDataManager.removeDiscountCode(discountCode);
    }

    public SimpleAccount getAccountWithUserNameFromDatabase(String username) throws IOException {
        return YaDataManager.getAccountWithUserName(username);
    }


}
