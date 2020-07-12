package client.view.graphical;

import server.controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import client.view.commandline.View;

public class FilterByCategory {
    public TextField categoryNameTextField;

    public void filterByCategory(ActionEvent actionEvent) {
        String categoryName = categoryNameTextField.getText();
        try {
            View.filteringMenu.filter(new common.model.filter.FilterByCategory("Filter by category "+categoryName , View.manageCategoryMenu.getCategory(categoryName)));
            if (MenuHandler.getInstance().getCurrentMenu().getFxmlFileAddress().equals("../../Products.fxml")) {
                SceneHandler.getProductsMenuLoad().deleteCommodities(SceneHandler.getProductsMenuLoad().getRoot());
                SceneHandler.getProductsMenuLoad().setCommodities(SceneHandler.getProductsMenuLoad().getRoot());
            }
            if (MenuHandler.getInstance().getCurrentMenu().getFxmlFileAddress().equals("../../../fxml/OffMenu.fxml")) {
                SceneHandler.getOffMenu().deleteCommodities(SceneHandler.getOffMenu().getRoot());
                SceneHandler.getOffMenu().setCommodities(SceneHandler.getOffMenu().getRoot());
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid category name");
            alert.setContentText("Invalid category name");
            alert.show();
            try {
                View.filteringMenu.disableFilter("Filter by category "+categoryName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        categoryNameTextField.getScene().getWindow().hide();
    }
}
