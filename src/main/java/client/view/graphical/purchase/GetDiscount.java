package client.view.graphical.purchase;

import server.controller.customer.CartMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import common.model.commodity.DiscountCode;
import client.view.commandline.View;

import java.io.IOException;

public class GetDiscount {
    private final CartMenu cartMenu = View.cartMenu;
    public Label error;
    public TextField discountField;

    public void back(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void purchase(ActionEvent actionEvent) {
        try {
            DiscountCode discountCode = cartMenu.getDiscountCodeWithCode(discountField.getText());
            cartMenu.purchase(discountCode);
            Parent parent = null;
            Popup popupMenu = new Popup();
            try {
                parent = FXMLLoader.load(getClass().getResource("../../../../fxml/customer/purchase/FinishPurchase.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            popupMenu.getContent().add(parent);
            popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
        } catch (Exception e) {
            error.setText(e.getMessage() + '\n' + "please enter a discount code or enter nothing");
        }
    }
}