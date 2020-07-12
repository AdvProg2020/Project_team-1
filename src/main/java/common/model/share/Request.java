package common.model.share;

import server.controller.Statistics;

import java.io.IOException;

public class Request {

    private Requestable obj;
    private String applicantUsername;
    private int id;

    public int getId() {
        return id;
    }

    public Request(Requestable obj, String applicantUsername) throws IOException {
        this.obj = obj;
        this.applicantUsername = applicantUsername;
        this.id = Statistics.updatedStats.requestId();
    }

    public Requestable getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "Request{" +
                "obj=" + obj.toString() +
                ", id=" + id +
                '}';
    }
}
