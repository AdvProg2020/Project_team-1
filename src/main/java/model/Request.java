package model;

public class Request {
    private Requestable obj;

    public Request(Requestable obj) {
        this.obj = obj;
    }

    public Requestable getObj() {
        return obj;
    }

}
