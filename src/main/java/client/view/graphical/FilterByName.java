package client.view.graphical;

import server.controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import client.view.commandline.View;

public class FilterByName {
    public TextField name;

    public void filterByName(ActionEvent actionEvent) {
        String nameString = name.getText();
        try {
            View.filteringMenu.filter(new common.model.filter.FilterByName("Filter by name " + nameString, nameString));
            if (MenuHandler.getInstance().getCurrentMenu().getFxmlFileAddress().equals("../../Products.fxml")) {
                SceneHandler.getProductsMenuLoad().deleteCommodities(SceneHandler.getProductsMenuLoad().getRoot());
                SceneHandler.getProductsMenuLoad().setCommodities(SceneHandler.getProductsMenuLoad().getRoot());
            }
            if (MenuHandler.getInstance().getCurrentMenu().getFxmlFileAddress().equals("../../../fxml/OffMenu.fxml")) {
                SceneHandler.getOffMenu().deleteCommodities(SceneHandler.getOffMenu().getRoot());
                SceneHandler.getOffMenu().setCommodities(SceneHandler.getOffMenu().getRoot());
            }
            name.getScene().getWindow().hide();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
