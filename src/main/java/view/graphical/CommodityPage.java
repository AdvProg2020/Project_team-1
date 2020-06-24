package view.graphical;

import controller.share.CommodityMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Session;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommodityPage implements Initializable {
    private final CommodityMenu commodityMenu = View.commodityMenu;
    public Label commodityName;
    public Label commodityPriceAndRating;
    public Label commodityDescription;
    public GridPane fieldsGridPane;
    public GridPane buttonsGridPane;
    public Label commodityBrand;
    public ImageView commodityImage;
    public ChoiceBox comparableCommodities;

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
            buttonsGridPane.add(new LogOutButton(), 0, 0);
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

    public void onCompareClick(MouseEvent mouseEvent) {
    }

    public void onAddClick(MouseEvent mouseEvent) {
    }

    public void onBackClick(MouseEvent mouseEvent) {
    }

    public void onProductsClick(MouseEvent mouseEvent) {
    }

    public void onUserPanelClick(MouseEvent mouseEvent) {
    }

    private static class ModifiedLabel extends Label {
        public ModifiedLabel(String s) {
            super(s);
            getStyleClass().add("hint-label");
        }
    }

    private static class LogOutButton extends Button {
        public LogOutButton() {
            super("Log Out");
            getStyleClass().add("logout-button");
        }
    }
}
