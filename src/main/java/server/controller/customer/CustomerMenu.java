package server.controller.customer;

import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;
import common.model.log.BuyLog;
import server.controller.comparator.Sort;
import server.controller.share.Menu;
import server.controller.share.MenuHandler;
import server.data.YaDataManager;

import java.util.ArrayList;

import static client.view.commandline.View.*;

public class CustomerMenu extends Menu {
    private String orderSortType = "payed";
    private String discountsSortType = "code";

    public CustomerMenu() {
        fxmlFileAddress = "../../../fxml/customer/Customer.fxml";
    }

    public double getBalance() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getCredit();
    }

    public ArrayList<DiscountCode> getMyDiscounts() throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        ArrayList<DiscountCode> discountCodes = new ArrayList<>();
        for (String code : account.getDiscountCodes()) {
            discountCodes.add(YaDataManager.getDiscountCodeWithCode(code));
        }
        Sort.sortDiscountArrayList(discountCodes, this.discountsSortType);
        discountCodes.removeIf(discountCode -> !discountCode.isOnTime() || discountCode.getMaximumNumberOfUses() ==
                account.getNumberOfTimesUsed(discountCode));
        return discountCodes;
    }

    public int getNumberOfTimesUsedDiscount(DiscountCode discountCode) {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        return account.getNumberOfTimesUsed(discountCode);
    }

    public void goToCartMenu() {
        cartMenu.setPreviousMenu(customerMenu);
        MenuHandler.getInstance().setCurrentMenu(cartMenu);
    }

    private void goToOrderMenu() {
        MenuHandler.getInstance().setCurrentMenu(orderMenu);
        orderMenu.setPreviousMenu(customerMenu);
    }

    public void setOrderSortType(String orderSortType) {
        this.orderSortType = orderSortType;
    }

    public ArrayList<BuyLog> getOrders() throws Exception {
        goToOrderMenu();
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        Sort.sortBuyLogArrayList(account.getBuyLogs(), this.orderSortType);
        return account.getBuyLogs();
    }

    public void setDiscountsSortType(String discountsSortType) {
        this.discountsSortType = discountsSortType;
    }
}
