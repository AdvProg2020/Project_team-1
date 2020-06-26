package view.graphical.purchase;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

import java.io.IOException;

public class GetInfo {
    public Label error;
    public TextField phone;
    public TextField address;
    public TextField postalCode;

    public void back(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void next(ActionEvent actionEvent) {
        String phoneNumber = phone.getText();
        String finalAddress = address.getText();
        String postal = postalCode.getText();
        if (!phoneNumber.matches("0\\d{10}")) {
            error.setText("please enter a valid phone number");
            return;
        }
        if (!postal.matches("\\d{10}")) {
            error.setText("please enter a valid postal code");
            return;
        }
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/customer/purchase/Discount.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
    }
}
