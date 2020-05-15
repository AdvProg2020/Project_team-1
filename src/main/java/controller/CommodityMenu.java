package controller;

import controller.commodity.CommentsMenu;
import controller.commodity.DigestMenu;
import model.Commodity;
import model.DataManager;

public class CommodityMenu extends Menu {
    private Commodity commodity;

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Commodity getCommodity() {
        return this.commodity;
    }

    public void goToDigestMenu(DigestMenu digestMenu) {
        HandleMenu.setMenu(digestMenu);
        digestMenu.setPreviousMenu(this);
    }

    public void goToCommentsMenu(CommentsMenu commentsMenu) {
        HandleMenu.setMenu(commentsMenu);
        commentsMenu.setPreviousMenu(this);
    }

    public Commodity compare(int id) throws Exception {
        Commodity comparingCommodity = DataManager.getCommodityById(id);
        if (!commodity.getCategory().equals(comparingCommodity.getCategory())) {
            throw new Exception("to compare two products, they have to in the same category");
        }
        return comparingCommodity;
    }
}
