package model;

import model.account.PersonalAccount;
import model.account.SimpleAccount;

public class Request {
    private Requestable obj;
    private SimpleAccount simpleAccount;

    public Request(Requestable obj, SimpleAccount simpleAccount) {
        this.obj = obj;
        this.simpleAccount = simpleAccount;
    }

    public Requestable getObj() {
        return obj;
    }

    public SimpleAccount getSimpleAccount() {
        return simpleAccount;
    }
}
