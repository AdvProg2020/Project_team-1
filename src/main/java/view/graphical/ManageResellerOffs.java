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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Session;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/reseller/AddProductPopup.fxml"));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert parent != null;
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        AddProduct addProduct = loader.getController();
        addProduct.setStage(stage);
        stage.show();
    }

    public void onSortButtonClick() {
        try {
            manageOffsTilePane.getChildren().clear();
            ArrayList<Commodity> commodities = null;
            try {
                if (manageOffsSortField.getValue() == null) {
                    commodities = resellerMenu.manageCommodities();
                } else {
                    //commodities = manageResellerOffsMenu.sort(manageOffsSortField.getValue());
                }
            } catch (Exception e) {
                // Be Tokhmam
            }
            for (int i = 0; i < Objects.requireNonNull(commodities).size(); i++) {
                Commodity commodity = manageOffsSortOrderToggleButton.isSelected() ?
                        commodities.get(commodities.size() - 1 - i) : commodities.get(i);
                AnchorPane productAnchorPane = new AnchorPane();
                productAnchorPane.setMaxHeight(318);
                productAnchorPane.setMaxWidth(318);
                ImageView productImage = new ImageView(new Image(new FileInputStream(commodity.getImagePath())));
                productImage.setPreserveRatio(true);
                productImage.setFitHeight(250);
                productImage.setFitWidth(250);
                Label productName = new Label(commodity.getName());
                productName.getStyleClass().add("hint-label");
                HBox actions = new HBox();
                actions.setAlignment(Pos.CENTER);
                Button edit = new Button("Show/Edit"), remove = new Button("Remove");
                edit.getStyleClass().add("small-button");
                remove.getStyleClass().add("small-button");
                edit.setOnMouseClicked(mouseEvent1 -> {
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader(getClass().
                            getResource("../../fxml/reseller/ShowEditProduct.fxml"));
                    try {
                        parent = loader.load();
                    } catch (IOException e) {
                        // Khob chi kar konam?
                    }
                    ShowEditProduct showEditProduct = loader.getController();
                    showEditProduct.initScene(commodity);
                    assert parent != null;
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    showEditProduct.setStage(stage);
                    stage.setScene(scene);
                    stage.setTitle("Show/Edit product");
                    stage.show();
                });
                remove.setOnMouseClicked(mouseEvent12 -> {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to remove this product?", ButtonType.NO, ButtonType.YES);
                    confirmation.setResizable(false);
                    confirmation.setHeight(200);
                    confirmation.setWidth(500);
                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        try {
                            resellerMenu.removeProduct(commodity.getCommodityId());
                        } catch (Exception e) {
                            // Be Kiram
                        }
                    }
                    onSortButtonClick();
                });
                actions.getChildren().addAll(productName, edit, remove);
                productAnchorPane.getChildren().addAll(productImage, actions);
                manageOffsTilePane.getChildren().add(productAnchorPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
