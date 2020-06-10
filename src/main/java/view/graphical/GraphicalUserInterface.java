package view.graphical;

import controller.share.LoginRegisterMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class GraphicalUserInterface extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new SceneHandler().setScene(stage, new LoginRegisterMenu());
    }
}
