package view.graphical;

import controller.share.Menu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    public void setScene(Stage stage, Menu menu) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(menu.getFxmlFileAddress()));
        stage.setScene(new Scene(root));
        stage.setTitle(menu.getStageTitle());
        stage.show();
    }
}
