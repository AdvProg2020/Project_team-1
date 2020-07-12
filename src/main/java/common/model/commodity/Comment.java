package common.model.commodity;

import common.model.share.Requestable;
import common.model.share.Status;
import server.data.YaDataManager;

import java.io.Serializable;

public class Comment implements Requestable, Serializable {
    private String username;
    private int commodityId;
    private String title;
    private String content;
    private boolean isABuyer;
    private Status status;

    public Comment(String username, int commodityId, String title, String content, boolean isABuyer) {
        this.username = username;
        this.commodityId = commodityId;
        this.title = title;
        this.content = content;
        this.isABuyer = isABuyer;
        this.status = Status.UNDER_CHECKING_FOR_CREATE;
    }

    @Override
    public String toString() {
        System.out.println(commodityId);
        return "Comment{" +
                "account=" + username +
                ", commodity=" + commodityId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isABuyer=" + isABuyer +
                '}';
    }

    public String getInformation() {
        if (isABuyer) {
            return "account: " + this.username + '\n' +
                    "did buy this? true\n" +
                    "title: " + this.title + '\n' +
                    "comment: " + this.content;
        }
        return "account: " + this.username + '\n' +
                "did buy this? false\n" +
                "title: " + this.title + '\n' +
                "comment: " + this.content;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getCommodityId() {
        return commodityId;
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
    public void addObj() throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(this.commodityId);
        commodity.getAllComments().add(this);
        YaDataManager.removeCommodity(commodity);
        YaDataManager.addCommodity(commodity);
    }
}
