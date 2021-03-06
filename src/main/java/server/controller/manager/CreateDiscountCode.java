package server.controller.manager;

import common.model.account.PersonalAccount;
import server.dataManager.YaDataManager;
import common.model.commodity.DiscountCode;
import client.view.graphical.holyManager.AddPersonToDiscountCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static client.view.commandline.View.managerMenu;

public class CreateDiscountCode {
    public void checkCode(String code) throws Exception {
        for (DiscountCode discountCode : YaDataManager.getDiscountCodes()) {
            if (discountCode.getCode().equals(code))
                throw new Exception("Invalid discount code");
        }
    }

    public boolean checkDateAndNumbers(Date finish , Date start , int discountPercentage ,
                             int maximumDiscountPrice, int maximumNumberOfUse){
        if (finish.compareTo(start) < 0) {
            return false;
        }
        if (discountPercentage <= 0 || discountPercentage > 100)
            return false;
        if (maximumDiscountPrice <= 0)
            return false;
        return maximumNumberOfUse > 0;
    }

    public void createDiscountCodeNC(String code , String stringStart , String stringFinish ,
                                     String discountPercentageString , String maximumDiscountPriceString , String maximumNumberOfUseString , ArrayList<PersonalAccount> accounts) throws Exception {
        checkCode(code);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date start = null;
        try {
            start = format.parse(stringStart);
        } catch (ParseException e) {
            throw new Exception("Invalid date format");
        }
        Date finish = null;
        try {
            finish = format.parse(stringFinish);
        } catch (ParseException e) {
            throw new Exception("Invalid date format");
        }
        int discountPercentage;
        try {
            discountPercentage = Integer.parseInt(discountPercentageString);
        } catch (Exception e) {
            throw new Exception("Invalid discount percentage");
        }
        int maximumDiscountPrice;
        try {
            maximumDiscountPrice = Integer.parseInt(maximumDiscountPriceString);
        } catch (Exception e) {
            throw new Exception("invalid maximum discount price");
        }
        int maximumNumberOfUse;
        try {
            maximumNumberOfUse = Integer.parseInt(maximumNumberOfUseString);
        } catch (Exception e) {
            throw new Exception("invalid maximum number of user");
        }
        if (!checkDateAndNumbers(finish,start,discountPercentage,maximumDiscountPrice,maximumNumberOfUse))
            throw new Exception("Invalid entry.");
        System.out.println(AddPersonToDiscountCode.getAccounts());
        managerMenu.addDiscountCode(code, start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse, accounts);
    }

}
