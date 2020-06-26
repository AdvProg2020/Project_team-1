package view.graphical.commodity;

import controller.share.CommodityMenu;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Compare implements Initializable {
    private final CommodityMenu commodityMenu = View.commodityMenu;
    public GridPane compareGridPane;
    public Label commodityName;
    public Label secondCommodityName;
    public Label commodityBrand;
    public Label secondCommodityBrand;
    public Label commodityPrice;
    public Label secondCommodityPrice;
    public Label commodityRating;
    public Label secondCommodityRating;
    public ImageView commodityImage;
    public ImageView secondCommodityImage;
    private Commodity comparingCommodity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comparingCommodity = commodityMenu.getComparingCommodity();
        commodityName.setText(commodityMenu.getCommodity().getName());
        commodityBrand.setText(commodityMenu.getCommodity().getBrand());
        commodityPrice.setText(String.valueOf(commodityMenu.getCommodity().getPrice()));
        commodityRating.setText(String.format("%.1f", commodityMenu.getCommodity().getAverageScore()));
        assert comparingCommodity != null;
        secondCommodityBrand.setText(comparingCommodity.getBrand());
        try {
            commodityImage.setImage(new Image(new FileInputStream(commodityMenu.getCommodity().getImagePath())));
            secondCommodityImage.setImage(new Image(new FileInputStream(comparingCommodity.getImagePath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        secondCommodityName.setText(comparingCommodity.getName());
        secondCommodityPrice.setText(String.valueOf(comparingCommodity.getPrice()));
        secondCommodityRating.setText(String.format("%.1f", comparingCommodity.getAverageScore()));
        for (int i = 0; i < comparingCommodity.getCategorySpecifications().size(); i++) {
            compareGridPane.getRowConstraints().add(new RowConstraints(30, 30, 60, Priority.SOMETIMES,
                    VPos.CENTER, true));
            compareGridPane.add(new CommodityPage.ModifiedLabel(comparingCommodity.getCategorySpecifications().get(i).getTitle()),
                    0, i+6);
            compareGridPane.add(new CommodityPage.ModifiedLabel(String.valueOf(comparingCommodity.getCategorySpecifications().get(
                    i).getValue())), 2, i+6);
            compareGridPane.add(new CommodityPage.ModifiedLabel(String.valueOf(commodityMenu.getCommodity().
                    getCategorySpecifications().get(i).getValue())), 1, i+6);
        }
    }

    public void back(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
