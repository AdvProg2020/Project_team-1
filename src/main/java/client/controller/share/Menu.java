package client.controller.share;

import client.Session;
import client.view.commandline.View;
import javafx.event.ActionEvent;

import static client.Main.outputStream;

public class Menu {
    protected Menu previousMenu;
    protected String fxmlFileAddress;
    protected String stageTitle;

    public Menu getPreviousMenu() {
        return previousMenu;
    }

    public Menu() {
        previousMenu = null;
    }

    public String getFxmlFileAddress() {
        return fxmlFileAddress;
    }

    public String getStageTitle() {
        return stageTitle;
    }

    public void setPreviousMenu(Menu previousMenu) {
        this.previousMenu = previousMenu;
    }

    public void goToPreviousMenu() {
        if (previousMenu != null) {
            MenuHandler.getInstance().setCurrentMenu(previousMenu);
            previousMenu = null;
        }
    }

    public void products() {
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
    }

    public void logout() throws Exception {
        if (Session.getOnlineAccount() == null) {
            throw new Exception("You are not logged in");
        }
        Session.setOnlineAccount(null);
        MenuHandler.getInstance().setCurrentMenu(View.loginRegisterPanel);
        MenuHandler.getInstance().getCurrentMenu().setPreviousMenu(null);
        outputStream.writeUTF("logout");
        outputStream.flush();
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        View.mainMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
    }
}
