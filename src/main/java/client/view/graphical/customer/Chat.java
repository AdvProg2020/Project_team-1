package client.view.graphical.customer;

import client.Session;
import client.view.graphical.Message;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import server.Main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Main.socket;

public class Chat implements Initializable {
    public ListView<String> messages1 = new ListView<>();
    public TextArea textArea;
    public ArrayList<Message> messagesArray = new ArrayList<>();
    public AnchorPane pane;

    public static void setUserSupportAccount(String userSupportAccount) {
        Chat.userSupportAccount = userSupportAccount;
    }

    private static String userSupportAccount;

    public void close(ActionEvent actionEvent) {

    }

    public void addMessage(String input , String username) {
        messagesArray.add(new Message( username, input));
        setUpPane();
    }

    public void setUpPane(){
        for (int i = 0 ; i < messages1.getItems().size() ; i++) {
            messages1.getItems().remove(messages1.getItems().get(i));
            i--;
        }
        for (Message message : messagesArray) {
            messages1.getItems().add(message.username + ": " +  message.message);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(messages1);
        messages1.setLayoutX(14.0);
        messages1.setLayoutY(6.0);
        messages1.setPrefHeight(256);
        messages1.setPrefWidth(573);
        new Thread(() -> {
            while (true) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    String input = dataInputStream.readUTF();
                    addMessage(input , userSupportAccount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        setUpPane();
    }

    public void send(ActionEvent actionEvent) throws IOException {
        addMessage(textArea.getText() , Session.getOnlineAccount().getUsername());
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("new message from: " + Session.getOnlineAccount().getUsername() + " "  + textArea.getText());
        dataOutputStream.flush();
        setUpPane();
    }
}
