package client;

import common.model.account.SimpleAccount;
import client.view.graphical.SceneHandler;

public class Session {
    private static SimpleAccount onlineAccount;
    private static SceneHandler sceneHandler = new SceneHandler();

    public static SceneHandler getSceneHandler() {
        return sceneHandler;
    }

    private Session() {
    }

    public static SimpleAccount getOnlineAccount() {
        return onlineAccount;
    }

    public static void setOnlineAccount(SimpleAccount onlineAccount) {
        Session.onlineAccount = onlineAccount;
    }
}
