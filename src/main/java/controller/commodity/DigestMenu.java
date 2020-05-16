package controller.commodity;

import controller.Menu;
import controller.MenuHandler;
import model.Commodity;
import model.DataManager;
import model.Session;
import model.account.PersonalAccount;
import model.log.SellLog;
import view.View;

public class DigestMenu extends Menu {
    private Commodity commodity;

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public void addToCart() throws Exception {
        if (Session.getOnlineAccount() == null) {
            MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
            View.loginRegisterMenu.setPreviousMenu(this);
            throw new Exception("you have to login or register first");
        }
        PersonalAccount personalAccount = ((PersonalAccount) Session.getOnlineAccount());
        personalAccount.addToCart(commodity);
    }
}
