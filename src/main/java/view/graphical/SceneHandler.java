package view.graphical;

import controller.data.YaDataManager;
import controller.share.Menu;
import controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.commodity.Category;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Handler;

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
        stage.setResizable(false);
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        stage.setTitle(menu.getStageTitle());
        stage.show();
    }

}

