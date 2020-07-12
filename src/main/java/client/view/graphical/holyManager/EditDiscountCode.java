package client.view.graphical.holyManager;

import client.Session;
import client.view.commandline.View;
import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.data.YaDataManager;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
        ArrayList<PersonalAccount> personalAccounts = new ArrayList<>();
        for (String username : discountCode.getAccountsUsername()) {
            try {
                personalAccounts.add(YaDataManager.getPersonWithUserName(username));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AddPersonToDiscountCode.setAccounts(personalAccounts);
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
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddPersonToDiscountCode.fxml");
    }

    public void editDiscountCode(ActionEvent actionEvent) {
        if (CreateDiscountCode.createDiscountCode(code, startDate, finishDate, maximumDiscountPercentage, maximumDiscountPrice, maximumNumberOfUses, errorLabel, "Discount code successfully edited.")) {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Session.getSceneHandler().updateScene(stage);
        }
        try {
            View.getDiscountCode.changeCode(code.getText(),discountCode);
            View.getDiscountCode.changeDiscountPercentage(Integer.parseInt(maximumDiscountPercentage.getText()), discountCode);
            View.getDiscountCode.changeMaximumNumberOfUses(Integer.parseInt(maximumNumberOfUses.getText()),discountCode);
            View.getDiscountCode.changeDiscountPercentage(Integer.parseInt(maximumDiscountPercentage.getText()),discountCode);
            View.getDiscountCode.changeDiscountPercentage(Integer.parseInt(maximumDiscountPercentage.getText()),discountCode);
            SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
            Date startDateAsli =  format.parse(startDate.getText());
            Date finishDateASli = format.parse(finishDate.getText());
            View.getDiscountCode.changeFinishDate(finishDateASli , discountCode);
            View.getDiscountCode.changeStartDate(startDateAsli , discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
