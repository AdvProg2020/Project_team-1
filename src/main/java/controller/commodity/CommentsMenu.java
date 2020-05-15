package controller.commodity;

import controller.Menu;
import model.Comment;
import model.Commodity;
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
                if (logCommodity.equals(this.commodity)) {
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
