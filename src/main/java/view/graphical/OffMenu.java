package view.graphical;

import controller.data.YaDataManager;
import controller.share.MenuHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.commodity.Commodity;
import model.commodity.Off;
import view.commandline.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OffMenu implements Initializable {

    public AnchorPane root;
    private int count = 0;
    private int blockIncrement = 2;

    public void mainMenu(ActionEvent actionEvent) {
        View.mainMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.mainMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFilterMenuButton(root);
        try {
            setCommodities(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFilterMenuButton(Pane root) {
        MenuButton filter = new MenuButton("Filter");
        filter.getStyleClass().add("normal-button");
        filter.setMinWidth(100);
        filter.setMinHeight(30);
        filter.setLayoutX(0);
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

    public void setCommodities(Pane root) throws Exception {
        int i = 0;
        int j = 100;
        ArrayList<Off> offs = YaDataManager.getOffs();
        for (Off off : offs) {
            ArrayList<Commodity> commodities = off.getCommodities();
            if (off.isActive()) {
                for (int p = 0; p < commodities.size(); p++) {
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
                    Label start_date = createLabel(i + 300, j + 150 , "Start date" , off.getStartTime().toString(),false );
                    Label finish_date = createLabel(i + 300, j + 200 , "Finish date" , off.getEndTime().toString(),false );
                    Label remainingTime = createLabel(i + 300 , j + 250 , "Remaining time" , String.valueOf(off.getEndTime().getTime() - off.getStartTime().getTime()),false );
                    Label discount = createLabel(i+300 , j + 300 , "Discount" , String.valueOf(off.getDiscountPercent()) , false);
                    if (commodities.get(p).getInventory() == 0) {
                        Label inventory = createLabel(i + 300, j + 150, "Inventory", "0", true);
                        root.getChildren().add(inventory);
                    }
                    root.getChildren().add(score);
                    root.getChildren().add(name);
                    root.getChildren().add(price);
                    root.getChildren().add(start_date);
                    root.getChildren().add(finish_date);
                    root.getChildren().add(remainingTime);
                    root.getChildren().add(discount);
                    if (i >= 500) {
                        i = -500;
                        j += 500;
                    }
                    i += 500;
                }
            }
        }
        setLogoutButton(root);
        setBackButton(root);
        setUserPanelButton();
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

    private void changeMenuToProductMenu(MouseEvent mouseEvent, Commodity commodity) {
        commodity.setNumberOfVisits(commodity.getNumberOfVisits() + 1);
        MenuHandler.getInstance().setCurrentMenu(View.commodityMenu);
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
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

    private void setBackButton(Pane root) {
        Button back = new Button("Back");
        back.getStyleClass().add("back-button");
        back.setLayoutX(800);
        back.setLayoutY(root.getChildren().get(root.getChildren().size() - 1).getLayoutY() + 150);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    View.offMenu.goToPreviousMenu();
                    Session.getSceneHandler().updateScene(((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        root.getChildren().add(back);
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


}
