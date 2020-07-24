package client.controller.commodity;

import common.model.commodity.Auction;
import client.controller.share.Menu;

public class AuctionMenu extends Menu {
    private Auction auction;

    public AuctionMenu() {
        fxmlFileAddress = "../../../fxml/AuctionPage.fxml";
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
