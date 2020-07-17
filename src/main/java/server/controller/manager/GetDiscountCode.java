package server.controller.manager;

import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import common.model.commodity.DiscountCode;
import server.controller.share.Menu;
import server.dataManager.YaDataManager;

import java.io.IOException;
import java.util.Date;

public class GetDiscountCode extends Menu {
    public DiscountCode getDiscountCode(String code) throws Exception {
        return YaDataManager.getDiscountCodeWithCode(code);
    }

    public void changeCode(String code, DiscountCode discountCode) throws Exception {
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
            for (String username : discountCode.getAccountsUsername()) {
                if (userName.equalsIgnoreCase(username)) {
                    discountCode.deleteAccount(YaDataManager.getAccountWithUserName(username));
                    updateAccountsDiscountCode(discountCode);
                    PersonalAccount personalAccount = (PersonalAccount) getAccountWithUserNameFromDatabase(userName);
                    personalAccount.removeDiscountCode(discountCode);
                    YaDataManager.removePerson(personalAccount);
                    YaDataManager.addPerson(personalAccount);
                    updateAccounts(discountCode);
                    updateDiscountCode(discountCode);
                }
                return;
            }
        } else
            throw new Exception("user name is not valid");
    }

    public void updateAccountsDiscountCode(DiscountCode discountCode) throws Exception {
        for (String username : discountCode.getAccountsUsername()) {
            PersonalAccount personalAccount = YaDataManager.getPersonWithUserName(username);
            personalAccount.removeDiscountCode(discountCode);
            personalAccount.addDiscountCode(discountCode);
        }

    }

    public void addAccount(String userName, DiscountCode discountCode) throws Exception {
        if (YaDataManager.isUsernameExist(userName)) {
            discountCode.addAccount((PersonalAccount) getAccountWithUserNameFromDatabase(userName));
            ((PersonalAccount) getAccountWithUserNameFromDatabase(userName)).addDiscountCode(discountCode);
            updateAccountsDiscountCode(discountCode);
            updateDiscountCode(discountCode);
            updateAccounts(discountCode);
        }
        else
            throw new Exception();
    }


    public void deleteDiscountCode(DiscountCode discountCode) throws Exception {
        YaDataManager.removeDiscountCode(discountCode);
        for (String username : discountCode.getAccountsUsername()) {
            PersonalAccount account = YaDataManager.getPersonWithUserName(username);
            for (String code : account.getDiscountCodes()) {
                if (code.equalsIgnoreCase(discountCode.getCode())) {
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
        fxmlFileAddress = "../../../fxml/HolyManager/ViewDiscountCode.fxml";
    }

    public void updateAccounts(DiscountCode discountCode) throws Exception {
        for (String username : discountCode.getAccountsUsername()) {
            PersonalAccount account = YaDataManager.getPersonWithUserName(username);
            YaDataManager.removePerson(account);
            YaDataManager.addPerson(account);
        }
    }

    public void updateDiscountCode(DiscountCode discountCode) throws IOException {
        YaDataManager.removeDiscountCode(discountCode);
        YaDataManager.addDiscountCode(discountCode);
    }

}
