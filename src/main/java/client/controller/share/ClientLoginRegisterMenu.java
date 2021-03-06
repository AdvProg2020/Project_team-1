package client.controller.share;

import client.Session;
import client.view.commandline.View;
import common.model.account.*;

public class ClientLoginRegisterMenu {
    public static void login(SimpleAccount simpleAccount) {
        Session.setOnlineAccount(simpleAccount);
        if (simpleAccount instanceof ManagerAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.managerPanel);
        } else if (simpleAccount instanceof BusinessAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.sellerPanel);
        } else if (simpleAccount instanceof PersonalAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.customerPanel);
        } else if (simpleAccount instanceof SupportAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.supportMenu);
        }
    }
}
