package controller.manager;

import controller.share.Menu;
import model.account.PersonalAccount;
import model.commodity.DiscountCode;
import controller.data.YaDataManager;
import model.account.SimpleAccount;

import java.io.IOException;
import java.util.Date;

public class GetDiscountCode extends Menu {
    public DiscountCode getDiscountCode(String code) throws Exception {
        return YaDataManager.getDiscountCodeWithCode(code);
    }

    public void changeCode(String code, DiscountCode discountCode) throws IOException {
        discountCode.setCode(code);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeStartDate(Date startDate, DiscountCode discountCode) throws Exception {
        if (discountCode.getFinishDate().compareTo(discountCode.getStartDate()) < 0){
            throw new Exception("invalid start date");
        }
        discountCode.setStartDate(startDate);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeFinishDate(Date finishDate, DiscountCode discountCode) throws Exception {
        discountCode.setFinishDate(finishDate);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeMaximumDiscountPrice(int maximumDiscountPrice, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumDiscountPrice(maximumDiscountPrice);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeMaximumNumberOfUses(int maximumNumberOfUses, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumNumberOfUses(maximumNumberOfUses);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void deleteAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName)) {
            for (SimpleAccount account : discountCode.getAccounts()) {
                if (account.getUsername().equalsIgnoreCase(userName)){
                    discountCode.deleteAccount(account);
                    for (DiscountCode code : ((PersonalAccount) getAccountWithUserNameFromDatabase(userName)).getDiscountCodes()) {
                        if (code.getCode().equalsIgnoreCase(discountCode.getCode()))
                            ((PersonalAccount) getAccountWithUserNameFromDatabase(userName)).getDiscountCodes().remove(code);
                    }
                    YaDataManager.removePerson((PersonalAccount)getAccountWithUserNameFromDatabase(userName));
                    YaDataManager.addPerson((PersonalAccount)getAccountWithUserNameFromDatabase(userName));
                }
            }
        }
        else
            throw new Exception();
    }

    public void addAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName)) {
            discountCode.addAccount((PersonalAccount) getAccountWithUserNameFromDatabase(userName));
            ((PersonalAccount)getAccountWithUserNameFromDatabase(userName)).addDiscountCode(discountCode);
            updateDiscountCode(discountCode);
            updateAccounts(discountCode);
        }
        else
            throw new Exception();
    }


    public void deleteDiscountCode(DiscountCode discountCode) throws Exception {
        YaDataManager.removeDiscountCode(discountCode);
        for (SimpleAccount account : discountCode.getAccounts()) {
            for (DiscountCode code : ((PersonalAccount) account).getDiscountCodes()) {
                if (code.getCode().equalsIgnoreCase(discountCode.getCode()))
                    ((PersonalAccount) account).getDiscountCodes().remove(code);
            }
            updateAccounts(discountCode);
        }
    }

    public SimpleAccount getAccountWithUserNameFromDatabase(String username) throws IOException {
        return YaDataManager.getAccountWithUserName(username);
    }

    public void updateAccounts(DiscountCode discountCode) throws IOException {
        for (SimpleAccount account : discountCode.getAccounts()) {
            ((PersonalAccount) account).r
            YaDataManager.removePerson((PersonalAccount) account);
            YaDataManager.addPerson((PersonalAccount) account);
        }
    }

    public void updateDiscountCode(DiscountCode discountCode) throws IOException {
        YaDataManager.removeDiscountCode(discountCode);
        YaDataManager.addDiscountCode(discountCode);
    }

}
