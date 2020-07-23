package client.controller.commodity;

import common.model.commodity.Auction;
import common.model.commodity.Commodity;
import server.controller.share.Menu;

public class AuctionMenu extends Menu {
    private static Auction auction;

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        AuctionMenu.auction = auction;
    }

    public AuctionMenu() {
        fxmlFileAddress = "../../../fxml/AuctionPage.fxml";
    }
}
