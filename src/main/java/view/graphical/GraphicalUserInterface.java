package view.graphical;

import controller.share.MenuHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Session;
import view.commandline.View;

public class GraphicalUserInterface extends Application {

    @Override
    public void start(Stage stage){
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
        Session.getSceneHandler().updateScene(stage);
    }
}
