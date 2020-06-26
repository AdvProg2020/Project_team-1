package view.graphical.customer;

import controller.share.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Session;
import model.account.PersonalAccount;
import view.commandline.View;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewInfo implements Initializable {
    public AnchorPane pane;
    public ListView userInfo;
    public Label label;

    ObservableList<String> firstName;
    ObservableList<String> lastName;
    ObservableList<String> email;
    ObservableList<String> phone;
    ObservableList<String> userName;

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
                    removeItems();
                    addItems();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
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
                    setLabel(Color.GREEN, "Last name successfully changed");
                    updatePane();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    setLabel(Color.RED, exception.getMessage());
                }
            }
        });
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
                    setLabel(Color.GREEN, "Email successfully changed");
                    updatePane();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    setLabel(Color.RED, exception.getMessage());
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
                    setLabel(Color.GREEN, "Phone successfully changed");
                    updatePane();
                } catch (Exception exception) {

                    setLabel(Color.RED, exception.getMessage());
                }
            }
        });
    }

    private void updatePane() {
        removeItems();
        addItems();
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
                    setLabel(Color.GREEN, "Password successfully changed");
                } catch (Exception exception) {
                    setLabel(Color.RED, exception.getMessage());
                }
            }
        });
    }

    private void setLabel(Color color, String text) {
        label.setVisible(true);
        label.setTextFill(color);
        label.setText(text);
    }

    public void back(ActionEvent actionEvent) {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addItems();
    }

    private void addItems() {
        firstName = FXCollections.observableArrayList("First name: " +
                Session.getOnlineAccount().getFirstName());
        lastName = FXCollections.observableArrayList("Last name: " +
                Session.getOnlineAccount().getLastName());
        email = FXCollections.observableArrayList("Email: " +
                Session.getOnlineAccount().getEmail());
        phone = FXCollections.observableArrayList("Phone: " +
                Session.getOnlineAccount().getPhoneNumber());
        userName = FXCollections.observableArrayList("User name: " +
                Session.getOnlineAccount().getUsername());
        userInfo.getItems().addAll(userName, firstName, lastName, email, phone);
    }

    private void removeItems() {
        userInfo.getItems().removeAll(userName, firstName, lastName, email, phone);
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


    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
