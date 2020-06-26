package view.graphical;

import controller.share.FilteringMenu;
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
import view.AudioPlayer;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProductsMenuLoad {
    public Pane getRoot() {
        return root;
    }
    private Pane root;
    private int count = 0;
    private int blockIncrement = 2;

    public void initializeProductsRoot(Stage stage) {
        root = new Pane();
        root.setStyle("-fx-background-image: url(bg.jpg); -fx-background-size: stretch");
        root.getStylesheets().add("fxml/Common.css");
        try {
            setPlayPauseButton(root);
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

    private void setPlayPauseButton(Pane pane) {

        Button pause = new Button("\u23F8");
        Button play = new Button("\u25B6");
        pane.getChildren().add(pause);
        pane.getChildren().add(play);
        pause.getStyleClass().add("normal-button");
        play.getStyleClass().add("normal-button-Green");
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AudioPlayer.mediaPlayer.pause();
            }
        });

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AudioPlayer.mediaPlayer.play();
            }
        });

        pause.relocate(900, 0);
        play.relocate(800, 0);

    }

    private void setSortMenuButton(Pane root) {
        MenuButton sort = new MenuButton("Sort");
        sort.getStyleClass().add("normal-button");
        sort.setLayoutX(150);
        sort.setMinHeight(30);
        sort.setMinWidth(100);
        sort.getItems().add(new MenuItem("Price"));
        sort(sort, 0, "Price", root);
        sort.getItems().add(new MenuItem("Date"));
        sort(sort, 1, "Date", root);
        sort.getItems().add(new MenuItem("Score"));
        sort(sort, 2, "Average score", root);
        sort.getItems().add(new MenuItem("Number of visits"));
        sort(sort, 3, "Number of visits", root);
        root.getChildren().add(sort);
    }

    private void sort(MenuButton sort, int index, String sortName, Pane root) {
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

    public void deleteCommodities(Pane root) {
        for (int i = 0; i < root.getChildren().size(); i++) {
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
        setCategoryButtonOnAction(categories, root);
        root.getChildren().add(categories);
    }

    public void setCommodities(Pane root) throws Exception {
        int i = 0;
        int j = 100;
        ArrayList<Commodity> commodities = View.productsMenu.getProducts();
        int end;
        if (count + blockIncrement>= commodities.size())
            end = commodities.size();
        else end = count + blockIncrement;
        for (int p = count ; p < end; p++ ) {
            System.out.println(commodities.get(p).getImagePath());
            FileInputStream inputStream = new FileInputStream(commodities.get(p).getImagePath());
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            int finalP = p;
            Commodity tmp = commodities.get(finalP);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    View.commodityMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
                    View.commodityMenu.setCommodity(tmp);
                    View.digestMenu.setCommodity(tmp);
                    View.commentsMenu.setCommodity(tmp);
                    changeMenuToProductMenu(mouseEvent, tmp);
                }
            });
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            imageView.setLayoutX(i);
            imageView.setLayoutY(j);
            root.getChildren().add(imageView);
            Label name = createLabel(i + 300, j, "Name", commodities.get(p).getName(), false);
            Label price = createLabel(i + 300, j + 50, "Price", String.valueOf(commodities.get(p).getPrice()), false);
            Label score = createLabel(i + 300, j + 100, "Score", String.valueOf(commodities.get(p).getAverageScore()), false);
            if (commodities.get(p).getInventory() == 0) {
                Label inventory = createLabel(i + 300, j + 150, "Inventory", "0", true);
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
        setLogoutButton(root);
        setBackButton(root);
        setUserPanelButton();
        setNextButton();
        setBackButton();
    }

    private void setUserPanelButton() {
        Button userPanel = new Button("User panel");
        userPanel.getStyleClass().add("forward-button");
        userPanel.setLayoutY(root.getChildren().get(root.getChildren().size() - 1).getLayoutY() + 100);
        userPanel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                MainMenu.goToUserPanel(actionEvent);
            }
        });
        root.getChildren().add(userPanel);
    }

    private void setLogoutButton(Pane root) {
        Button logout = new Button("Logout");
        logout.getStyleClass().add("logout-button");
        logout.setLayoutX(800);
        logout.setLayoutY(root.getChildren().get(root.getChildren().size() - 1).getLayoutY() + 250);
        if (Session.getOnlineAccount() == null)
            logout.setDisable(true);
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    try {
                        MenuHandler.getInstance().getCurrentMenu().logout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        root.getChildren().add(logout);
    }

    private void changeMenuToProductMenu(MouseEvent mouseEvent, Commodity commodity) {
        commodity.setNumberOfVisits(commodity.getNumberOfVisits() + 1);
        MenuHandler.getInstance().setCurrentMenu(View.commodityMenu);
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public Label createLabel(int i, int j, String labelName, String description, boolean color) {
        Label label = new Label(labelName + ": " + description);
        label.getStyleClass().add("hint-label");
        label.setLayoutX(i);
        label.setLayoutY(j);
        if (color)
            label.setTextFill(Color.RED);
        return label;
    }

    private void setFilterMenuButton(Pane root) {
        MenuButton filter = new MenuButton("Filter");
        filter.getStyleClass().add("normal-button");
        filter.setMinWidth(100);
        filter.setMinHeight(30);
        filter.setLayoutX(270);
        filter.setLayoutY(0);
        filter.getItems().add(new MenuItem("Filter by name"));
        filter(filter.getItems().get(0), root, "FilterByName");
        filter.getItems().add(new MenuItem("Filter by category"));
        filter(filter.getItems().get(1), root, "FilterByCategory");
        javafx.scene.control.Menu filterByCategorySpecification = new javafx.scene.control.Menu("Filter by category specification");
        filterByCategorySpecification.getItems().add(new MenuItem("Numerical field"));
        filter(filterByCategorySpecification.getItems().get(0), root, "FBCSNF");
        filterByCategorySpecification.getItems().add(new MenuItem("Optional field"));
        filter(filterByCategorySpecification.getItems().get(1), root, "FBCSOF");
        filter.getItems().add(filterByCategorySpecification);
        filter.getItems().add(new MenuItem("Remove filter"));
        filter(filter.getItems().get(3), root, "DisableFilter");
        root.getChildren().add(filter);
    }

    private void filter(MenuItem filer, Pane root, String fileName) {
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

    private void setBackButton(Pane root) {
        Button back = new Button("Back");
        back.getStyleClass().add("back-button");
        back.setLayoutX(800);
        back.setLayoutY(root.getChildren().get(root.getChildren().size() - 1).getLayoutY() + 150);
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

    private void setCategoryButtonOnAction(Button categoryMenuButton, Pane root) {
        categoryMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent parent = null;
                Popup popupMenu = new Popup();
                try {
                    parent = FXMLLoader.load(getClass().getResource("../../fxml/" + "CategoriesPopup" + ".fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                popupMenu.getContent().add(parent);
                popupMenu.show((root.getScene().getWindow()));
            }
        });
    }

    private void setNextButton(){
        Button next = new Button("Next page");
        next.getStyleClass().add("normal-button");
        next.relocate(450 ,  root.getChildren().get(root.getChildren().size() - 1).getLayoutY() + 100);
        root.getChildren().add(next);
        if (count + blockIncrement >= FilteringMenu.getFilteredCommodities().size())
            next.setDisable(true);
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                count += blockIncrement;
                try {
                    deleteCommodities(root);
                    setCommodities(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setBackButton(){
        Button back = new Button("Back");
        back.getStyleClass().add("normal-button");
        back.relocate(350 ,  root.getChildren().get(root.getChildren().size() - 2).getLayoutY() + 100);
        root.getChildren().add(back);
        if (count - blockIncrement < 0 )
            back.setDisable(true);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                count -= blockIncrement;
                try {
                    deleteCommodities(root);
                    setCommodities(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

