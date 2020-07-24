package client.view.graphical.customer;

import client.Session;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.SimpleAccount;
import common.model.account.SupportAccount;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import client.controller.share.MenuHandler;
import server.dataManager.YaDataManager;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static client.Main.socket;

public class OnlineSupportAccounts implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public AnchorPane pane;
    private ArrayList<SimpleAccount> simpleAccounts;
    private ListView<CheckBox> listView = new ListView<>();
    private static Stage stage;

    public static void setStage(Stage stage) {
        OnlineSupportAccounts.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(listView);
        try {
            setUpPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpPane() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("get online accounts");
        dataOutputStream.flush();
        simpleAccounts = yaGson.fromJson(dataInputStream.readUTF() , new TypeToken<ArrayList<SimpleAccount>>(){}.getType());
        listView.getItems().removeAll(listView.getItems());
        for (SimpleAccount simpleAccount : simpleAccounts) {
            if (simpleAccount instanceof SupportAccount) {
                listView.getItems().add(new CheckBox(simpleAccount.getInformation()));
                listView.getItems().get(listView.getItems().size() - 1).setId(simpleAccount.getUsername());
            }
        }
        listView.relocate(300, 0);
        listView.setPrefHeight(300);
        listView.setPrefWidth(500);
    }

    public ArrayList<SimpleAccount> getAccounts(String usernames) throws IOException {
        Scanner scanner = new Scanner(usernames);

        ArrayList<SimpleAccount> simpleAccounts = new ArrayList<>();
        System.out.println("Salam" + usernames + "ahsant");
        while (scanner.hasNextLine()) {
            SimpleAccount simpleAccount = YaDataManager.getAccountWithUserName(scanner.nextLine());
            System.out.println(simpleAccount.getUsername());
            simpleAccounts.add(simpleAccount);
        }
        return simpleAccounts;
    }

    public void close(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void chat(ActionEvent actionEvent) throws IOException {
        String username = "";
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected())
                username = item.getId();
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("Chat between: " + Session.getOnlineAccount().getUsername() + " " + username);
        dataOutputStream.flush();
        Chat.setUserSupportAccount(username);
        MenuHandler.getInstance().setCurrentMenu(View.chat);
        Session.getSceneHandler().updateScene(stage);
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
