package controller;

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
}
