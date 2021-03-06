package client.view.graphical;

import client.controller.reseller.ClientResellerMenu;
import common.model.commodity.Category;
import common.model.field.Field;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {
    private final TreeItem<String> photosPath = new TreeItem<>("Photos");
    private final TreeItem<String> videosPath = new TreeItem<>("Videos");
    public CheckBox isProductFileCheckBox;
    public Button chooseProductFileButton;
    public TextField brandTf;
    public TextField nameTf;
    public TextField priceTf;
    public TextArea descriptionTextArea;
    public TextField amountTf;
    public TreeView<String> selectedMediaTv;
    public ComboBox<String> categoryCb;
    public Label errorMessageLabel;
    private Stage stage;
    private ArrayList<Field> productCategorySpecification = new ArrayList<>();
    private String productFilePath = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setProductCategorySpecification(ArrayList<Field> productCategorySpecification) {
        this.productCategorySpecification = productCategorySpecification;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList = FXCollections.observableList(ClientResellerMenu.getCategoriesName());
        categoryCb.setItems(observableList);
        categoryCb.valueProperty().addListener(
                (observableValue, s, t1) -> getCategorySpecification(t1));
        TreeItem<String> root = new TreeItem<>("Media");
        root.getChildren().addAll(photosPath, videosPath);
        selectedMediaTv.setRoot(root);
        chooseProductFileButton.setDisable(true);
        isProductFileCheckBox.setOnAction(actionEvent -> {
            chooseProductFileButton.setDisable(!isProductFileCheckBox.isSelected());
        });
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void onChooseProductImageClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        if (file != null) {
            String imagePath = file.getAbsolutePath();
            photosPath.getChildren().add(new TreeItem<>(imagePath));
        }
    }

    public void onChooseProductVideoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("mp4 file", "*.mp4");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        if (file != null) {
            String videoPath = file.getAbsolutePath();
            videosPath.getChildren().add(new TreeItem<>(videoPath));
        }
    }

    public void onAddClick(MouseEvent mouseEvent) {
        try {
            if (photosPath.getChildren().size() == 0) {
                throw new Exception("Choose a photo");
            }
            String imagePath = photosPath.getChildren().get(0).getValue();
            System.out.println("FCB");
            ClientResellerMenu.addProduct(brandTf.getText(), nameTf.getText(), Integer.parseInt(priceTf.getText()),
                    ClientResellerMenu.getCategoryByName(categoryCb.getValue()),
                    productCategorySpecification, descriptionTextArea.getText(),
                    Integer.parseInt(amountTf.getText()), productFilePath, imagePath);
            System.out.println("RMA");
            onCancelClick(mouseEvent);
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    private void getCategorySpecification(String categoryName) {
        Popup popupMenu = new Popup();
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../../../fxml/reseller/GetCategorySpecification.fxml"));
        Category category = null;
        try {
            category = ClientResellerMenu.getCategoryByName(categoryName);
            parent = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert parent != null;
        GetCategorySpecs getCategorySpecs = loader.getController();
        getCategorySpecs.populateVBox(category);
        getCategorySpecs.setAddProduct(this);
        popupMenu.getContent().add(parent);
        popupMenu.show(stage);
    }

    public void onChooseProductFileClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        if (file != null) {
            productFilePath = file.getAbsolutePath();
            chooseProductFileButton.setText(file.getName());
        }
    }
}
