package view.graphical;

import controller.share.Menu;
import controller.share.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.commandline.View;

import java.io.IOException;

public class SceneHandler {

    private final ProductsMenuLoad productsMenuLoad = new ProductsMenuLoad();
    static {
        //MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
    }
    public void updateScene(Stage stage) {
        Menu menu = MenuHandler.getInstance().getCurrentMenu();

        if (menu.getFxmlFileAddress().equals("../../Products.fxml"))
            productsMenuLoad.initializeProductsRoot(stage);
        else {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(menu.getFxmlFileAddress()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert root != null;
            stage.setScene(new Scene(root));
        }
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setTitle(menu.getStageTitle());
        stage.show();
    }

}

