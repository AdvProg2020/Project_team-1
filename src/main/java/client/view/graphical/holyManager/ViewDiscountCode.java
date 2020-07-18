package client.view.graphical.holyManager;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import server.dataManager.YaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import common.model.commodity.DiscountCode;
import client.view.commandline.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static client.Main.socket;

public class ViewDiscountCode extends HolyManager implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public AnchorPane pane;
    public Label errorLabel;
    private ListView<CheckBox> listView = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(listView);
        try {
            setUpPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setUpPane() throws IOException {
        listView.getItems().removeAll(listView.getItems());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Discount codes");
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        ArrayList<DiscountCode> discountCodes = yaGson.fromJson(dataInputStream.readUTF() , new TypeToken<ArrayList<DiscountCode>>(){}.getType());
        for (DiscountCode discountCode : discountCodes) {
            listView.getItems().add(new CheckBox(discountCode.getInformation()));
        }
        listView.relocate(300,0);
        listView.setPrefHeight(300);
        listView.setPrefWidth(500);
    }

    public void editDiscountCode(ActionEvent actionEvent) throws IOException {
        EditDiscountCode.setStage((Stage) ((Node)actionEvent.getSource()).getScene().getWindow());
        errorLabel.setVisible(false);
        if  (checkError()) return;
        setEditDiscountCodeStaticField();
        newPopup(actionEvent , "../../../../fxml/holyManager/EditDiscountCode.fxml");
    }

    private void setEditDiscountCodeStaticField() {
        DiscountCode discountCode;
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()) {
                discountCode = getDiscountCode(item);
                EditDiscountCode.setDiscountCode(discountCode);
                break;
            }
        }
    }

    private DiscountCode getDiscountCode(CheckBox item) {
        DiscountCode discountCode = null;
        String discountCodeInformation = item.getText();
        String code = "";
        code = getCode(discountCodeInformation, code);
        try {
            discountCode = YaDataManager.getDiscountCodeWithCode(code);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return discountCode;
    }

    private String getCode(String discountCodeInformation, String code) {
        char[] itemTextCharArray = discountCodeInformation.toCharArray();
        for (int i = 7 ; i < discountCodeInformation.length() ; i++){
            if (itemTextCharArray[i] == ',') {
                code = discountCodeInformation.substring(7, i);
               break;
            }
        }
        return code;
    }

    private boolean checkError() {
        int counter = 0;
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected())
                counter++;
            if (counter == 2){
                error();
                return true;
            }
        }
        if (counter == 0) {
            error();
            return true;
        }
        return false;
    }

    public void error(){
        errorLabel.setText("Select a discount code");
        errorLabel.setVisible(true);
    }

    public void deleteDiscountCode(ActionEvent actionEvent) throws IOException {
        DiscountCode discountCode;
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()){
                discountCode = getDiscountCode(item);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("Delete discount code " + discountCode.getCode());
                dataOutputStream.flush();
            }
        }
        try {
            setUpPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


