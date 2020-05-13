package controller;

public class Menu {
    public CommandProcess commandProcess;
    protected Menu previousMenu;

    public Menu getPreviousMenu() {
        return previousMenu;
    }

    public void setPreviousMenu(Menu previousMenu) {
        this.previousMenu = previousMenu;
    }
}
