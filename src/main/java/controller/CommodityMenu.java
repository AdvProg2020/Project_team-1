package controller;

import controller.commodity.DigestMenu;
import model.Commodity;

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
}
