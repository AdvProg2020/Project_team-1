package view.graphical;

import controller.reseller.ResellerMenu;
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
import model.commodity.Category;
import model.field.Field;
import view.commandline.View;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final ResellerMenu resellerMenu = View.resellerMenu;
    public TextField brandTf;
    public TextField nameTf;
    public TextField priceTf;
    public TextArea descriptionTextArea;
    public TextField amountTf;
    public TreeView<String> selectedMediaTv;
    public ComboBox<String> categoryCb;
    public Label errorMessageLabel;
    private final TreeItem<String> photosPath = new TreeItem<>("Photos");
    private final TreeItem<String> videosPath = new TreeItem<>("Videos");
    private ArrayList<Field> productCategorySpecification = new ArrayList<>();

    public void setProductCategorySpecification(ArrayList<Field> productCategorySpecification) {
        this.productCategorySpecification = productCategorySpecification;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList = FXCollections.observableList(resellerMenu.getCategoriesName());
        categoryCb.setItems(observableList);
        categoryCb.valueProperty().addListener(
                (observableValue, s, t1) -> getCategorySpecification(t1));
        TreeItem<String> root = new TreeItem<>("Media");
        root.getChildren().addAll(photosPath, videosPath);
        selectedMediaTv.setRoot(root);
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
            String imagePath = file.toURI().toString().substring(5);
            photosPath.getChildren().add(new TreeItem<>(imagePath));
        }
    }

    public void onChooseProductVideoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("mp4 file", "*.mp4");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        if (file != null) {
            String videoPath = file.toURI().toString().substring(5);
            videosPath.getChildren().add(new TreeItem<>(videoPath));
        }
    }

    public void onAddClick(MouseEvent mouseEvent) {
        try {
            if (photosPath.getChildren().size() == 0) {
                throw new Exception("Choose a photo");
            }
            String imagePath = photosPath.getChildren().get(0).getValue();
            resellerMenu.addProduct(brandTf.getText(), nameTf.getText(), Integer.parseInt(priceTf.getText()),
                    resellerMenu.getCategoryByName(categoryCb.getValue()),
                    productCategorySpecification, descriptionTextArea.getText(),
                    Integer.parseInt(amountTf.getText()), imagePath);
            onCancelClick(mouseEvent);
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    private void getCategorySpecification(String categoryName) {
        Popup popupMenu = new Popup();
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/reseller/GetCategorySpecification.fxml"));
        Category category = null;
        try {
            category = resellerMenu.getCategoryByName(categoryName);
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
}
