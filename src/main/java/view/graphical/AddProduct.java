package view.graphical;

import controller.reseller.ResellerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import view.commandline.View;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList = FXCollections.observableList(resellerMenu.getCategoriesName());
        categoryCb.setItems(observableList);
        TreeItem<String> root = new TreeItem<String>("Media");
        root.getChildren().addAll(photosPath, videosPath);
        selectedMediaTv.setRoot(root);
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onChooseProductImageClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        String imagePath = file.toURI().toString();
        photosPath.getChildren().add(new TreeItem<>(imagePath));
    }

    public void onChooseProductVideoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("mp4 file", "*.mp4");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        String videoPath = file.toURI().toString();
        videosPath.getChildren().add(new TreeItem<>(videoPath));
    }

    public void onAddClick(MouseEvent mouseEvent) {

    }
}
