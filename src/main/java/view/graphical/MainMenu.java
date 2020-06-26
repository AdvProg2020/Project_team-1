package view.graphical;

import controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import view.AudioPlayer;
import view.commandline.View;

public class MainMenu {
    public void products(ActionEvent actionEvent) {
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));

    }

    public void userPanel(ActionEvent actionEvent) {
        goToUserPanel(actionEvent);
    }

    public static void goToUserPanel(ActionEvent actionEvent) {
        if (Session.getOnlineAccount() == null) {
            View.loginRegisterMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        } else if (Session.getOnlineAccount() instanceof ManagerAccount) {
            View.managerMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        } else if (Session.getOnlineAccount() instanceof PersonalAccount) {
            View.customerMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.customerMenu);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        } else if (Session.getOnlineAccount() instanceof BusinessAccount) {
            View.resellerMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.resellerMenu);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        }
    }

    public void offs(ActionEvent actionEvent) {
        View.offMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.offMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void pause(ActionEvent actionEvent){
        AudioPlayer.mediaPlayer.pause();
    }

    public void play(ActionEvent actionEvent){
        AudioPlayer.mediaPlayer.play();
    }

}
