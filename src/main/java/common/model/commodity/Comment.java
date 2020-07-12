package common.model.commodity;

import server.data.YaDataManager;
import common.model.account.SimpleAccount;
import common.model.share.Requestable;
import common.model.share.Status;

import java.io.IOException;
import java.io.Serializable;

public class Comment implements Requestable , Serializable {
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
        System.out.println(commodity);
        return "Comment{" +
                "account=" + account.getUsername() +
                ", commodity=" + commodity.getName() +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isABuyer=" + isABuyer +
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

    public Commodity getCommodity() {
        return commodity;
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
    public void addObj() throws IOException {
        YaDataManager.removeCommodity(this.commodity);
        this.commodity.getAllComments().add(this);
        YaDataManager.addCommodity(this.commodity);
    }
}