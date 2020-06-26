package view.graphical.purchase;

import controller.customer.CartMenu;
import controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.PersonalAccount;
import model.commodity.Commodity;
import view.AudioPlayer;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Cart implements Initializable {
    private final CartMenu cartMenu = View.cartMenu;
    public Label totalPrice;
    public GridPane cartGridPane;
    public Label error;
    public ScrollPane scrollPane;


    public void pause(ActionEvent actionEvent) {
        AudioPlayer.mediaPlayer.pause();
    }

    public void play(ActionEvent actionEvent) {
        AudioPlayer.mediaPlayer.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (scrollPane.getContent() == null) {
            scrollPane.setContent(cartGridPane);
        }
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        totalPrice.setText("Total price: " + cartMenu.calculateTotalPrice() + " Rials");
        int counter = 0;
        for (Commodity commodity : account.getCart().keySet()) {
            try {
                GridPane commodityGridPane = new GridPane();
                ImageView imageView = new ImageView(new Image(new FileInputStream(commodity.getImagePath())));
                imageView.setOnMouseClicked(mouseEvent -> {
                    View.commodityMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
                    View.commodityMenu.setCommodity(commodity);
                    commodity.setNumberOfVisits(commodity.getNumberOfVisits() + 1);
                    MenuHandler.getInstance().setCurrentMenu(View.commodityMenu);
                    Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
                });
                imageView.setFitWidth(250);
                imageView.setFitHeight(250);
                commodityGridPane.add(imageView, 0, 0, 1, 4);
                commodityGridPane.add(new ModifiedLabel("Name: " + commodity.getName()), 1, 0, 2, 1);
                commodityGridPane.add(new ModifiedLabel("Number: " + account.getAmount(commodity) + ", Price: " + commodity.getPrice() + " Rials"), 1, 1, 2, 1);
                commodityGridPane.add(new ModifiedLabel("Total price: " + account.getAmount(commodity) * commodity.getPrice()), 1, 2, 2, 1);
                ImageView plusButton = new ImageView("icons/plus.png");
                plusButton.setFitWidth(32);
                plusButton.setFitHeight(32);
                plusButton.setOnMouseClicked(actionEvent -> {
                    try {
                        cartMenu.increase(commodity.getCommodityId());
                        initialize(url, resourceBundle);
                        error.setText("Increased successfully");
                    } catch (Exception e) {
                        error.setText(e.getMessage());
                    }
                });
                commodityGridPane.add(plusButton, 1, 3);
                ImageView minusButton = new ImageView("icons/minus.png");
                minusButton.setFitHeight(32);
                minusButton.setFitWidth(32);
                minusButton.setOnMouseClicked(actionEvent -> {
                    try {
                        cartMenu.decrease(commodity.getCommodityId());
                        if (account.getAmount(commodity) == 0) {
                            scrollPane.setContent(null);
                        }
                        initialize(url, resourceBundle);
                        error.setText("Decreased successfully");
                    } catch (Exception e) {
                        error.setText(e.getMessage());
                    }
                });
                commodityGridPane.add(minusButton, 2, 3);
                cartGridPane.add(commodityGridPane, counter % 2, counter / 2);
                counter++;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void back(ActionEvent actionEvent) {
        MenuHandler.getInstance().getCurrentMenu().goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void logout(ActionEvent actionEvent) {
        try {
            MenuHandler.getInstance().getCurrentMenu().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void goToProductsMenu(ActionEvent actionEvent) {
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void goToGetInfoPage(ActionEvent actionEvent) {
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/customer/purchase/Information.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
    }

    private static class ModifiedLabel extends Label {
        public ModifiedLabel(String s) {
            super(s);
            getStyleClass().add("hint-label");
        }
    }
}
