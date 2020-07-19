package client.view.graphical.holyManager;

import client.Session;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.dataManager.YaDataManager;
import static client.Main.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class EditDiscountCode extends ViewDiscountCode implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private static DiscountCode discountCode;
    private static Stage stage;

    public static void setStage(Stage stage) {
        EditDiscountCode.stage = stage;
    }

    public TextField code;
    public TextField startDate;
    public TextField finishDate;
    public TextField maximumDiscountPercentage;
    public TextField maximumDiscountPrice;
    public TextField maximumNumberOfUses;
    public Label errorLabel;

    public static void setDiscountCode(DiscountCode discountCode) {
        EditDiscountCode.discountCode = discountCode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<PersonalAccount> personalAccounts = new ArrayList<>();
        for (String username : discountCode.getAccountsUsername()) {
            try {
                personalAccounts.add(YaDataManager.getPersonWithUserName(username));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AddPersonToDiscountCode.setAccounts(personalAccounts);
        AddPersonToDiscountCode.setDiscountCode(discountCode);
        code.setText(discountCode.getCode());
        startDate.setText(discountCode.getStartDate().toString());
        finishDate.setText(discountCode.getFinishDate().toString());
        maximumDiscountPercentage.setText(String.valueOf(discountCode.getDiscountPercentage()));
        maximumDiscountPrice.setText(String.valueOf(discountCode.getMaximumDiscountPrice()));
        maximumNumberOfUses.setText(String.valueOf(discountCode.getMaximumNumberOfUses()));

    }

    public void addAccount(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddPersonToDiscountCode.fxml");
    }

    public void editDiscountCode(ActionEvent actionEvent) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Edit discount code " + discountCode.getCode());
        dataOutputStream.flush();
        dataOutputStream.writeUTF(code.getText() + " " + startDate.getText() + " " + finishDate.getText() + " " +
                maximumDiscountPercentage.getText() + " " + maximumDiscountPrice.getText() + " " + maximumNumberOfUses.getText());
        dataOutputStream.flush();
        dataOutputStream.writeUTF(yaGson.toJson(AddPersonToDiscountCode.getAccounts(),
                new TypeToken<ArrayList<PersonalAccount>>() {
                }.getType()));
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String respond = dataInputStream.readUTF();
        System.out.println(respond);
        if (respond.equalsIgnoreCase("Discount code successfully edited.")){
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Session.getSceneHandler().updateScene(stage);
        }else {
            errorLabel.setVisible(true);
            errorLabel.setText("Invalid entry");
        }

    }
}
