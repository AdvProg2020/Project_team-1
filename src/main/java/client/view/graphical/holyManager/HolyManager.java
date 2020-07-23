package client.view.graphical.holyManager;

import client.Session;
import client.view.AudioPlayer;
import client.view.commandline.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;
import server.controller.share.MenuHandler;
import static client.Main.socket;
import static client.Main.socketB;

import java.io.DataOutputStream;
import java.io.IOException;

public class HolyManager {

    public TextField minCurrencyOrWage;
    public Label error;

    public void viewPersonalInfo(ActionEvent actionEvent) {
        View.viewPersonalInfoMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.viewPersonalInfoMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));

    }

    public void manageUsers(ActionEvent actionEvent) {
        View.manageUsersPanel.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.manageUsersPanel);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void manageCategories(ActionEvent actionEvent) {
        View.manageCategoriesMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.manageCategoriesMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));

    }

    public void manageRequests(ActionEvent actionEvent) {
        View.manageRequestsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.manageRequestsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));

    }

    public void viewDiscountCode(ActionEvent actionEvent) {
        View.getDiscountCodeMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.getDiscountCodeMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void createDiscountCode(ActionEvent actionEvent) {
        View.createDiscountCodeMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.createDiscountCodeMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
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

    public void goToMainMenu(ActionEvent actionEvent) {
        View.mainMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void setValues(ActionEvent actionEvent){
        newPopup(actionEvent , "../../../../fxml/HolyManager/SetValues.fxml");
    }

    public void setMinimumCurrency(ActionEvent actionEvent) throws IOException {
        try {
            System.out.println(minCurrencyOrWage.getText());
            Double minCurrency = Double.parseDouble(minCurrencyOrWage.getText());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Set min currency " + minCurrency);
            dataOutputStream.flush();
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }catch (Exception e){
            error.setVisible(true);
            error.setText("Invalid minimum currency or wage.");
        }

    }

    public void setWage(ActionEvent actionEvent) {
        try {
            Double wage = Double.parseDouble(minCurrencyOrWage.getText());
            if (wage > 100)
                throw new Exception("Invalid wage.");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Set wage " + wage);
            dataOutputStream.flush();
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }catch (Exception e){
            error.setVisible(true);
            error.setText("Invalid minimum currency or wage.");
        }
    }
}
