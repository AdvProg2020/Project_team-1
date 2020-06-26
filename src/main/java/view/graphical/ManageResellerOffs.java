package view.graphical;

import controller.reseller.ManageResellerOffsMenu;
import controller.reseller.ResellerMenu;
import controller.share.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Session;
import model.commodity.Off;
import view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManageResellerOffs implements Initializable {
    private final ManageResellerOffsMenu manageResellerOffsMenu = View.manageResellerOffMenu;
    private final ResellerMenu resellerMenu = View.resellerMenu;
    public TilePane manageOffsTilePane;
    public ChoiceBox<String> manageOffsSortField;
    public ToggleButton manageOffsSortOrderToggleButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSortFieldChoiceBox();
        onSortButtonClick();
    }

    private void initializeSortFieldChoiceBox() {
        List<String> fieldsList = new ArrayList<>(Arrays.asList("discount percent", "start time", "id", "end time"));
        ObservableList<String> observableList = FXCollections.observableList(fieldsList);
        manageOffsSortField.setItems(observableList);
    }

    public void onBackClick(MouseEvent mouseEvent) {
        MenuHandler.getInstance().getCurrentMenu().goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onAddOffClick() {
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/reseller/AddOffPopup.fxml"));
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
            manageOffsTilePane.getChildren().clear();
            ArrayList<Off> offs = null;
            try {
                if (manageOffsSortField.getValue() == null) {
                    offs = resellerMenu.manageOffs();
                } else {
                    offs = manageResellerOffsMenu.sort(manageOffsSortField.getValue());
                }
            } catch (Exception e) {
                // Be Tokhmam
            }
            for (int i = 0; i < Objects.requireNonNull(offs).size(); i++) {
                Off off = manageOffsSortOrderToggleButton.isSelected() ?
                        offs.get(offs.size() - 1 - i) : offs.get(i);
                AnchorPane productAnchorPane = new AnchorPane();
                productAnchorPane.setMaxHeight(318);
                productAnchorPane.setMaxWidth(318);
                Label offIdLabel = new Label(String.valueOf(off.getOffID()));
                offIdLabel.getStyleClass().add("hint-label");
                HBox actions = new HBox();
                actions.setAlignment(Pos.CENTER);
                Button edit = new Button("Show/Edit");
                edit.getStyleClass().add("small-button");
                edit.setOnMouseClicked(mouseEvent1 -> {
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader(getClass().
                            getResource("../../fxml/reseller/ShowEditOff.fxml"));
                    try {
                        parent = loader.load();
                    } catch (IOException e) {
                        // Khob chi kar konam?
                    }
                    ShowEditOff showEditOff = loader.getController();
                    showEditOff.initScene(off);
                    assert parent != null;
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Show/Edit off");
                    stage.show();
                });
                actions.getChildren().addAll(offIdLabel, edit);
                productAnchorPane.getChildren().addAll(actions);
                manageOffsTilePane.getChildren().add(productAnchorPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
