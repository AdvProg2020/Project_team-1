package server.controller.commodity;

import client.Session;
import client.view.commandline.View;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import server.controller.share.Menu;
import server.controller.share.MenuHandler;

import static client.Main.outputStream;

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
        personalAccount.addToCart(commodity.getCommodityId());
        outputStream.writeUTF("add to cart " + commodity.getCommodityId());
        outputStream.flush();
    }
}
