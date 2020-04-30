package controller;

import model.DiscountCode;
import model.SuperMarket;

public class ManageDiscountsMenu extends ProductsMenu implements CommandProcess {
    public DiscountCode getDiscountWithCode(String code) throws Exception {
        return SuperMarket.getDiscountWithCode(code);
    }

    public void removeDiscount(String code) throws Exception {
        SuperMarket.getAllDiscountCodes().remove(SuperMarket.getDiscountWithCode(code));
    }

    @Override
    public String runCommand(String command) {
        return null;
    }
}
