package view.graphical;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginRegister implements Initializable {

    public ChoiceBox<String> accountType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAccountTypeChoiceBox();
    }

    private void initializeAccountTypeChoiceBox() {
        List<String> accountTypesList = new ArrayList<>(Arrays.asList("personal", "reseller", "manager"));
        ObservableList<String> observableList = FXCollections.observableList(accountTypesList);
        accountType.setItems(observableList);
    }
}
