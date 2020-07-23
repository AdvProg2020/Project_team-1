package server.controller.reseller;

import common.model.account.BusinessAccount;
import server.dataManager.YaDataManager;

import java.io.IOException;

public class ResellerMenu {
    public void walletTransaction(double amount, BusinessAccount businessAccount) throws IOException {
        businessAccount.addToCredit(amount);
        YaDataManager.removeBusiness(businessAccount);
        YaDataManager.addBusiness(businessAccount);
    }
}
