package client.controller.share;

import client.Session;
import client.view.commandline.View;
import common.model.account.*;
import server.controller.share.MenuHandler;

public class ClientLoginRegisterMenu {
    public static void login(SimpleAccount simpleAccount) {
        Session.setOnlineAccount(simpleAccount);
        if (simpleAccount instanceof ManagerAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
        } else if (simpleAccount instanceof BusinessAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.resellerMenu);
        } else if (simpleAccount instanceof PersonalAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.customerMenu);
        } else if (simpleAccount instanceof SupportAccount) {
            MenuHandler.getInstance().setCurrentMenu(View.supportMenu);
        }
    }
}
