package client.view.graphical.purchase;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import client.Session;
import common.model.account.PersonalAccount;
import common.model.log.BuyLog;
import client.view.commandline.View;

import java.net.URL;
import java.util.ResourceBundle;

public class FinishPurchase implements Initializable {
    public Label username;
    public Label payedMoney;
    public Label discountMoney;
    public Label discount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Session.getOnlineAccount().getUsername());
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        BuyLog buyLog = View.cartMenu.getBuyLog();
        payedMoney.setText(buyLog.getPayedMoney() + " Rials");
        discountMoney.setText(buyLog.getDeductedMoney() + " Rials");
        if (buyLog.getDiscountByCode() != null) {
            discountMoney.setText(buyLog.getDiscountByCode().getCode());
        } else {
            discountMoney.setText("No discount");
        }
    }

    public void close(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
