package server.controller.customer;

import common.model.account.PersonalAccount;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class CustomerMenu {
    public void walletTransaction(double amount, PersonalAccount personalAccount) throws IOException {
        personalAccount.addToCredit(amount);
        YaDataManager.removePerson(personalAccount);
        YaDataManager.addPerson(personalAccount);
    }
}
