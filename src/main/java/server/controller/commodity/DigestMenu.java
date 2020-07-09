package server.controller.commodity;

import server.data.YaDataManager;
import server.controller.share.Menu;
import server.controller.share.MenuHandler;
import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import client.view.commandline.View;

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
        YaDataManager.removePerson(personalAccount);
        YaDataManager.addPerson(personalAccount);
    }
}
