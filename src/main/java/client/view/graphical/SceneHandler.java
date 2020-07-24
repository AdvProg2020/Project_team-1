package client.view.graphical;

import client.controller.share.Menu;
import client.controller.share.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    public static ProductsMenuLoad getProductsMenuLoad() {
        return productsMenuLoad;
    }

    public static OffMenu getOffMenu(){
        return offMenu;
    }

    private static final ProductsMenuLoad productsMenuLoad = new ProductsMenuLoad();
    private static final OffMenu offMenu = new OffMenu();
    static {
        //MenuHandler.getInstance().setCurrentMenu(View.managerMenu);
    }
    public void updateScene(Stage stage) {
        System.out.println("MAmaml");

        Menu menu = MenuHandler.getInstance().getCurrentMenu();
         if (menu.getFxmlFileAddress().equals("../../Products.fxml"))
            productsMenuLoad.initializeProductsRoot(stage);
        else if (menu.getFxmlFileAddress().equals("../../../fxml/OffMenu.fxml"))
            offMenu.initialize(stage);
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

        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        stage.setTitle(menu.getStageTitle());
        stage.show();
    }

}

