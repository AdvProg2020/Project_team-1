package view.graphical.commodity;

import controller.commodity.CommentsMenu;
import controller.commodity.DigestMenu;
import controller.customer.OrderMenu;
import controller.share.CommodityMenu;
import controller.share.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    private final OrderMenu orderMenu = View.orderMenu;
    public Label commodityName;
    public Label commodityPriceAndRating;
    public Label commodityDescription;
    public GridPane fieldsGridPane;
    public Label commodityBrand;
    public ImageView commodityImage;
    public ChoiceBox comparableCommodities;
    public Button logOutButton;
    public Button addToCartButton;
    public Label addToCartLabel;
    private final String emptyStarAddress = "stars/emptyStar.png";
    private final String fullStarAddress = "stars/fullStar.png";
    public ImageView star5;
    public ImageView star4;
    public ImageView star3;
    public ImageView star2;
    public ImageView star1;
    public Button starButton5;
    public Button starButton4;
    public Button starButton3;
    public Button starButton2;
    public Button starButton1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Commodity commodity = commodityMenu.getCommodity();
        commodityName.setText("Name: " + commodity.getName());
        commodityDescription.setText("Description: " + commodity.getDescription());
        commodityPriceAndRating.setText("Price: " + commodity.getPrice() + "Rials,\t\tRating: ");
        commodityBrand.setText("Brand" + commodity.getBrand());
        try {
            if (commodity.getAverageScore() < 4.5) {
                star5.setImage(new Image(emptyStarAddress));
                if (commodity.getAverageScore() < 3.5) {
                    star4.setImage(new Image(emptyStarAddress));
                    if (commodity.getAverageScore() < 2.5) {
                        star3.setImage(new Image(emptyStarAddress));
                        if (commodity.getAverageScore() < 1.5) {
                            star2.setImage(new Image(emptyStarAddress));
                            if (commodity.getAverageScore() < 0.5) {
                                star1.setImage(new Image(emptyStarAddress));
                            } else {
                                star1.setImage(new Image(fullStarAddress));
                            }
                        } else {
                            star2.setImage(new Image(fullStarAddress));
                        }
                    } else {
                        star3.setImage(new Image(fullStarAddress));
                    }
                } else {
                    star4.setImage(new Image(fullStarAddress));
                }
            } else {
                star5.setImage(new Image(fullStarAddress));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Image image = new Image(new FileInputStream(commodity.getImagePath()));
            if (commodity.getInventory() > 0) {
                commodityImage.setImage(image);
            } else {
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();
                PixelReader pixelReader = image.getPixelReader();
                WritableImage grayImage = new WritableImage(width, height);
                PixelWriter pixelWriter = grayImage.getPixelWriter();
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int pixel = pixelReader.getArgb(x, y);
                        int red = ((pixel >> 16) & 0xff);
                        int green = ((pixel >> 8) & 0xff);
                        int blue = (pixel & 0xff);
                        int grayLevel = (int) ((double) red + (double) green + (double) blue) / 3;
                        grayLevel = 255 - grayLevel;
                        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                        pixelWriter.setArgb(x, y, -gray);
                    }
                }
                commodityImage.setImage(grayImage);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        commodityImage.setPreserveRatio(true);
        for (int i = 0; i < commodity.getCategorySpecifications().size(); i++) {
            fieldsGridPane.add(new ModifiedLabel(commodity.getCategorySpecifications().get(i).getTitle()),
                    0, i + 1);
            fieldsGridPane.add(new ModifiedLabel
                    (String.valueOf(commodity.getCategorySpecifications().get(i).getValue())), 1, i + 1);
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
        if (!orderMenu.canRateProduct(commodity.getCommodityId())) {
            starButton1.setDisable(true);
            starButton2.setDisable(true);
            starButton3.setDisable(true);
            starButton4.setDisable(true);
            starButton5.setDisable(true);
        }
    }

    public void onCompareClick(MouseEvent mouseEvent) {
        if (comparableCommodities.getValue() != null) {
            for (Commodity commodity : commodityMenu.getCommodity().getCategory().getCommodities()) {
                if ((commodity.getName() + ", " + commodity.getBrand()).equals(comparableCommodities.getValue())) {
                    commodityMenu.setComparingCommodity(commodity);
                }
            }
            Parent parent = null;
            Popup popupMenu = new Popup();
            try {
                parent = FXMLLoader.load(getClass().getResource("../../../fxml/Compare.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
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
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) mouseEvent.getSource()).getScene().getWindow()));
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

    private void rate(int rate) {
        starButton1.setDisable(true);
        starButton2.setDisable(true);
        starButton3.setDisable(true);
        starButton4.setDisable(true);
        starButton5.setDisable(true);
        starButton1.setStyle("-fx-background-image: url('stars/fullStar.png')");
        try {
            orderMenu.rateProduct(commodityMenu.getCommodity().getCommodityId(), rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rateOne(ActionEvent actionEvent) {
        rate(1);
    }

    public void rateTwo(ActionEvent actionEvent) {
        rate(2);
        starButton2.setStyle("-fx-background-image: url('stars/fullStar.png')");
    }

    public void rateThree(ActionEvent actionEvent) {
        rate(3);
        starButton2.setStyle("-fx-background-image: url('stars/fullStar.png')");
        starButton3.setStyle("-fx-background-image: url('stars/fullStar.png')");
    }

    public void rateFour(ActionEvent actionEvent) {
        rate(4);
        starButton2.setStyle("-fx-background-image: url('stars/fullStar.png')");
        starButton3.setStyle("-fx-background-image: url('stars/fullStar.png')");
        starButton4.setStyle("-fx-background-image: url('stars/fullStar.png')");
    }

    public void rateFive(ActionEvent actionEvent) {
        rate(5);
        starButton2.setStyle("-fx-background-image: url('stars/fullStar.png')");
        starButton3.setStyle("-fx-background-image: url('stars/fullStar.png')");
        starButton4.setStyle("-fx-background-image: url('stars/fullStar.png')");
        starButton5.setStyle("-fx-background-image: url('stars/fullStar.png')");
    }

    protected static class ModifiedLabel extends Label {
        public ModifiedLabel(String s) {
            super(s);
            getStyleClass().add("hint-label");
        }
    }
}