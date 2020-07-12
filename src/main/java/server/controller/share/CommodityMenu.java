package server.controller.share;

import server.controller.comparator.Sort;
import server.data.YaDataManager;
import client.Session;
import common.model.commodity.Comment;
import common.model.commodity.Commodity;
import common.model.share.Status;

import java.util.ArrayList;

import static client.view.commandline.View.commentsMenu;
import static client.view.commandline.View.digestMenu;

public class CommodityMenu extends Menu {
    private Commodity commodity;
    private Commodity comparingCommodity;
    private String CommentsSortType = "title";

    public CommodityMenu() {
        fxmlFileAddress = "../../../fxml/Commodity.fxml";
    }

    public Commodity getComparingCommodity() {
        return comparingCommodity;
    }

    public void setComparingCommodity(Commodity comparingCommodity) {
        this.comparingCommodity = comparingCommodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Commodity getCommodity() {
        return this.commodity;
    }

    public void goToDigestMenu() {
        if (Session.getOnlineAccount().getAccountType().equals("personal")) {
            MenuHandler.getInstance().setCurrentMenu(digestMenu);
            digestMenu.setCommodity(this.commodity);
            digestMenu.setPreviousMenu(this);
        }
    }

    private void goToCommentsMenu() {
        MenuHandler.getInstance().setCurrentMenu(commentsMenu);
        commentsMenu.setCommodity(this.commodity);
        commentsMenu.setPreviousMenu(this);
    }

    public Commodity compare(int id) throws Exception {
        Commodity comparingCommodity = YaDataManager.getCommodityById(id);
        if (!commodity.getCategory().equals(comparingCommodity.getCategory())) {
            throw new Exception("to compare two products, they have to in the same category");
        }
        if (commodity.getCommodityId() == comparingCommodity.getCommodityId()) {
            throw new Exception("these two products are identical");
        }
        return comparingCommodity;
    }

    public void setCommentsSortType(String commentsSortType) {
        CommentsSortType = commentsSortType;
    }

    public ArrayList<Comment> getComments() throws Exception {
        goToCommentsMenu();
        ArrayList<Comment> comments = new ArrayList<>();
        comments.addAll(commodity.getAllComments());
        comments.removeIf(comment -> comment.getStatus() != Status.VERIFIED);
        Sort.sortCommentArrayList(comments, this.CommentsSortType);
        return comments;
    }
}
