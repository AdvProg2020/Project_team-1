package view.graphical;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.commodity.Commodity;

public class ShowEditProduct {

    private Commodity commodity;
    public Label brandLabel;
    public Label nameLabel;
    public Label priceLabel;
    public Label descriptionLabel;
    public Label amountLabel;
    public TreeView<String> selectedMediaTv;
    public Label errorMessageLabel;
    private final TreeItem<String> photosPath = new TreeItem<>("Photos");
    private final TreeItem<String> videosPath = new TreeItem<>("Videos");

    public void initScene(Commodity commodity) {
        this.commodity = commodity;
        brandLabel.setText(commodity.getBrand());
        nameLabel.setText(commodity.getName());
        priceLabel.setText(String.valueOf(commodity.getPrice()));
        descriptionLabel.setText(commodity.getDescription());
        amountLabel.setText(String.valueOf(commodity.getInventory()));
        TreeItem<String> root = new TreeItem<>("Media");
        photosPath.getChildren().add(new TreeItem<>(commodity.getImagePath()));
        root.getChildren().addAll(photosPath, videosPath);
        selectedMediaTv.setRoot(root);
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void onSaveClick(MouseEvent mouseEvent) {
        // Save change
        onCancelClick(mouseEvent);
    }

    public void onBrandLabelClick(MouseEvent mouseEvent) {
    }

    public void onNameLabelClick(MouseEvent mouseEvent) {
    }

    public void onPriceLabelClick(MouseEvent mouseEvent) {
    }

    public void onDescriptionLabelClick(MouseEvent mouseEvent) {
    }

    public void onAmountLabelClick(MouseEvent mouseEvent) {
    }

    public void onChooseProductVideoClick(MouseEvent mouseEvent) {
    }

    public void onChooseProductImageClick(MouseEvent mouseEvent) {
    }

    public void onEditCategorySpecsClick(MouseEvent mouseEvent) {
    }
}
