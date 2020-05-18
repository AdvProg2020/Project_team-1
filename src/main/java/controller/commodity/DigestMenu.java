package controller.commodity;

import controller.share.Menu;
import controller.share.MenuHandler;
import model.commodity.Commodity;
import model.Session;
import model.account.PersonalAccount;
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
