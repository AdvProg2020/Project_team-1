package server.controller.commodity;

import server.data.YaDataManager;
import server.controller.share.Menu;
import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.Comment;
import common.model.commodity.Commodity;
import common.model.log.BuyLog;
import common.model.share.Request;

import java.io.IOException;

public class CommentsMenu extends Menu {
    private Commodity commodity;

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public boolean hasBoughtThisCommodity() {
        if (Session.getOnlineAccount() == null || !Session.getOnlineAccount().getAccountType().equals("personal")) {
            return false;
        }
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
        Request request = new Request(comment, Session.getOnlineAccount());
        YaDataManager.addRequest(request);
    }
}
