package client.view.graphical;

import client.Session;
import client.controller.reseller.ClientResellerMenu;
import client.view.commandline.View;
import common.model.commodity.Off;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import client.controller.reseller.ManageResellerOffsMenu;
import server.controller.reseller.ResellerMenu;
import client.controller.share.MenuHandler;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManageResellerOffs implements Initializable {
    private final ManageResellerOffsMenu manageResellerOffsMenu = View.manageResellerOffMenu;
    private final ResellerMenu resellerMenu = View.resellerMenu;
    public ChoiceBox<String> manageOffsSortField;
    public ToggleButton manageOffsSortOrderToggleButton;
    public ListView<HBox> offsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSortFieldChoiceBox();
        onSortButtonClick();
    }

    private void initializeSortFieldChoiceBox() {
        List<String> fieldsList = new ArrayList<>(Arrays.asList("discount percent", "start time", "end time"));
        ObservableList<String> observableList = FXCollections.observableList(fieldsList);
        manageOffsSortField.setItems(observableList);
    }

    public void onBackClick(MouseEvent mouseEvent) {
        MenuHandler.getInstance().getCurrentMenu().goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onAddOffClick() {
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/reseller/AddOffPopup.fxml"));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert parent != null;
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Add off");
        stage.setScene(scene);
        stage.show();
    }

    public void onSortButtonClick() {
        try {
            offsListView.getItems().clear();
            ArrayList<Off> offs = null;
            try {
                if (manageOffsSortField.getValue() == null) {
                    offs = ClientResellerMenu.manageOffs();
                } else {
                    offs = manageResellerOffsMenu.sort(manageOffsSortField.getValue());
                }
            } catch (Exception e) {
                // Be Tokhmam
            }
            List<HBox> hBoxes = new ArrayList<>();
            for (int i = 0; i < Objects.requireNonNull(offs).size(); i++) {
                Off off = manageOffsSortOrderToggleButton.isSelected() ?
                        offs.get(offs.size() - 1 - i) : offs.get(i);
                HBox offHBox = new HBox();
                Label offIdLabel = new Label(off.getOffID() + " " + off.getStartTime().toString() + " " +
                        off.getStartTime().toString());
                offIdLabel.getStyleClass().add("hint-label");
                Button edit = new Button("Show/Edit");
                edit.getStyleClass().add("small-button");
                edit.setOnMouseClicked(mouseEvent1 -> {
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader(getClass().
                            getResource("../../../fxml/reseller/ShowEditOff.fxml"));
                    try {
                        parent = loader.load();
                    } catch (IOException e) {
                        // Khob chi kar konam?
                    }
                    ShowEditOff showEditOff = loader.getController();
                    try {
                        showEditOff.initScene(off);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    assert parent != null;
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Show/Edit off");
                    stage.show();
                });
                offHBox.getChildren().addAll(offIdLabel, edit);
                hBoxes.add(offHBox);
            }
            ObservableList<HBox> hBoxObservableList = FXCollections.observableList(hBoxes);
            offsListView.setItems(hBoxObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onProductsClick(MouseEvent mouseEvent) {
        View.manageResellerOffMenu.products();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }
}
