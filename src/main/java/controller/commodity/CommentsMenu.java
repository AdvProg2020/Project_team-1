package controller.commodity;

import controller.data.YaDataManager;
import controller.share.Menu;
import model.Session;
import model.account.PersonalAccount;
import model.commodity.Comment;
import model.commodity.Commodity;
import model.log.BuyLog;
import model.share.Request;

import java.io.IOException;

public class CommentsMenu extends Menu {
    private Commodity commodity;

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public boolean hasBoughtThisCommodity() {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            for (Commodity logCommodity : log.getCommodities()) {
                if (logCommodity.getCommodityId() == this.commodity.getCommodityId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addComment(String title, String content) throws IOException {
        Comment comment = new Comment(Session.getOnlineAccount(), this.commodity, title, content,
                hasBoughtThisCommodity());
        this.commodity.getAllComments().add(comment);
        Request request = new Request(comment, Session.getOnlineAccount());
        YaDataManager.addRequest(request);
    }
}
