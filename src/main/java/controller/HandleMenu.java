package controller;

import commands.Command;

public class HandleMenu {
    private static final HandleMenu handleMenu = new HandleMenu();

    private static CommandProcess menu;

    private HandleMenu() {
    }

    public static CommandProcess getMenu() {
        return menu;
    }

    public static void setMenu(CommandProcess menu) {
        HandleMenu.menu = menu;
    }

    public static HandleMenu getInstance() {
        return handleMenu;
    }
}
