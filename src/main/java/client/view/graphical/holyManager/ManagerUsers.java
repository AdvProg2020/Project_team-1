package client.view.graphical.holyManager;

import server.data.YaDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerUsers extends HolyManager implements Initializable {

    public ListView usersInfo;
    public Button delete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> item = FXCollections.<String>observableArrayList();
        try {
            for (ManagerAccount managerAccount : YaDataManager.getManagers()) {
                item.add(managerAccount.getInformation());
            }
            for (BusinessAccount business : YaDataManager.getBusinesses()) {
               item.add(business.getInformation());
            }
            for (PersonalAccount person : YaDataManager.getPersons()) {
                item.add(person.getInformation());
            }
            usersInfo.setItems(item);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteAccount(ActionEvent actionEvent) {

        newPopup(actionEvent, "../../../../fxml/HolyManager/DeleteAccount.fxml");

    }


    public void addManager(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddManager.fxml");
    }
}
