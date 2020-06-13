package view.graphical;

import controller.data.YaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.commodity.Category;
import model.commodity.CategorySpecification;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryDescription implements Initializable {

    public AnchorPane pane;

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
        int i = 0;
        int j = 0;
        for (Category category : categories) {
            Text text = new Text("Category name: " + category.getName());
            text.setStyle("-fx-font-weight: bold");
            text.setX(i);
            text.setY(j + 20);
            pane.getChildren().add(text);
            text = new Text("Category specification:");
            text.setX(i);
            text.setY(j + 50);
            pane.getChildren().add(text);
            int counter =  150;
            for (CategorySpecification fieldOption : category.getFieldOptions()) {
                Text text1 = new Text(fieldOption.getTitle());
                text1.setX(i + counter);
                text1.setY(j + 50);
                pane.getChildren().add(text1);
                counter+= 100;
            }
            j += 70;
        }
    }
}
