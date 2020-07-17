package client.view.graphical;

import server.dataManager.YaDataManager;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import common.model.commodity.Category;
import common.model.commodity.CategorySpecification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Categories implements Initializable {
    public TreeView<String> categoriesTreeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootTreeItem = new TreeItem<>("All categories");
        try {
            for (Category category : YaDataManager.getCategories()) {
                TreeItem<String> categoryTreeItem = new TreeItem<>(category.getName());
                for (CategorySpecification fieldOption : category.getFieldOptions()) {
                    categoryTreeItem.getChildren().add(new TreeItem<>(fieldOption.getTitle()));
                }
                rootTreeItem.getChildren().add(categoryTreeItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoriesTreeView.setRoot(rootTreeItem);
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
