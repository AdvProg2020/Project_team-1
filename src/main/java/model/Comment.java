package model;

import model.account.SimpleAccount;

public class Comment implements Requestable {
    private SimpleAccount account;
    private Commodity commodity;
    private String title;
    private String string;
    private boolean isABuyer;
    private Status status;

    @Override
    public String toString() {
        return "Comment{" +
                "account=" + account +
                ", commodity=" + commodity +
                ", title='" + title + '\'' +
                ", string='" + string + '\'' +
                ", isABuyer=" + isABuyer +
                ", status=" + status +
                '}';
    }

    public Comment(SimpleAccount account, Commodity commodity, String title, String string, boolean isABuyer) {
        this.account = account;
        this.commodity = commodity;
        this.title = title;
        this.string = string;
        this.isABuyer = isABuyer;
        this.status = Status.UNDER_CHECKING_FOR_CREATE;
    }

    public SimpleAccount getAccount() {
        return account;
    }

    public String getTitle() {
        return title;
    }

    public String getString() {
        return string;
    }

    public boolean isABuyer() {
        return isABuyer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void addObj() {
        commodity.getAllComments().add(this);
    }
}
