package client.view.graphical;

import server.dataManager.YaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import common.model.commodity.Category;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryDescription implements Initializable {

    public AnchorPane pane;
    public ListView<String> listView = new ListView<>();

    public void Ok(ActionEvent actionEvent) {
        pane.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Category> categories = null;
        try {
            categories = YaDataManager.getCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Category category : categories) {
            listView.getItems().add(category.toString());
        }
        pane.getChildren().add(listView);
    }
}
