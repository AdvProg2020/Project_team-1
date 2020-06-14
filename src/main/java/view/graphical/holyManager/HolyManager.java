package view.graphical.holyManager;

import controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Session;
import view.commandline.View;

import java.net.URL;
import java.util.ResourceBundle;

public class HolyManager{


    public void viewPersonalInfo(ActionEvent actionEvent) {
        MenuHandler.getInstance().setCurrentMenu(View.viewPersonalInfoMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

}
