package model;

import model.account.SimpleAccount;
import view.graphical.SceneHandler;

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
