package view.graphical;

import controller.commodity.CommentsMenu;
import controller.commodity.DigestMenu;
import controller.share.CommodityMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommodityPage implements Initializable {
    private final CommodityMenu commodityMenu = View.commodityMenu;
    private final DigestMenu digestMenu = View.digestMenu;
    private final CommentsMenu commentsMenu = View.commentsMenu;
    public Label commodityName;
    public Label commodityPriceAndRating;
    public Label commodityDescription;
    public GridPane fieldsGridPane;
    public Label commodityBrand;
    public ImageView commodityImage;
    public ChoiceBox comparableCommodities;
    public Button logOutButton;
    public Button addToCartButton;
    public ImageView secondCommodityImage;
    public Label secondCommodityRating;
    public Label firstCommodityRating;
    public Label secondCommodityPrice;
    public Label firstCommodityPrice;
    public Label secondCommodityBrand;
    public Label secondCommodityName;
    public GridPane compareGridPane;
    public Label addToCartLabel;
    Popup popupMenu = new Popup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Commodity commodity = commodityMenu.getCommodity();
        commodityName.setText(commodity.getName());
        commodityDescription.setText(commodity.getDescription());
        commodityPriceAndRating.setText("Price: " + commodity.getPrice() + "Rials,\t\tRating: " + String.format("%.1f",
                commodity.getAverageScore()));
        commodityBrand.setText(commodity.getBrand());
        try {
            commodityImage.setImage(new Image(new FileInputStream(commodity.getImagePath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        commodityImage.setPreserveRatio(true);
        for (int i = 0; i < commodity.getCategorySpecifications().size(); i++) {
            fieldsGridPane.add(new ModifiedLabel(commodity.getCategorySpecifications().get(i).getTitle()), 0, i);
            fieldsGridPane.add(new ModifiedLabel(String.valueOf(commodity.getCategorySpecifications().get(i).getValue())
            ), 1, i);
        }
        if (Session.getOnlineAccount() != null) {
            logOutButton.setDisable(true);
            if (Session.getOnlineAccount().getAccountType().equals("personal")) {
                addToCartButton.setDisable(true);
            } else {
                addToCartButton.setDisable(false);
            }
        } else {
            logOutButton.setDisable(false);
        }
        List<String> commoditiesList = new ArrayList<>();
        for (Commodity commodity1 : commodity.getCategory().getCommodities()) {
            if (!commodity.equals(commodity1)) {
                commoditiesList.add(commodity1.getName() + ", " + commodity1.getBrand());
            }
        }
        ObservableList<String> observableList = FXCollections.observableList(commoditiesList);
        comparableCommodities.setItems(observableList);
    }

    public void onCompareClick(MouseEvent mouseEvent) throws FileNotFoundException {
        if (comparableCommodities.getValue() != null) {
            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource("../../fxml/Compare.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Commodity comparingCommodity = null;
            for (Commodity commodity : commodityMenu.getCommodity().getCategory().getCommodities()) {
                if ((commodity.getName() + ", " + commodity.getBrand()).equals(comparableCommodities.getValue())) {
                    comparingCommodity = commodity;
                }
            }
            firstCommodityPrice.setText(String.valueOf(commodityMenu.getCommodity().getPrice()));
            firstCommodityRating.setText(String.format("%.1f", commodityMenu.getCommodity().getAverageScore()));
            assert comparingCommodity != null;
            secondCommodityBrand.setText(comparingCommodity.getBrand());
            secondCommodityImage.setImage(new Image(new FileInputStream(comparingCommodity.getImagePath())));
            secondCommodityName.setText(comparingCommodity.getName());
            secondCommodityPrice.setText(String.valueOf(comparingCommodity.getPrice()));
            secondCommodityRating.setText(String.format("%.1f", comparingCommodity.getAverageScore()));
            for (int i = 0; i < comparingCommodity.getCategorySpecifications().size(); i++) {
                compareGridPane.getRowConstraints().add(new RowConstraints(30, 30, 60, Priority.SOMETIMES,
                        VPos.CENTER, true));
                compareGridPane.add(new ModifiedLabel(comparingCommodity.getCategorySpecifications().get(i).getTitle()),
                        i + 5, 0);
                compareGridPane.add(new ModifiedLabel(String.valueOf(comparingCommodity.getCategorySpecifications().get(
                        i).getValue())), i + 5, 2);
                compareGridPane.add(new ModifiedLabel(String.valueOf(commodityMenu.getCommodity().
                        getCategorySpecifications().get(i).getValue())), i + 5, 1);
            }
            popupMenu.getContent().add(parent);
            popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
        }
    }

    public void onAddClick(MouseEvent mouseEvent) {
        try {
            digestMenu.addToCart();
            addToCartLabel.setText("Added to cart successfully");
        } catch (Exception e) {
            addToCartLabel.setText(e.getMessage());
        }

    }

    public void onBackClick(MouseEvent mouseEvent) {
        commodityMenu.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onProductsClick(MouseEvent mouseEvent) {
    }

    public void onUserPanelClick(MouseEvent mouseEvent) {
    }

    public void onLogOutClick(MouseEvent mouseEvent) {
        try {
            commodityMenu.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    private static class ModifiedLabel extends Label {
        public ModifiedLabel(String s) {
            super(s);
            getStyleClass().add("hint-label");
        }
    }
}
