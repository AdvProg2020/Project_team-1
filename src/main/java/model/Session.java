package model;

import model.account.SimpleAccount;

public class Session {
    private static SimpleAccount simpleAccount;

    private Session() {
    }

    public static SimpleAccount getSimpleAccount() {
        return simpleAccount;
    }

    public static void setSimpleAccount(SimpleAccount simpleAccount) {
        Session.simpleAccount = simpleAccount;
    }
}
