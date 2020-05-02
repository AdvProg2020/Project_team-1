package model;

import model.account.SimpleAccount;

public class Request {
    private Requestable obj;
    private SimpleAccount simpleAccount;

    @Override
    public String toString() {
        return "Request{" +
                "obj=" + obj +
                ", simpleAccount=" + simpleAccount +
                '}';
    }

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
