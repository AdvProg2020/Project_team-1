package view.graphical.holyManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Session;
import org.w3c.dom.Text;
import view.commandline.View;

import java.awt.*;
import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;

public class ViewPersonalInfo extends HolyManager implements Initializable {
    public ListView userInfo;
    public AnchorPane pane;
    public Label label;
    private ListView<String> personalInfo = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personalInfo.getItems().add("First name: " + Session.getOnlineAccount().getFirstName());
        personalInfo.getItems().add("Last name: " + Session.getOnlineAccount().getLastName());
        personalInfo.getItems().add("Email: " + Session.getOnlineAccount().getEmail());
        personalInfo.getItems().add("Phone: " + Session.getOnlineAccount().getPhoneNumber());
        personalInfo.getItems().add("User name: " + Session.getOnlineAccount().getUsername());
        userInfo.getItems().add(personalInfo);
    }

    private void updatePane() {
        userInfo.getItems().remove(personalInfo);
        personalInfo= new ListView<>();
        personalInfo.getItems().add("First name: " + Session.getOnlineAccount().getFirstName());
        personalInfo.getItems().add("Last name: " + Session.getOnlineAccount().getLastName());
        personalInfo.getItems().add("Email: " + Session.getOnlineAccount().getEmail());
        personalInfo.getItems().add("Phone: " + Session.getOnlineAccount().getPhoneNumber());
        personalInfo.getItems().add("User name: " + Session.getOnlineAccount().getUsername());
        userInfo.getItems().add(personalInfo);
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
                    deleteButtonAndTextField(change, textField);
                    View.viewPersonalInfoMenu.editFirstName(textField.getText(), Session.getOnlineAccount());
                    setLabel(Color.GREEN , "First name successfully changed");
                    updatePane();
                } catch (Exception exception) {
                    setLabel(Color.RED , exception.getMessage());
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
                    View.viewPersonalInfoMenu.editLastName(textField.getText(), Session.getOnlineAccount());
                    setLabel(Color.GREEN , "Last name successfully changed");
                    updatePane();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    setLabel(Color.RED , exception.getMessage());
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
                    View.viewPersonalInfoMenu.editEmail(textField.getText(), Session.getOnlineAccount());
                    setLabel(Color.GREEN , "Email successfully changed");
                    updatePane();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    setLabel(Color.RED , exception.getMessage());
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
                    View.viewPersonalInfoMenu.editPhoneNumber(textField.getText(), Session.getOnlineAccount());
                    setLabel(Color.GREEN , "Phone successfully changed");
                    updatePane();
                } catch (Exception exception) {

                    setLabel(Color.RED , exception.getMessage());
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
                    View.viewPersonalInfoMenu.editPassword(passwordField.getText(), Session.getOnlineAccount());
                    setLabel(Color.GREEN , "Password successfully changed");
                } catch (Exception exception) {
                    setLabel(Color.RED , exception.getMessage());
                }
            }
        });
    }

    private void setLabel(Color color, String text){
        label.setVisible(true);
        label.setTextFill(color);
        label.setText(text);
    }
}
