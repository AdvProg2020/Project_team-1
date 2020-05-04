package controller;

import model.DiscountCode;
import model.SuperMarket;

import java.util.Date;

public class ManageDiscountsMenu extends ProductsMenu implements CommandProcess {
    public DiscountCode getDiscountWithCode(String code) throws Exception {
        return SuperMarket.getDiscountWithCode(code);
    }

    public void removeDiscount(String code) throws Exception {
        SuperMarket.getAllDiscountCodes().remove(SuperMarket.getDiscountWithCode(code));
    }

    public void editStartDate(Date date, DiscountCode discountCode) throws Exception {
        discountCode.setStartDate(date);
    }

    public void editFinishDate(Date date, DiscountCode discountCode) throws Exception {
        discountCode.setFinishDate(date);
    }

    public void editDiscountPercentage(int percentage, DiscountCode discountCode) throws Exception {
        discountCode.setDiscountPercentage(percentage);
    }

    public void editMaximumPrice(int price, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumDiscountPrice(price);
    }

    public void editMaximumNumberOfUses(int number, DiscountCode discountCode) throws Exception {
        discountCode.setMaximumNumberOfUses(number);
    }

}
