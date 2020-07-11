package client.view.graphical;

import common.model.account.SimpleAccount;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import server.data.YaDataManager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import static client.Main.socket;

public class SupportAccount implements Initializable {
    public TextArea textArea;
    public ListView chat;
    public ListView<CheckBox> accounts;
    public static HashMap<SimpleAccount , ArrayList<Message>> chatMessages = new HashMap<SimpleAccount , ArrayList<Message>>();

    public void refresh(ActionEvent actionEvent) {
        setUpPane();
    }

    public void setUpPane(){
        updateAccounts();
        ArrayList<Message> messages = getMessages();
        for (Message message : messages) {
            chat.getItems().add(message.username + ": " + message.message);
        }
    }

    public ArrayList<Message> getMessages() {
        for (CheckBox item : accounts.getItems()) {
            if (item.isSelected()){
                ArrayList<Message> messages;
                for (SimpleAccount simpleAccount : chatMessages.keySet()) {
                    if (item.getText().equals(simpleAccount.getUsername())){
                        return chatMessages.get(simpleAccount);
                    }
                }
            }
        }
        return null;
    }

    public void updateAccounts() {
        for (SimpleAccount simpleAccount : chatMessages.keySet()) {
            for (CheckBox item : accounts.getItems()) {
                if (simpleAccount.getUsername().equals(item.getText()))
                    break;
            }
            accounts.getItems().add(new CheckBox(simpleAccount.getUsername()));
        }
    }

    public void send(ActionEvent actionEvent) {
        setUpPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            while (true) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    String input = dataInputStream.readUTF();
                    if (input.startsWith("new chat with:")) {
                        createNewChat(input);
                    } else if (input.startsWith("new message from: ")) {
                        addMessage(input);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        setUpPane();
    }

    public void addMessage(String input){
        String[] splitInput = input.split(" ");
        String message = "";
        for (SimpleAccount account : chatMessages.keySet()) {
            if (account.getUsername().equals(splitInput[3])){
                for (int i = 4 ; i < splitInput.length - 1 ; i++)
                    message += splitInput[i];
                chatMessages.get(account).add(new Message(account.getUsername() , message));
            }
        }
    }

    public void createNewChat(String input) throws IOException {
        String[] splitInput = input.split(" ");
        SimpleAccount simpleAccount = YaDataManager.getAccountWithUserName(splitInput[3]);
        chatMessages.put(simpleAccount , new ArrayList<>());
    }
}
