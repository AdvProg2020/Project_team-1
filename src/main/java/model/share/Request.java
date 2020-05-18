package model.share;

import controller.data.YaDataManager;
import model.account.SimpleAccount;

import java.io.IOException;

public class Request {

    private Requestable obj;
    private SimpleAccount simpleAccount;
    private int id;
    private static int counter = 0;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "obj=" + obj.toString() +
                ", simpleAccount=" + simpleAccount.toString() +
                '}';
    }

    public Request(Requestable obj, SimpleAccount simpleAccount) throws IOException {
        this.obj = obj;
        this.simpleAccount = simpleAccount;
        this.id = YaDataManager.getRequests().size() + 1;
    }


    public Requestable getObj() {
        return obj;
    }

    public SimpleAccount getSimpleAccount() {
        return simpleAccount;
    }
}
