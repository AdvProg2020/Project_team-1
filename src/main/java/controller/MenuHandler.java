package controller;

public class MenuHandler {

    private static MenuHandler singletonInstance = new MenuHandler();

    private Menu currentMenu;

    private MenuHandler() {}

    public static MenuHandler getInstance() {
        return singletonInstance;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
