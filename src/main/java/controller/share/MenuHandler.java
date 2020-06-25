package controller.share;

import view.AudioPlayer;

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
        AudioPlayer.mediaPlayer.stop();
        if (AudioPlayer.count != 8)
            AudioPlayer.count++;
        else AudioPlayer.count = 1;
        AudioPlayer.music();
        this.currentMenu = currentMenu;
    }
}
