package client.view.graphical.holyManager;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.*;
import server.dataManager.YaDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import static client.Main.socket;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ManagerUsers extends HolyManager implements Initializable {

    public ListView usersInfo;
    public Button delete;
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> item = FXCollections.<String>observableArrayList();
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("get online accounts");
            dataOutputStream.flush();
            ArrayList<SimpleAccount> simpleAccounts = yaGson.fromJson(dataInputStream.readUTF()
                    , new TypeToken<ArrayList<SimpleAccount>>(){}.getType());
            dataOutputStream.writeUTF("Send all accounts");
            dataOutputStream.flush();
            ArrayList<ManagerAccount> managerAccounts =  yaGson.fromJson(dataInputStream.readUTF()
                    , new TypeToken<ArrayList<ManagerAccount>>(){}.getType());
            ArrayList<PersonalAccount> personalAccounts =  yaGson.fromJson(dataInputStream.readUTF()
                    , new TypeToken<ArrayList<PersonalAccount>>(){}.getType());
            ArrayList<BusinessAccount> businessAccounts =  yaGson.fromJson(dataInputStream.readUTF()
                    , new TypeToken<ArrayList<BusinessAccount>>(){}.getType());
            for (ManagerAccount managerAccount : managerAccounts) {
                if (isAccountOnline(managerAccount, simpleAccounts))
                    item.add(managerAccount.getInformation() + "\n online");
                else item.add(managerAccount.getInformation() + "\n offline");
            }
            for (BusinessAccount business : businessAccounts) {
                if (isAccountOnline(business, simpleAccounts))
                    item.add(business.getInformation() +"\n online" );
                else  item.add(business.getInformation() +"\n offline" );
            }
            for (PersonalAccount person : personalAccounts) {
                if (isAccountOnline(person, simpleAccounts))
                    item.add(person.getInformation() + "\n online");
                else item.add(person.getInformation() + "\n offline");
            }
            for (SupportAccount support : YaDataManager.getSupports()) {
                if (isAccountOnline(support, simpleAccounts))
                    item.add(support.getInformation() + "\n online");
                else  item.add(support.getInformation() + "\n offline");

            }
            usersInfo.setItems(item);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isAccountOnline(SimpleAccount simpleAccount, ArrayList<SimpleAccount> simpleAccounts) {
        for (SimpleAccount account : simpleAccounts) {
            if (account.getUsername().equalsIgnoreCase(simpleAccount.getUsername()))
                return true;
        }
        return false;
    }


    public void deleteAccount(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../../fxml/HolyManager/DeleteAccount.fxml");
    }


    public void addManager(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddManager.fxml");
    }

    public void addSupportAccount(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddSupportAccount.fxml");
    }


    public void manageAllProducts(ActionEvent actionEvent) {
    }
}
