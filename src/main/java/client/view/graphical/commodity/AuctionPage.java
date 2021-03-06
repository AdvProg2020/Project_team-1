package client.view.graphical.commodity;

import client.Session;
import client.controller.commodity.AuctionMenu;
import client.controller.share.MenuHandler;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.Main;
import server.dataManager.YaDataManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    public ImageView imageView;
    public Button logoutButton;
    public ListView<String> messages1;
    public TextField textArea;
    private Auction auction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUpPane();
            Date date = auction.getDeadline();
            new Thread(() -> {
                Timer timer = new Timer();
                timer.schedule(new AuctionPage.AuctionCloser(submitButton), date);
            }).start();
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
        if (Session.getOnlineAccount() == null || !Session.getOnlineAccount().getAccountType().equals("personal")) {
            submitButton.setDisable(true);
        }
        if (Session.getOnlineAccount() == null) {
            logoutButton.setDisable(true);
        }
        commodity.setImagePath("tmp\\" + commodity.getCommodityId() + ".png");
        FileInputStream inputStream = new FileInputStream(commodity.getImagePath());
        Image image = new Image(inputStream);
        imageView.setImage(image);
        imageView.setFitWidth(400);
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
        if (auction.getTopBidder().equals(personalAccount.getUsername())) {
            blocked -= auction.getTopBid();
        }
        if (bid < auction.getTopBid() || ((double) (bid - blocked)) > personalAccount.getCredit()) {
            error.setText("Your offer is not valid");
        } else {
            auction.newBid(Session.getOnlineAccount().getUsername(), bid);
            auctionMenu.setAuction(auction);
            outputStream.writeUTF("new bid " + bid + " for " + auction.getCommodityId());
            outputStream.flush();
            error.setText("Your bid was successful");
            setUpPane();
        }
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

    public void userPanel(ActionEvent actionEvent) throws IOException {
        MainMenu.goToUserPanel(actionEvent);
    }

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        View.mainMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void send(ActionEvent actionEvent) throws IOException {
    }

    private static class AuctionCloser extends TimerTask {
        private Button button;

        public AuctionCloser(Button button) {
            this.button = button;
        }

        @Override
        public void run() {
            button.setDisable(true);
        }
    }
}
