package view.graphical;

import controller.customer.CustomerMenu;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.PersonalAccount;
import model.commodity.DiscountCode;
import view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class Customer implements Initializable {
    private final CustomerMenu customerMenu = View.customerMenu;
    public Label usernameLabel;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label emailLabel;
    public Label phoneLabel;
    public Label balanceLabel;
    public ScrollPane discountScrollPane;
    Popup popupMenu = new Popup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        usernameLabel.setText(account.getUsername());
        firstNameLabel.setText(account.getFirstName());
        lastNameLabel.setText(account.getLastName());
        emailLabel.setText(account.getEmail());
        phoneLabel.setText(account.getPhoneNumber());
        balanceLabel.setText(customerMenu.getBalance() + " Rials");
    }

    public void onBackButtonClick(MouseEvent mouseEvent) {
        customerMenu.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCartButtonClick(MouseEvent mouseEvent) {
        customerMenu.goToCartMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onDiscountClick(MouseEvent mouseEvent) throws Exception {
        VBox vBox = new VBox(10);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (DiscountCode myDiscount : customerMenu.getMyDiscounts()) {
            GridPane gridPane = new GridPane();
            gridPane.getStylesheets().add(getClass().getResource("Common.css").toExternalForm());
            gridPane.setPrefHeight(200);
            gridPane.add(new NewLabel("Code:"), 0, 0, 2, 1);
            gridPane.add(new NewLabel(myDiscount.getCode()), 0, 2, 2, 1);
            gridPane.add(new NewLabel("Discount percentage:"), 1, 0);
            gridPane.add(new NewLabel(myDiscount.getMaximumDiscountPrice() + "%"), 1, 1);
            gridPane.add(new NewLabel("Maximum amount:"), 1, 2);
            gridPane.add(new NewLabel(myDiscount.getMaximumDiscountPrice() + " Rials"), 1, 3);
            gridPane.add(new NewLabel("Start date:"), 2, 0);
            gridPane.add(new NewLabel(dateFormat.format(myDiscount.getStartDate())), 2, 1);
            gridPane.add(new NewLabel("Finish date:"), 2, 2);
            gridPane.add(new NewLabel(dateFormat.format(myDiscount.getFinishDate())), 2, 3);
            gridPane.add(new NewLabel("Times used:"), 3, 0);
            gridPane.add(new NewLabel(String.valueOf(account.getNumberOfTimesUsed(myDiscount))), 3, 1);
            gridPane.add(new NewLabel("Times can be used:"), 3, 2);
            gridPane.add(new NewLabel(String.valueOf(myDiscount.getMaximumNumberOfUses())), 3, 3);
            vBox.getChildren().add(gridPane);
        }
        discountScrollPane.setContent(vBox);
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/customer/DiscountCodes.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    private static class NewLabel extends Label {
        public NewLabel(String string) {
            setText(string);
            getStyleClass().add("hint-label");
            GridPane.setHalignment(this, HPos.CENTER);
        }
    }
}
