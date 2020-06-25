package view.graphical;

import controller.share.FilteringMenu;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import model.filter.Filter;
import view.commandline.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisableFilter implements Initializable {
    

    public Button disableFilterButton;
    public AnchorPane pane;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    public void disableFilter(ActionEvent actionEvent) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()){
                try {
                    View.filteringMenu.disableFilter(checkBox.getText());
                    SceneHandler.getProductsMenuLoad().deleteCommodities(SceneHandler.getProductsMenuLoad().getRoot());
                    SceneHandler.getProductsMenuLoad().setCommodities(SceneHandler.getProductsMenuLoad().getRoot());

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        pane.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int counter = 0;
        for (Filter currentFilter : FilteringMenu.getCurrentFilters()) {
            CheckBox filter = new CheckBox(currentFilter.getFilterName());
            filter.setLayoutY(disableFilterButton.getLayoutY() - 200 + counter);
            filter.setLayoutX(disableFilterButton.getLayoutX() - 300);
            checkBoxes.add(filter);
            pane.getChildren().add(filter);
            counter+=50;
        }
    }
}
