package view.graphical;

import controller.reseller.ManageResellerProductsMenu;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManageResellerProducts implements Initializable {

    private final ManageResellerProductsMenu manageResellerProductsMenu = View.manageResellerProductsMenu;
    private final ResellerMenu resellerMenu = View.resellerMenu;
    public AnchorPane manageProductsAnchorPane;
    public ChoiceBox<String> manageProductsSortField;
    public ToggleButton manageProductsSortOrderToggleButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSortFieldChoiceBox();
        onSortButtonClick();
    }

    public void onAddProductClick() {
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

    private void initializeSortFieldChoiceBox() {
        List<String> fieldsList = new ArrayList<>(Arrays.asList("brand", "name", "id", "price", "visits",
                "number of scores", "average score"));
        ObservableList<String> observableList = FXCollections.observableList(fieldsList);
        manageProductsSortField.setItems(observableList);
    }

    public void onSortButtonClick() {
        try {
            manageProductsAnchorPane.getChildren().clear();
            ArrayList<Commodity> commodities = null;
            try {
                if (manageProductsSortField.getValue() == null) {
                    commodities = resellerMenu.manageCommodities();
                } else {
                    commodities = manageResellerProductsMenu.sort(manageProductsSortField.getValue());
                }
            } catch (Exception e) {
                // Be Tokhmam
            }
            for (int i = 0; i < Objects.requireNonNull(commodities).size(); i++) {
                Commodity commodity = manageProductsSortOrderToggleButton.isSelected() ?
                        commodities.get(commodities.size() - 1 - i) : commodities.get(i);
                AnchorPane productAnchorPane = new AnchorPane();
                productAnchorPane.setMaxHeight(300);
                productAnchorPane.setMaxWidth(300);
                ImageView productImage = new ImageView(new Image(new FileInputStream(commodity.getImagePath())));
                productImage.setPreserveRatio(true);
                productImage.setFitHeight(250);
                productImage.setFitWidth(250);
                Label productName = new Label(commodity.getName());
                HBox actions = new HBox();
                actions.setAlignment(Pos.CENTER);
                Button edit = new Button("Show/Edit"), remove = new Button("Remove");
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
                actions.getChildren().addAll(edit, remove);
                productAnchorPane.getChildren().addAll(productImage, productName, actions);
                manageProductsAnchorPane.getChildren().add(productAnchorPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onBackClick(MouseEvent mouseEvent) {
        MenuHandler.getInstance().getCurrentMenu().goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }
}
