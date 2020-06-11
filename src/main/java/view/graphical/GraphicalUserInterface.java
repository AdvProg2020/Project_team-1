package view.graphical;

import controller.share.LoginRegisterMenu;
import controller.share.MenuHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Session;

public class GraphicalUserInterface extends Application {

    @Override
    public void start(Stage stage) {
        MenuHandler.getInstance().setCurrentMenu(new LoginRegisterMenu());
        Session.getSceneHandler().updateScene(stage);
    }
}
