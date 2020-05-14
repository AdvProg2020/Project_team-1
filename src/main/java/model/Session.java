package model;

import model.account.SimpleAccount;

public class Session {
    private static SimpleAccount onlineAccount;

    private Session() {
    }

    public static SimpleAccount getOnlineAccount() {
        return onlineAccount;
    }

    public static void setOnlineAccount(SimpleAccount onlineAccount) {
        Session.onlineAccount = onlineAccount;
    }
}
