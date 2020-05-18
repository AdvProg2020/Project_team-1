package model.commodity;

import model.share.Requestable;
import model.share.Status;
import model.account.SimpleAccount;

public class Comment implements Requestable {
    private SimpleAccount account;
    private Commodity commodity;
    private String title;
    private String content;
    private boolean isABuyer;
    private Status status;

    public Comment(SimpleAccount account, Commodity commodity, String title, String content, boolean isABuyer) {
        this.account = account;
        this.commodity = commodity;
        this.title = title;
        this.content = content;
        this.isABuyer = isABuyer;
        this.status = Status.UNDER_CHECKING_FOR_CREATE;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "account=" + account +
                ", commodity=" + commodity +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isABuyer=" + isABuyer +
                ", status=" + status +
                '}';
    }

    public String getInformation() {
        if (isABuyer) {
            return "account: " + this.account.getUsername() + '\n' +
                    "did buy this? true\n" +
                    "title: " + this.title + '\n' +
                    "comment: " + this.content;
        }
        return "account: " + this.account.getUsername() + '\n' +
                "did buy this? false\n" +
                "title: " + this.title + '\n' +
                "comment: " + this.content;
    }

    public SimpleAccount getAccount() {
        return account;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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
