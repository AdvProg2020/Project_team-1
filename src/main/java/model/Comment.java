package model;

import model.account.SimpleAccount;

public class Comment implements Requestable{
    private SimpleAccount account;
    private Commodity commodity;
    private String title;
    private String string;
    private boolean isABuyer;
    private boolean isApproved;

    public Comment(SimpleAccount account, Commodity commodity, String title, String string, boolean isABuyer) {
        this.account = account;
        this.commodity = commodity;
        this.title = title;
        this.string = string;
        this.isABuyer = isABuyer;
        this.isApproved = false;
    }

    public void setStatus(boolean isApproved) {
        this.isApproved = isApproved;
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

    public boolean isApproved() {
        return isApproved;
    }
}