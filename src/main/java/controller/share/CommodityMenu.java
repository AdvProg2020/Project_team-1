package controller.share;

import controller.comparator.Sort;
import controller.data.YaDataManager;
import model.Session;
import model.commodity.Comment;
import model.commodity.Commodity;
import model.share.Status;

import java.util.ArrayList;

import static view.commandline.View.commentsMenu;
import static view.commandline.View.digestMenu;

public class CommodityMenu extends Menu {
    private Commodity commodity;
    private String CommentsSortType = "title";

    public CommodityMenu() {
        fxmlFileAddress = "../../fxml/Commodity.fxml";
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
