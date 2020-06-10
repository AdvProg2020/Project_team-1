package view.graphical;

import controller.share.Menu;
import controller.share.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    public void updateScene(Stage stage) {
        Menu menu = MenuHandler.getInstance().getCurrentMenu();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(menu.getFxmlFileAddress()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        stage.setScene(new Scene(root));
        stage.setTitle(menu.getStageTitle());
        stage.show();
    }
}
