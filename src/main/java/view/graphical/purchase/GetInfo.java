package view.graphical.purchase;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Session;

public class GetInfo {
    public Label error;
    public TextField phone;
    public TextField address;
    public TextField postalCode;

    public void back(ActionEvent actionEvent) {
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
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
        //go to discount page
    }
}
