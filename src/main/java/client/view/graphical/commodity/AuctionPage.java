package client.view.graphical.commodity;

import client.Session;
import client.controller.commodity.AuctionMenu;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.commodity.Auction;
import common.model.commodity.Commodity;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static client.Main.outputStream;
import static client.Main.inputStream;

public class AuctionPage implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public Label brand;
    public Label originalPrice;
    public Label topBidder;
    public Label topBid;
    public TextField offerTF;
    public Button submitButton;
    public Label error;
    private Auction auction;
    public Label name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        auction = AuctionMenu.getAuction();
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        if (offerTF.getText() == null || !offerTF.getText().matches("\\d+")) {
            error.setText("Enter a number in the box");
            return;
        }
        int bid = Integer.parseInt(offerTF.getText());
        outputStream.writeUTF("update auction " + auction.getCommodityId());
        outputStream.flush();
        auction = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Auction>(){}.getType());
        outputStream.writeUTF("send blocked money");
        outputStream.flush();
        int blocked = Integer.parseInt(inputStream.readUTF());
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        if (bid < auction.getTopBid() || (double) (bid - blocked) > personalAccount.getCredit()) {
            error.setText("Your offer is not accepted");
            return;
        }

    }
}
