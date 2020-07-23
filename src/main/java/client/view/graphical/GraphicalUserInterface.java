package client.view.graphical;

import server.controller.share.MenuHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import client.Session;
import client.view.commandline.View;

public class GraphicalUserInterface extends Application {

    @Override
    public void start(Stage stage){
        System.out.println("MAmaml");
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
        System.out.println("MAmaml");
        Session.getSceneHandler().updateScene(stage);

    }
}
