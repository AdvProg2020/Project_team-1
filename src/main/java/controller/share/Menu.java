package controller.share;

import model.Session;
import view.AudioPlayer;
import view.commandline.View;

public class Menu {

    public CommandProcess commandProcess;

    protected Menu previousMenu;

    protected String fxmlFileAddress;

    public String getFxmlFileAddress() {
        return fxmlFileAddress;
    }

    public String getStageTitle() {
        return stageTitle;
    }

    protected String stageTitle;

    public Menu() {
        commandProcess = null;
        previousMenu = null;
    }

    public void setPreviousMenu(Menu previousMenu) {
        this.previousMenu = previousMenu;
    }

    public void goToPreviousMenu() {
        AudioPlayer.audioClip.stop();
        if (AudioPlayer.count != 8)
            AudioPlayer.count++;
        else AudioPlayer.count = 1;
        AudioPlayer.music();
        if (previousMenu != null) {
            MenuHandler.getInstance().setCurrentMenu(previousMenu);
            previousMenu = null;
        }
    }

    public void setLoginAndRegisterMenu(){
        MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
    }

    public void products(){
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
    }

    public void logout() throws Exception {
        if (Session.getOnlineAccount() == null) {
            throw new Exception("You are not logged in");
        }
        Session.setOnlineAccount(null);
        MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
        MenuHandler.getInstance().getCurrentMenu().setPreviousMenu(null);
    }

    public void commandProcess(String command) throws Exception {
        commandProcess.commandProcessor(command);
    }
}
