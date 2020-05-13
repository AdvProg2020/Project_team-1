package controller;

public class HandleMenu {
    old.CommandProcess commandProcess;

    private static final HandleMenu handleMenu = new HandleMenu();

    private static Menu menu;

    public static Menu getMenu() {
        return menu;
    }

    public static void setMenu(Menu menu) {
        HandleMenu.menu = menu;
    }

    public static HandleMenu getInstance() {
        return handleMenu;
    }
}
