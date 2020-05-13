package old;

public class HandleMenu {
    CommandProcess commandProcess;

    private static final HandleMenu handleMenu = new HandleMenu();

    private static CommandProcess menu;

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
