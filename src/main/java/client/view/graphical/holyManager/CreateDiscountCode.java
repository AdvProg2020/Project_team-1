package client.view.graphical.holyManager;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import client.view.commandline.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateDiscountCode extends HolyManager implements Initializable {
    public TextField code;
    public TextField startDate;
    public TextField finishDate;
    public TextField maximumDiscountPercentage;
    public TextField maximumDiscountPrice;
    public TextField maximumNumberOfUses;
    public Label errorLabel;

    public void addAccount(ActionEvent actionEvent) {
            newPopup(actionEvent ,"../../../../fxml/HolyManager/AddPersonToDiscountCode.fxml");
    }

    public void createDiscountCodeNC(ActionEvent actionEvent) {
        createDiscountCode(code, startDate, finishDate, maximumDiscountPercentage, maximumDiscountPrice, maximumNumberOfUses, errorLabel ,"Discount code successfully created");
        code.setText("");
        startDate.setText("");
        finishDate.setText("");
        maximumDiscountPercentage.setText("");
        maximumDiscountPrice.setText("");
        maximumNumberOfUses.setText("");
    }

    static boolean createDiscountCode(TextField code, TextField startDate, TextField finishDate, TextField maximumDiscountPercentage, TextField maximumDiscountPrice, TextField maximumNumberOfUses, Label errorLabel , String text) {
        try {
            View.createDiscountCode.createDiscountCodeNC(code.getText(), startDate.getText() , finishDate.getText() ,
                    maximumDiscountPercentage.getText(), maximumDiscountPrice.getText() , maximumNumberOfUses.getText());
            AddPersonToDiscountCode.setAccounts(new ArrayList<>());
            informationAlert(text);
            return true;
        } catch (Exception exception) {
            errorLabel.setText(exception.getMessage());
            errorLabel.setVisible(true);
            return false;
        }
    }

    static void informationAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPersonToDiscountCode.setAccounts(new ArrayList<>());
    }
}
