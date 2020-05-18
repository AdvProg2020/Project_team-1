package controller.commodity;

import controller.share.Menu;
import model.commodity.Comment;
import model.commodity.Commodity;
import model.Session;
import model.account.PersonalAccount;
import model.log.BuyLog;

public class CommentsMenu extends Menu {
    private Commodity commodity;

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    private boolean hasBoughtThisCommodity() {
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

    public void addComment(String title, String content) {
        this.commodity.getAllComments().add(new Comment(Session.getOnlineAccount(), this.commodity, title, content,
                hasBoughtThisCommodity()));
    }
}
