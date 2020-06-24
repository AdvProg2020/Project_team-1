package view.graphical;

import controller.data.YaDataManager;
import controller.share.MenuHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Session;
import view.AudioPlayer;
import view.commandline.View;

import java.io.IOException;

public class GraphicalUserInterface extends Application {

    @Override
    public void start(Stage stage){
        MenuHandler.getInstance().setCurrentMenu(View.loginRegisterMenu);
        Session.getSceneHandler().updateScene(stage);
    }
}
