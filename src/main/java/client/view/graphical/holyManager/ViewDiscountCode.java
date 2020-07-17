package client.view.graphical.holyManager;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewDiscountCode extends HolyManager implements Initializable {
    public AnchorPane pane;
    public Label errorLabel;
    private ListView<CheckBox> listView = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(listView);
        setUpPane();
    }

    protected void setUpPane() {
        listView.getItems().removeAll(listView.getItems());
        try {
            for (DiscountCode discountCode : YaDataManager.getDiscountCodes()) {
                listView.getItems().add(new CheckBox(discountCode.getInformation()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.relocate(300,0);
        listView.setPrefHeight(300);
        listView.setPrefWidth(500);
    }

    public void editDiscountCode(ActionEvent actionEvent) {
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

    public void deleteDiscountCode(ActionEvent actionEvent) {
        DiscountCode discountCode;
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()){
                discountCode = getDiscountCode(item);
                try {
                    View.getDiscountCode.deleteDiscountCode(discountCode);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        setUpPane();
    }
}
