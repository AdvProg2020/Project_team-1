package view.graphical.holyManager;

import controller.data.YaDataManager;
import controller.share.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Session;
import model.account.PersonalAccount;
import model.account.SimpleAccount;
import view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPersonToDiscountCode implements Initializable {
    public static void setAccounts(ArrayList<PersonalAccount> accounts) {
        AddPersonToDiscountCode.accounts = accounts;
    }

    private static ArrayList<PersonalAccount> accounts;
    public ListView<CheckBox> listView = new ListView<CheckBox>();
    public AnchorPane pane;

    public static ArrayList<PersonalAccount> getAccounts() {
        return accounts;
    }

    public void add(ActionEvent actionEvent) throws Exception {
        accounts = new ArrayList<>();
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()){
                PersonalAccount personalAccount =  YaDataManager.getPersonWithUserName(item.getText());
                accounts.add(personalAccount);
            }
        }
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (PersonalAccount person : YaDataManager.getPersons()) {
                    listView.getItems().add(new CheckBox(person.getUsername()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().add(listView);

    }
}
