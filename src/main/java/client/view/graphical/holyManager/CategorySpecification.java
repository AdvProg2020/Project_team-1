package client.view.graphical.holyManager;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import client.view.commandline.View;

import java.net.URL;
import java.util.ResourceBundle;

public class CategorySpecification implements Initializable {
    public AnchorPane pane;
    ListView<CheckBox> listView = new ListView<CheckBox>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(listView);
        for (common.model.commodity.CategorySpecification option : EditCategory.getCategory().getFieldOptions()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(option.getTitle());
            listView.getItems().add(checkBox);
        }
    }

    public void deleteCategorySpecification(ActionEvent actionEvent) {
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()){
                try {
                    View.manageCategoriesMenu.removeCategorySpecification(item.getText() , EditCategory.getCategory());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();

    }
}
