package client.view.graphical;

import client.Session;
import common.model.account.SimpleAccount;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import server.data.YaDataManager;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static client.Main.socket;

public class SupportAccount implements Initializable {
    public TextArea textArea;
    public ListView<String> chat1 = new ListView<>();
    public ListView<CheckBox> accounts;
    public static HashMap<SimpleAccount, ArrayList<Message>> chatMessages = new HashMap<SimpleAccount, ArrayList<Message>>();
    public String usernamePersonWhoSupportAccountChatWith;
    public AnchorPane pane;

    public void refresh(ActionEvent actionEvent) {
        setUpPane();
    }

    public void setUpPane() {
        updateAccounts();
        ArrayList<Message> messages = getMessages();
        for (int i = 0; i < chat1.getItems().size(); i++) {
            chat1.getItems().remove(chat1.getItems().get(i));
            i--;
        }
        if (messages != null)
            for (Message message : messages) {
                chat1.getItems().add(message.username + ": " + message.message);
            }
    }

    public ArrayList<Message> getMessages() {
        for (CheckBox item : accounts.getItems()) {
            if (item.isSelected()) {
                usernamePersonWhoSupportAccountChatWith = item.getText();
                for (SimpleAccount simpleAccount : chatMessages.keySet()) {
                    if (item.getText().equals(simpleAccount.getUsername())) {
                        return chatMessages.get(simpleAccount);
                    }
                }
            }
        }
        return null;
    }

    public void updateAccounts() {
        for (SimpleAccount simpleAccount : chatMessages.keySet()) {
            boolean flag = true;
            for (CheckBox item : accounts.getItems()) {
                if (simpleAccount.getUsername().equals(item.getText()))
                    flag = false;
            }
            if (flag)
                accounts.getItems().add(new CheckBox(simpleAccount.getUsername()));
        }
    }

    public void send(ActionEvent actionEvent) throws IOException {
        addMessage("new message from: " + usernamePersonWhoSupportAccountChatWith + " " + textArea.getText(), true);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF(textArea.getText());
        dataOutputStream.flush();
        setUpPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(chat1);
        chat1.setLayoutX(268);
        chat1.setLayoutY(14);
        chat1.setPrefHeight(277);
        chat1.setPrefWidth(425);
        new Thread(() -> {
            while (true) {
                try {


                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    String input = dataInputStream.readUTF();
                    System.out.println(input + "asddadsadasdsa");
                    if (input.startsWith("new chat with:")) {
                        createNewChat(input);
                        System.out.println(input);
                    } else if (input.startsWith("new message from: ")) {
                        addMessage(input, false);
                    }
                    setUpPane();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        setUpPane();
    }

    public void addMessage(String input, boolean isItSupport) {
        String[] splitInput = input.split(" ");
        String message = "";
        for (SimpleAccount account : chatMessages.keySet()) {
            if (account.getUsername().equals(splitInput[3])) {
                for (int i = 4; i < splitInput.length; i++)
                    message += splitInput[i];
                if (isItSupport)
                    chatMessages.get(account).add(new Message(Session.getOnlineAccount().getUsername(), message));
                else chatMessages.get(account).add(new Message(account.getUsername(), message));
            }
        }
    }

    public void createNewChat(String input) throws IOException {
        String[] splitInput = input.split(" ");
        SimpleAccount simpleAccount = YaDataManager.getAccountWithUserName(splitInput[3]);
        chatMessages.put(simpleAccount, new ArrayList<>());
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("Start chat with " + simpleAccount.getUsername());
        dataOutputStream.flush();
    }
}
