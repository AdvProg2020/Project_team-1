package client.view.graphical;

import client.view.commandline.View;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import server.data.YaDataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowEditOff {

    public Label startTime;
    public Label endTime;
    public Label discountPercent;
    public ListView<HBox> productsInOffListView;
    public Label errorMessageLabel;
    public TextField productIdTf;
    ArrayList<Commodity> addedProducts = new ArrayList<>(),
    removedProducts = new ArrayList<>();
    private Off off;

    public void initScene(Off off) throws Exception {
        this.off = off;
        startTime.setText(off.getStartTime().toString());
        endTime.setText(off.getEndTime().toString());
        discountPercent.setText(String.valueOf(off.getDiscountPercent()));
        List<HBox> hBoxes = new ArrayList<>();
        for (int commodityId : off.getCommoditiesId()) {
            HBox productHBox = new HBox();
            Commodity commodity = YaDataManager.getCommodityById(commodityId);
            Label productLabel = new Label(commodity.getName() + " #" + commodity.getCommodityId());
            productLabel.getStyleClass().add("hint-label");
            Button remove = new Button("Remove");
            remove.getStyleClass().add("small-button");
            remove.setOnMouseClicked(mouseEvent -> {
                removedProducts.add(commodity);
                remove.setText("Removed");
                remove.setDisable(true);
            });
            productHBox.getChildren().addAll(productLabel, remove);
            hBoxes.add(productHBox);
        }
        ObservableList<HBox> hBoxObservableList = FXCollections.observableList(hBoxes);
        productsInOffListView.setItems(hBoxObservableList);
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void onSaveClick(MouseEvent mouseEvent) {
        try {
            View.manageResellerOffMenu.editOff(off, removedProducts, addedProducts,
                    startTime.getText().equals(off.getStartTime().toString()) ? "-": startTime.getText(),
                    endTime.getText().equals(off.getEndTime().toString()) ? "-": endTime.getText(),
                    (int) Double.parseDouble(discountPercent.getText()));
            onCancelClick(mouseEvent);
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onStartTimeClick(MouseEvent mouseEvent) {
        openDatePickerPopup((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow(), true);
    }

    public void onEndTimeClick(MouseEvent mouseEvent) {
        openDatePickerPopup((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow(), false);
    }

    private void openDatePickerPopup(Stage stage, boolean startDate) {
        Popup popup = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/DatePickerPopup.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            // What should I do? If I suddenly feel ...
        }
        DatePickerPopup datePickerPopup = loader.getController();
        datePickerPopup.setShowEditOff(this);
        datePickerPopup.setStartDate(startDate);
        popup.getContent().add(parent);
        popup.show(stage);
    }

    public void onDiscountPercentClick(MouseEvent mouseEvent) {
        Popup popup = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/PercentPickerPopup.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            // What should I do? If I suddenly feel ...
        }
        PercentPickerPopup percentPickerPopup = loader.getController();
        percentPickerPopup.setShowEditOff(this);
        popup.getContent().add(parent);
        popup.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onAddProductToOffClick(MouseEvent mouseEvent) {
        try {
            addedProducts.add(View.manageResellerProductsMenu.getCommodityById(Integer.parseInt(productIdTf.getText())));
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }
}
