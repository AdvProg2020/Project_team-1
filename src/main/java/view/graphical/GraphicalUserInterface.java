package view.graphical;

import javafx.application.Application;
import javafx.stage.Stage;

public class GraphicalUserInterface extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new SceneHandler().setScene(stage);
    }
}
