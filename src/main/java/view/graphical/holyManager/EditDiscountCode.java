package view.graphical.holyManager;

import controller.data.YaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Session;
import model.commodity.DiscountCode;
import view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static view.graphical.holyManager.CreateDiscountCode.informationAlert;

public class EditDiscountCode extends ViewDiscountCode implements Initializable {
    private static DiscountCode discountCode;
    private static Stage stage;

    public static void setStage(Stage stage) {
        EditDiscountCode.stage = stage;
    }

    public TextField code;
    public TextField startDate;
    public TextField finishDate;
    public TextField maximumDiscountPercentage;
    public TextField maximumDiscountPrice;
    public TextField maximumNumberOfUses;
    public Label errorLabel;

    public static void setDiscountCode(DiscountCode discountCode) {
        EditDiscountCode.discountCode = discountCode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPersonToDiscountCode.setAccounts(discountCode.getAccounts());
        AddPersonToDiscountCode.setDiscountCode(discountCode);
        code.setText(discountCode.getCode());
        startDate.setText(discountCode.getStartDate().toString());
        finishDate.setText(discountCode.getFinishDate().toString());
        maximumDiscountPercentage.setText(String.valueOf(discountCode.getDiscountPercentage()));
        maximumDiscountPrice.setText(String.valueOf(discountCode.getMaximumDiscountPrice()));
        maximumNumberOfUses.setText(String.valueOf(discountCode.getMaximumNumberOfUses()));
        try {
            View.getDiscountCode.deleteDiscountCode(discountCode);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addAccount(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../fxml/HolyManager/AddPersonToDiscountCode.fxml");
    }

    public void editDiscountCode(ActionEvent actionEvent) {
        if (CreateDiscountCode.createDiscountCode(code, startDate, finishDate, maximumDiscountPercentage, maximumDiscountPrice, maximumNumberOfUses, errorLabel, "Discount code successfully edited.")) {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Session.getSceneHandler().updateScene(stage);
        }

    }
}
