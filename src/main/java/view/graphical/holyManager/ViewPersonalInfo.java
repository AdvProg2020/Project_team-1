package view.graphical.holyManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Session;
import org.w3c.dom.Text;
import view.commandline.View;

import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;

public class ViewPersonalInfo extends HolyManager implements Initializable {
    public ListView userInfo;
    public AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> firstName = FXCollections.<String>observableArrayList("First name: " + Session.getOnlineAccount().getFirstName());
        ObservableList<String> lastName = FXCollections.<String>observableArrayList("Last name: " + Session.getOnlineAccount().getLastName());
        ObservableList<String> email = FXCollections.<String>observableArrayList("Email: " + Session.getOnlineAccount().getEmail());
        ObservableList<String> phone = FXCollections.<String>observableArrayList("Phone: " + Session.getOnlineAccount().getPhoneNumber());
        ObservableList<String> userName = FXCollections.<String>observableArrayList("User name: " + Session.getOnlineAccount().getUsername());
        userInfo.getItems().add(firstName);
        userInfo.getItems().add(lastName);
        userInfo.getItems().add(email);
        userInfo.getItems().add(phone);
        userInfo.getItems().add(userName);
    }

    public void editFirstName(ActionEvent actionEvent) {
        pane.getStylesheets().add("fxml/Common.css");
        TextField textField = getTextField("new first name");
        Button change = getButton();
        pane.getChildren().add(change);
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    deleteButtonAndTextField(change,textField);
                    View.viewPersonalInfoMenu.editFirstName(textField.getText() , Session.getOnlineAccount());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private Button getButton() {
        Button change = new Button("Change");
        change.getStyleClass().add("normal-button");
        change.setLayoutX(690);
        change.setLayoutY(250);
        return change;
    }

    private TextField getTextField(String name) {
        TextField textField = new TextField();
        textField.setPromptText(name);
        textField.setLayoutX(400);
        textField.setLayoutY(200);
        pane.getChildren().add(textField);
        return textField;
    }

    public void editLastName(ActionEvent actionEvent) {
        pane.getStylesheets().add("fxml/Common.css");
        TextField textField = getTextField("new last name");
        Button change = getButton();
        pane.getChildren().add(change);
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    deleteButtonAndTextField(change, textField);
                    View.viewPersonalInfoMenu.editLastName(textField.getText() , Session.getOnlineAccount());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void deleteButtonAndTextField(Button change, TextField textField) {
        pane.getChildren().remove(change);
        pane.getChildren().remove(textField);
    }

    public void editEmail(ActionEvent actionEvent) {
        pane.getStylesheets().add("fxml/Common.css");
        TextField textField = getTextField("new email");
        Button change = getButton();
        pane.getChildren().add(change);
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    deleteButtonAndTextField(change, textField);
                    View.viewPersonalInfoMenu.editEmail(textField.getText() , Session.getOnlineAccount());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void editPhone(ActionEvent actionEvent) {
        pane.getStylesheets().add("fxml/Common.css");
        TextField textField = getTextField("new phone");
        Button change = getButton();
        pane.getChildren().add(change);
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    deleteButtonAndTextField(change, textField);
                    View.viewPersonalInfoMenu.editPhoneNumber(textField.getText() , Session.getOnlineAccount());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void editPassword(ActionEvent actionEvent) {
        pane.getStylesheets().add("fxml/Common.css");
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(400);
        passwordField.setLayoutY(200);
        pane.getChildren().add(passwordField);
        Button change = getButton();
        pane.getChildren().add(change);
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    pane.getChildren().remove(change);
                    pane.getChildren().remove(passwordField);
                    View.viewPersonalInfoMenu.editPassword(passwordField.getText() , Session.getOnlineAccount());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

}
