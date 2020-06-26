package view.graphical.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Session;
import model.account.PersonalAccount;
import view.commandline.View;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewInfo implements Initializable {
    public AnchorPane pane;
    public ListView userInfo;

    public void editFirstName(ActionEvent actionEvent) {
        TextField textField = getTextField("new first name");
        Button change = getButton();
        pane.getChildren().add(change);
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    deleteButtonAndTextField(change, textField);
                    View.viewPersonalInfoMenu.editFirstName(textField.getText(), Session.getOnlineAccount());

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void editLastName(ActionEvent actionEvent) {
    }

    public void editEmail(ActionEvent actionEvent) {
    }

    public void editPhone(ActionEvent actionEvent) {
    }

    public void editPassword(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> balance = null;
        ObservableList<String> firstName = FXCollections.observableArrayList("First name: " +
                Session.getOnlineAccount().getFirstName());
        ObservableList<String> lastName = FXCollections.observableArrayList("Last name: " +
                Session.getOnlineAccount().getLastName());
        ObservableList<String> email = FXCollections.observableArrayList("Email: " +
                Session.getOnlineAccount().getEmail());
        ObservableList<String> phone = FXCollections.observableArrayList("Phone: " +
                Session.getOnlineAccount().getPhoneNumber());
        ObservableList<String> userName = FXCollections.observableArrayList("User name: " +
                Session.getOnlineAccount().getUsername());
        if (Session.getOnlineAccount() instanceof PersonalAccount) {
            balance = FXCollections.observableArrayList("Balance: " +
                    ((PersonalAccount) Session.getOnlineAccount()).getCredit());
        }
        userInfo.getItems().addAll(userName, firstName, lastName, email, phone, balance);
    }

    private TextField getTextField(String name) {
        TextField textField = new TextField();
        textField.setPromptText(name);
        textField.setLayoutX(10);
        textField.setLayoutY(200);
        pane.getChildren().add(textField);
        return textField;
    }

    private Button getButton() {
        Button change = new Button("Change");
        change.getStyleClass().add("normal-button");
        change.setLayoutX(250);
        change.setLayoutY(250);
        return change;
    }

    private void deleteButtonAndTextField(Button change, TextField textField) {
        pane.getChildren().remove(change);
        pane.getChildren().remove(textField);
    }

    public void editBalance(ActionEvent actionEvent) {
    }
}
