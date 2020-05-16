package controller;

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
        HandleMenu.setMenu(View.productsMenu);
    }

    public void commandProcess(String command) throws Exception {
        commandProcess.commandProcessor(command);
    }
}
