package controller;

import model.Session;
import view.View;

public class Menu {

    public CommandProcess commandProcess;

    protected Menu previousMenu;

    public Menu() {
        commandProcess = null;
        previousMenu = null;
    }

    public void setPreviousMenu(Menu previousMenu) {
        this.previousMenu = previousMenu;
    }

    public void goToPreviousMenu() {
        MenuHandler.getInstance().setCurrentMenu(previousMenu);
        previousMenu = null;
    }

    public void products(){
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
    }

    public void logout() {
        Session.setOnlineAccount(null);
        MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
        MenuHandler.getInstance().getCurrentMenu().setPreviousMenu(null);
    }

    public void commandProcess(String command) throws Exception {
        commandProcess.commandProcessor(command);
    }
}
