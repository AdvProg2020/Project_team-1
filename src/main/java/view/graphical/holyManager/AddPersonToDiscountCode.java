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
import model.commodity.DiscountCode;
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
    private static DiscountCode discountCode;

    public static void setDiscountCode(DiscountCode discountCode) {
        AddPersonToDiscountCode.discountCode = discountCode;
    }

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
                CheckBox checkBox = new CheckBox(person.getUsername());
                if (doesPersonHaveDiscountCode(person.getUsername()))
                    checkBox.setSelected(true);
                listView.getItems().add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().add(listView);

    }

    private boolean doesPersonHaveDiscountCode(String username){
            for (PersonalAccount account : accounts) {
                if (account.getUsername().equals(username))
                    return true;
            }

        return false;
    }
}
