package client.view.graphical.customer;

import client.controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import client.Session;
import client.view.AudioPlayer;
import client.view.commandline.View;

import java.io.IOException;

public class CustomerPanel {
    Popup popupMenu = new Popup();

    public void viewPersonalInfo(ActionEvent actionEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../../fxml/customer/ViewInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
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

    public void pause(ActionEvent actionEvent) {
        AudioPlayer.mediaPlayer.pause();
    }

    public void play(ActionEvent actionEvent) {
        AudioPlayer.mediaPlayer.play();
    }

    public void goToProductsMenu(ActionEvent actionEvent) {
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void gotoCartMenu(ActionEvent actionEvent) {
        View.cartMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.cartMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void viewOrders(ActionEvent actionEvent) {
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("../../../../fxml/customer/OrdersHistory.fxml"));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrdersHistory controller = loader.getController();
        controller.setPopup(popupMenu);
        controller.setStage((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void viewDiscounts(ActionEvent actionEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../../fxml/customer/DiscountCodes.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void onlineAccounts(ActionEvent actionEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../../fxml/customer/OnlineSupportAccounts.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
        OnlineSupportAccounts.setStage((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void wallet(ActionEvent actionEvent) {
        newPopup(actionEvent , "../../../../fxml/Wallet.fxml");
    }

    protected void newPopup(ActionEvent actionEvent, String filePath) {
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show((((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void auctions(ActionEvent actionEvent) {
        View.auctionsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.auctionsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }
}
