package client.view.graphical.holyManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import client.Session;
import client.view.commandline.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static client.Main.socket;
public class ViewPersonalInfo extends HolyManager implements Initializable {
    public ListView userInfo;
    public AnchorPane pane;
    public Label label;
    private ListView<String> personalInfo = new ListView<>();
    private DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
    private DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

    public ViewPersonalInfo() throws IOException {
    }

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
                    dataOutputStream.writeUTF("Edit first "+
                            Session.getOnlineAccount().getUsername() + " " + textField.getText());
                    dataOutputStream.flush();
                    String respond = dataInputStream.readUTF();
                    if (respond.equals("successfully changed")) {
                        setLabel(Color.GREEN, respond);
                        Session.getOnlineAccount().changeFirstName(textField.getText());
                    }
                    else setLabel(Color.RED , respond);
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
                    dataOutputStream.writeUTF("Edit last name " +
                            Session.getOnlineAccount().getUsername() + " " +textField.getText());
                    dataOutputStream.flush();
                    String respond = dataInputStream.readUTF();
                    if (respond.equals("successfully changed")) {
                        setLabel(Color.GREEN, respond);
                        Session.getOnlineAccount().changeLastName(textField.getText());
                    } else setLabel(Color.RED , respond);updatePane();
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
                    dataOutputStream.writeUTF("Edit email " +
                            Session.getOnlineAccount().getUsername() + " "+ textField.getText());
                    dataOutputStream.flush();
                    String respond = dataInputStream.readUTF();
                    if (respond.equals("successfully changed")) {
                        setLabel(Color.GREEN, respond);
                        Session.getOnlineAccount().changeEmail(textField.getText());
                    }else setLabel(Color.RED , respond);
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
                    dataOutputStream.writeUTF("Edit phone "+
                            Session.getOnlineAccount().getUsername() + " " + textField.getText());
                    dataOutputStream.flush();
                    String respond = dataInputStream.readUTF();
                    if (respond.equals("successfully changed")) {
                        setLabel(Color.GREEN, respond);
                        Session.getOnlineAccount().changeEmail(textField.getText());
                    } else setLabel(Color.RED , respond);
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
                    dataOutputStream.writeUTF("Edit password "+
                            Session.getOnlineAccount().getUsername() + " " + passwordField.getText());
                    dataOutputStream.flush();
                    String respond = dataInputStream.readUTF();
                    if (respond.equals("successfully changed")) {
                        setLabel(Color.GREEN, respond);
                        Session.getOnlineAccount().changePassword(passwordField.getText());
                    } else setLabel(Color.RED , respond);
                } catch (Exception exception) {

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
