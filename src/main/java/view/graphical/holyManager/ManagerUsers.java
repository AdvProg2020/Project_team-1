package view.graphical.holyManager;

import controller.data.YaDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;
import model.commodity.Commodity;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.commandline.View.manageUsersMenu;

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

        newPopup(actionEvent, "../../../fxml/HolyManager/DeleteAccount.fxml");

    }


    public void addManager(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../fxml/HolyManager/AddManager.fxml");
    }
}
