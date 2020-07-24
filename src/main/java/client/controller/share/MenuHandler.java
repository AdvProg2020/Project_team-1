package client.controller.share;

public class MenuHandler {
    private static final MenuHandler singletonInstance = new MenuHandler();

    private Menu currentMenu;

    private MenuHandler() {
    }

    public static MenuHandler getInstance() {
        return singletonInstance;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
/*      if (!AudioPlayer.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
          AudioPlayer.mediaPlayer.stop();
          if (AudioPlayer.count != 8)
              AudioPlayer.count++;
          else AudioPlayer.count = 1;
          AudioPlayer.music();
      }*/
        this.currentMenu = currentMenu;
    }
}
