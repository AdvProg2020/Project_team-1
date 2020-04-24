package controller;

public class HandleMenu {
    private static final HandleMenu handleMenu = new HandleMenu();

    private static CommandProcess menu;

    public static CommandProcess getMenu() {
        return menu;
    }

    public static void setMenu(CommandProcess menu) {
        HandleMenu.menu = menu;
    }

    private HandleMenu() {}

    public static HandleMenu getInstance() {
        return handleMenu;
    }
}
