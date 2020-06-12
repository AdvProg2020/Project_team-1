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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.commodity.Category;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;

public class SceneHandler {

    public void updateScene(Stage stage) {
        Menu menu = MenuHandler.getInstance().getCurrentMenu();
        if (menu.getFxmlFileAddress().equals("../../Products.fxml"))
            initializeProductsRoot(stage);
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
        stage.setTitle(menu.getStageTitle());
        stage.show();
    }

    public void initializeProductsRoot(Stage stage) {
        Pane root = new Pane();
        try {
            setSortMenuButton(root);
            setCategoryMenuButton(root);
            setCommodities(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        stage.setScene(new Scene(scrollPane));
    }

    private void setSortMenuButton(Pane root) {
        MenuButton sort = new MenuButton("Sort");
        sort.setLayoutX(100);
        sort.setMinHeight(30);
        sort.setMinWidth(100);
        sort.getItems().add(new MenuItem("Price"));
        sort(sort , 0 , "Price" , root);
        sort.getItems().add(new MenuItem("Date"));
        sort(sort , 1 , "Date" ,root);
        sort.getItems().add(new MenuItem("Score"));
        sort(sort , 2 , "Average score" , root);
        sort.getItems().add(new MenuItem("Number of visits"));
        sort(sort , 3 , "Number of visits" , root);
        root.getChildren().add(sort);
    }

    private void sort(MenuButton sort , int index , String sortName , Pane root) {
        sort.getItems().get(index).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    View.sortingMenu.sort(sortName);
                    for (int i = 0 ; i < root.getChildren().size() ; i++) {
                        if (root.getChildren().get(i).getLayoutY() > 40) {
                            root.getChildren().remove(root.getChildren().get(i));
                            i--;
                        }
                    }
                    try {
                        setCommodities(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void setCategoryMenuButton(Pane root) throws IOException {
        MenuButton categories = new MenuButton("Categories");
        categories.setMinHeight(30);
        categories.setMinWidth(100);
        for (Category category : YaDataManager.getCategories()) {
            categories.getItems().add(new MenuItem(category.getName()));
        }
        root.getChildren().add(categories);
    }

    private void setCommodities(Pane root) throws Exception {
        int i = 0;
        int j = 100;
        for (Commodity commodity : View.productsMenu.getProducts()){
            FileInputStream inputStream = new FileInputStream(commodity.getImagePath());
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            imageView.setLayoutX(i);
            imageView.setLayoutY(j);
            root.getChildren().add(imageView);
            Label name = createLabel(i + 300, j, "Name", commodity.getName());
            Label price = createLabel(i + 300, j + 50, "Price", String.valueOf(commodity.getPrice()));
            Label score = createLabel(i + 300, j + 100, "Score", String.valueOf(commodity.getAverageScore()));
            root.getChildren().add(score);
            root.getChildren().add(name);
            root.getChildren().add(price);
            if (i >= 500) {
                i = -500;
                j += 300;
            }
            i += 500;
        }
    }

    public Label createLabel(int i, int j, String labelName, String description) {
        Label label = new Label(labelName + ": " + description);
        label.setLayoutX(i);
        label.setLayoutY(j);
        return label;
    }
}
