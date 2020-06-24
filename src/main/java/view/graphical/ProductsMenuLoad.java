package view.graphical;

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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.commodity.Commodity;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;

public class ProductsMenuLoad {

    public void initializeProductsRoot(Stage stage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-image: url(bg.jpg); -fx-background-size: stretch");
        root.getStylesheets().add("fxml/Common.css");
        try {
            setSortMenuButton(root);
            setCategoryMenuButton(root);
            setFilterMenuButton(root);
            setCommodities(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        stage.setScene(new Scene(scrollPane));
    }

    private void setSortMenuButton(Pane root) {
        MenuButton sort = new MenuButton("Sort");
        sort.getStyleClass().add("normal-button");
        sort.setLayoutX(150);
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
                    deleteCommodities(root);
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

    private void deleteCommodities(Pane root) {
        for (int i = 0 ; i < root.getChildren().size() ; i++) {
            if (root.getChildren().get(i).getLayoutY() > 40) {
                root.getChildren().remove(root.getChildren().get(i));
                i--;
            }
        }
    }

    private void setCategoryMenuButton(Pane root) throws IOException {
        Button categories = new Button("Categories");
        categories.setMinHeight(30);
        categories.setMinWidth(100);
        categories.getStyleClass().add("normal-button");
        setCategoryButtonOnAction(categories , root);
        root.getChildren().add(categories);
    }

    private void setCommodities(Pane root) throws Exception {
        int i = 0;
        int j = 100;
        for (Commodity commodity : View.productsMenu.getProducts()){
            FileInputStream inputStream = new FileInputStream(commodity.getImagePath());
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    changeMenuToProductMenu(mouseEvent, commodity);
                }
            });
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            imageView.setLayoutX(i);
            imageView.setLayoutY(j);
            root.getChildren().add(imageView);
            Label name = createLabel(i + 300, j, "Name", commodity.getName() , false);
            Label price = createLabel(i + 300, j + 50, "Price", String.valueOf(commodity.getPrice()),false);
            Label score = createLabel(i + 300, j + 100, "Score", String.valueOf(commodity.getAverageScore()),false);
            if (commodity.getInventory()==0){
                Label inventory = createLabel(i+300 , j + 150 , "Inventory" , "0"  , true);
                root.getChildren().add(inventory);
            }
            root.getChildren().add(score);
            root.getChildren().add(name);
            root.getChildren().add(price);
            if (i >= 500) {
                i = -500;
                j += 300;
            }
            i += 500;
        }
        setUpdateButton(root, j);
        setBackButton(root);
    }

    private void setUpdateButton(Pane root, int j) {
        Button update = new Button("Update");
        update.getStyleClass().add("normal-button");
        update.setLayoutX(800);
        update.setLayoutY(root.getChildren().get(root.getChildren().size()-1).getLayoutY()+250);
        update.setMinWidth(100);
        update.setMinHeight(100);
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    deleteCommodities(root);
                    setCommodities(root);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        root.getChildren().add(update);
    }

    private void changeMenuToProductMenu(MouseEvent mouseEvent, Commodity commodity) {
        commodity.setNumberOfVisits(commodity.getNumberOfVisits() + 1);
        MenuHandler.getInstance().setCurrentMenu(View.commodityMenu);
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public Label createLabel(int i, int j, String labelName, String description , boolean color) {
        Label label = new Label(labelName + ": " + description);
        label.getStyleClass().add("hint-label");
        label.setLayoutX(i);
        label.setLayoutY(j);
        if (color)
            label.setTextFill(Color.RED);
        return label;
    }

    private void setFilterMenuButton(Pane root){
        MenuButton filter = new MenuButton("Filter");
        filter.getStyleClass().add("normal-button");
        filter.setMinWidth(100);
        filter.setMinHeight(30);
        filter.setLayoutX(270);
        filter.setLayoutY(0);
        filter.getItems().add(new MenuItem("Filter by name"));
        filter(filter.getItems().get(0),root , "FilterByName");
        filter.getItems().add(new MenuItem("Filter by category"));
        filter(filter.getItems().get(1),root , "FilterByCategory");
        javafx.scene.control.Menu filterByCategorySpecification = new javafx.scene.control.Menu("Filter by category specification");
        filterByCategorySpecification.getItems().add(new MenuItem("Numerical field"));
        filter(filterByCategorySpecification.getItems().get(0),root , "FBCSNF");
        filterByCategorySpecification.getItems().add(new MenuItem("Optional field"));
        filter(filterByCategorySpecification.getItems().get(1),root , "FBCSOF");
        filter.getItems().add(filterByCategorySpecification);
        filter.getItems().add(new MenuItem("Remove filter"));
        filter(filter.getItems().get(3) , root , "DisableFilter");
        root.getChildren().add(filter);
    }

    private void filter(MenuItem filer,Pane root , String fileName){
        filer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent parent = null;
                Popup popupMenu = new Popup();
                try {
                    parent = FXMLLoader.load(getClass().getResource("../../fxml/" + fileName + ".fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                popupMenu.getContent().add(parent);
                popupMenu.show((root.getScene().getWindow()));
            }
        });
    }

    private void setBackButton(Pane root){
        Button back = new Button("Back");
        back.getStyleClass().add("back-button");
        back.setLayoutX(800);
        back.setLayoutY(root.getChildren().get(root.getChildren().size()-1).getLayoutY()+150);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    View.productsMenu.goToPreviousMenu();
                    Session.getSceneHandler().updateScene(((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        root.getChildren().add(back);
    }
    private void setCategoryButtonOnAction(Button categoryMenuButton , Pane root){
        categoryMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent parent = null;
                Popup popupMenu = new Popup();
                try {
                    parent = FXMLLoader.load(getClass().getResource("../../fxml/" + "Category" + ".fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                popupMenu.getContent().add(parent);
                popupMenu.show((root.getScene().getWindow()));
            }
        });
    }
}
