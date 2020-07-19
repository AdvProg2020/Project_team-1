package client.view.graphical.holyManager;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.dataManager.YaDataManager;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Main.socket;


public class CreateDiscountCode extends HolyManager implements Initializable {
    public TextField code;
    public TextField startDate;
    public TextField finishDate;
    public TextField maximumDiscountPercentage;
    public TextField maximumDiscountPrice;
    public TextField maximumNumberOfUses;
    public Label errorLabel;
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();


    public void addAccount(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddPersonToDiscountCode.fxml");
    }

    public void createDiscountCodeNC(ActionEvent actionEvent) {
        createDiscountCode(code, startDate, finishDate, maximumDiscountPercentage, maximumDiscountPrice, maximumNumberOfUses, errorLabel, "Discount code successfully created");
        code.setText("");
        startDate.setText("");
        finishDate.setText("");
        maximumDiscountPercentage.setText("");
        maximumDiscountPrice.setText("");
        maximumNumberOfUses.setText("");
    }

    static boolean createDiscountCode(TextField code, TextField startDate, TextField finishDate, TextField maximumDiscountPercentage, TextField maximumDiscountPrice, TextField maximumNumberOfUses, Label errorLabel, String text) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF("new discount code");
            dataOutputStream.flush();
            dataOutputStream.writeUTF(code.getText() + " " + startDate.getText() + " " + finishDate.getText() + " " +
                    maximumDiscountPercentage.getText() + " " + maximumDiscountPrice.getText() + " " + maximumNumberOfUses.getText());
            dataOutputStream.flush();
            dataOutputStream.writeUTF(yaGson.toJson(AddPersonToDiscountCode.getAccounts(),
                    new TypeToken<ArrayList<PersonalAccount>>() {
                    }.getType()));
            dataOutputStream.flush();
            String respond = dataInputStream.readUTF();
            if (respond.equals("Discount code successfully created")) {
                AddPersonToDiscountCode.setAccounts(new ArrayList<>());
                informationAlert(text);
                return true;
            } else throw new Exception(respond);
        } catch (Exception exception) {
            errorLabel.setText(exception.getMessage());
            errorLabel.setVisible(true);
            return false;
        }
    }

    static void informationAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPersonToDiscountCode.setAccounts(new ArrayList<>());
        System.out.println(AddPersonToDiscountCode.getAccounts());
    }
}
