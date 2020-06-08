package controller.commodity;

import controller.data.YaDataManager;
import controller.share.Menu;
import model.Session;
import model.account.PersonalAccount;
import model.commodity.Comment;
import model.commodity.Commodity;
import model.log.BuyLog;

import java.io.IOException;

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

    public void addComment(String title, String content) throws IOException {
        YaDataManager.removeCommodity(this.commodity);
        this.commodity.getAllComments().add(new Comment(Session.getOnlineAccount(), this.commodity, title, content,
                hasBoughtThisCommodity()));
        YaDataManager.addCommodity(this.commodity);
    }
}
