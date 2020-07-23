package client.view.graphical.commodity;

import client.Session;
import client.controller.commodity.AuctionMenu;
import client.view.commandline.View;
import client.view.graphical.MainMenu;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.commodity.Auction;
import common.model.commodity.Commodity;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.controller.share.MenuHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class AuctionPage implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private final AuctionMenu auctionMenu = View.auctionMenu;
    public Label brand;
    public Label originalPrice;
    public Label topBidder;
    public Label topBid;
    public TextField offerTF;
    public Button submitButton;
    public Label error;
    public Label name;
    private Auction auction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUpPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpPane() throws IOException {
        auction = auctionMenu.getAuction();
        outputStream.writeUTF("send commodity with id " + auction.getCommodityId());
        outputStream.flush();
        Commodity commodity = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Commodity>() {
        }.getType());
        brand.setText("Brand: " + commodity.getBrand());
        originalPrice.setText("Original Price: " + commodity.getPrice());
        name.setText("Name: " + commodity.getName());
        if (auction.getTopBidder() == null) {
            topBidder.setText("No Bid Yet");
            topBid.setText("0 Rial");
        } else {
            topBid.setText(auction.getTopBid() + " Rials");
            topBidder.setText(auction.getTopBidder());
        }
        if (Session.getOnlineAccount() == null || Session.getOnlineAccount().getAccountType().equals("personal")) {
            submitButton.setDisable(true);
        }
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        if (offerTF.getText() == null || !offerTF.getText().matches("\\d+")) {
            error.setText("Enter a number in the box");
            return;
        }
        int bid = Integer.parseInt(offerTF.getText());
        outputStream.writeUTF("update auction " + auction.getCommodityId());
        outputStream.flush();
        auction = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Auction>() {
        }.getType());
        outputStream.writeUTF("send blocked money");
        outputStream.flush();
        int blocked = inputStream.read();
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        if (bid < auction.getTopBid() || (double) (bid - blocked) > personalAccount.getCredit()) {
            error.setText("Your offer is not accepted");
            return;
        }
        auction.newBid(Session.getOnlineAccount().getUsername(), bid);
        auctionMenu.setAuction(auction);
        outputStream.writeUTF("new bid " + bid + " for " + auction.getCommodityId());
        outputStream.flush();
        setUpPane();
    }

    public void back(ActionEvent actionEvent) {
        auctionMenu.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void logout(ActionEvent actionEvent) {
        try {
            MenuHandler.getInstance().getCurrentMenu().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void products(ActionEvent actionEvent) {
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void userPanel(ActionEvent actionEvent) {
        MainMenu.goToUserPanel(actionEvent);
    }

    public void mainMenu(ActionEvent actionEvent) {
        View.mainMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }
}
