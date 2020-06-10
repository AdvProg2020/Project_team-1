package view.graphical;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    public void setScene(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/LoginRegister.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
