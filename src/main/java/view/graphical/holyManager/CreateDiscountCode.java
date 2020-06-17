package view.graphical.holyManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import view.commandline.View;

import java.io.IOException;

public class CreateDiscountCode extends HolyManager{
    public TextField code;
    public TextField startDate;
    public TextField finishDate;
    public TextField maximumDiscountPercentage;
    public TextField maximumDiscountPrice;
    public TextField maximumNumberOfUses;
    public Label errorLabel;

    public void addAccount(ActionEvent actionEvent) {
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/HolyManager/AddPersonToDiscountCode.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show((((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void createDiscountCodeNC(ActionEvent actionEvent) {
        try {
            View.createDiscountCode.createDiscountCodeNC(code.getText(), startDate.getText() , finishDate.getText() ,
                    maximumDiscountPercentage.getText(),maximumDiscountPrice.getText() , maximumNumberOfUses.getText());
        } catch (Exception exception) {
            errorLabel.setText(exception.getMessage());
            errorLabel.setVisible(true);
        }
    }
}
