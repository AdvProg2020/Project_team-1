package model;

import model.account.SimpleAccount;

public class Request {
    private Requestable obj;
    private SimpleAccount simpleAccount;
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "obj=" + obj +
                ", simpleAccount=" + simpleAccount +
                '}';
    }

    public Request(Requestable obj, SimpleAccount simpleAccount,int id) {
        this.obj = obj;
        this.simpleAccount = simpleAccount;
        this.id = id;
    }

    public Requestable getObj() {
        return obj;
    }

    public SimpleAccount getSimpleAccount() {
        return simpleAccount;
    }
}
