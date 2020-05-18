package model.share;

import controller.data.YaDataManager;
import model.Statistics;
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
                ", simpleAccount=" + simpleAccount.toString() +
                '}';
    }

    public Request(Requestable obj, SimpleAccount simpleAccount) throws IOException {
        this.obj = obj;
        this.simpleAccount = simpleAccount;
        this.id = Statistics.updatedStats.requestId();
    }


    public Requestable getObj() {
        return obj;
    }

    public SimpleAccount getSimpleAccount() {
        return simpleAccount;
    }
}
