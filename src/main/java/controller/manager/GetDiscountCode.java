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
        YaDataManager.removeDiscountCode(discountCode);
        discountCode.setCode(code);
        updateAccountsDiscountCode(discountCode);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeStartDate(Date startDate, DiscountCode discountCode) throws Exception {
        if (discountCode.getFinishDate().compareTo(discountCode.getStartDate()) < 0){
            throw new Exception("invalid start date");
        }
        discountCode.setStartDate(startDate);
        updateAccountsDiscountCode(discountCode);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeFinishDate(Date finishDate, DiscountCode discountCode) throws Exception {
        discountCode.setFinishDate(finishDate);
        updateAccountsDiscountCode(discountCode);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeMaximumDiscountPrice(int maximumDiscountPrice, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumDiscountPrice(maximumDiscountPrice);
        updateAccounts(discountCode);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeMaximumNumberOfUses(int maximumNumberOfUses, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumNumberOfUses(maximumNumberOfUses);
        updateDiscountCode(discountCode);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void changeDiscountPercentage(int discountPercentage , DiscountCode discountCode) throws Exception {
        discountCode.setDiscountPercentage(discountPercentage);
        updateDiscountCode(discountCode);
        updateAccounts(discountCode);
        updateDiscountCode(discountCode);
    }

    public void deleteAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName)) {
            for (SimpleAccount account : discountCode.getAccounts()) {
                if (account.getUsername().equalsIgnoreCase(userName)){
                    discountCode.deleteAccount(account);
                    updateAccountsDiscountCode(discountCode);
                    PersonalAccount personalAccount = (PersonalAccount)getAccountWithUserNameFromDatabase(userName);
                    personalAccount.removeDiscountCode(discountCode);
                    YaDataManager.removePerson(personalAccount);
                    YaDataManager.addPerson(personalAccount);
                    updateAccounts(discountCode);
                    updateDiscountCode(discountCode);
                }
                return;
            }
        }
        else
            throw new Exception("user name is not valid");
    }

    public void updateAccountsDiscountCode(DiscountCode discountCode){
        for (PersonalAccount personalAccount : discountCode.getAccounts()) {
            personalAccount.removeDiscountCode(discountCode);
            personalAccount.addDiscountCode(discountCode);
        }

    }

    public void addAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName)) {
            discountCode.addAccount((PersonalAccount) getAccountWithUserNameFromDatabase(userName));
            ((PersonalAccount)getAccountWithUserNameFromDatabase(userName)).addDiscountCode(discountCode);
            updateAccountsDiscountCode(discountCode);
            updateDiscountCode(discountCode);
            updateAccounts(discountCode);
        }
        else
            throw new Exception();
    }


    public void deleteDiscountCode(DiscountCode discountCode) throws Exception {
        YaDataManager.removeDiscountCode(discountCode);
        for (PersonalAccount account : discountCode.getAccounts()) {
            for (DiscountCode code : account.getDiscountCodes()) {
                if (code.getCode().equalsIgnoreCase(discountCode.getCode())) {
                    account.removeDiscountCode(discountCode);
                }
            }
            updateAccounts(discountCode);
        }
    }

    public SimpleAccount getAccountWithUserNameFromDatabase(String username) throws IOException {
        return YaDataManager.getAccountWithUserName(username);
    }

    public GetDiscountCode() {
        fxmlFileAddress = "../../fxml/HolyManager/ViewDiscountCode.fxml";
    }

    public void updateAccounts(DiscountCode discountCode) throws IOException {
        for (SimpleAccount account : discountCode.getAccounts()) {
            YaDataManager.removePerson((PersonalAccount) account);
            YaDataManager.addPerson((PersonalAccount) account);
        }
    }

    public void updateDiscountCode(DiscountCode discountCode) throws IOException {
        YaDataManager.removeDiscountCode(discountCode);
        YaDataManager.addDiscountCode(discountCode);
    }

}
