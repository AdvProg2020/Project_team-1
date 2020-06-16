package view.graphical.holyManager;

import controller.data.YaDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import model.Session;
import model.account.ManagerAccount;
import model.account.SimpleAccount;
import model.commodity.Commodity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerUsers extends HolyManager implements Initializable {

    public ListView usersInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (ManagerAccount managerAccount : YaDataManager.getManagers()) {
                ObservableList<String> item = FXCollections.<String>observableArrayList(managerAccount.toString());
                usersInfo.setCellFactory(ComboBoxListCell.forListView(item));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
