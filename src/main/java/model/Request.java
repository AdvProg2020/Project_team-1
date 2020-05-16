package model;

import model.account.SimpleAccount;

import java.io.IOException;

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
                "obj=" + obj.toString() +
                ", simpleAccount=" + simpleAccount +
                '}';
    }

    public Request(Requestable obj, SimpleAccount simpleAccount) throws IOException {
        this.obj = obj;
        this.simpleAccount = simpleAccount;
        this.id = DataManager.getAllRequests().length + 1;
    }


    public Requestable getObj() {
        return obj;
    }

    public SimpleAccount getSimpleAccount() {
        return simpleAccount;
    }
}
