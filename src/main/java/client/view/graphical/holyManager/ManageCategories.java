package client.view.graphical.holyManager;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Category;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class ManageCategories extends HolyManager implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public Label errorLabel;
    public AnchorPane pane;
    private ListView<CheckBox> listView = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(listView);
        setUpPane();
    }

    protected void setUpPane() {
        listView.getItems().removeAll(listView.getItems());
        try {
            outputStream.writeUTF("send categories");
            outputStream.flush();
            ArrayList<Category> categories = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Category>>() {
            }.getType());
            for (Category category : categories) {
                listView.getItems().add(new CheckBox(category.toString()));
                listView.getItems().get(listView.getItems().size() - 1).setId(category.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.relocate(300, 0);
        listView.setPrefHeight(300);
        listView.setPrefWidth(500);
    }


    private boolean checkError() {
        int counter = 0;
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected())
                counter++;
            if (counter == 2) {
                error();
                return true;
            }
        }
        if (counter == 0) {
            error();
            return true;
        }
        return false;
    }

    public void error() {
        errorLabel.setText("Select a category.");
        errorLabel.setVisible(true);
    }

    public void addCategory(ActionEvent actionEvent) {
        AddCategory.setStage((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        newPopup(actionEvent, "../../../../fxml/HolyManager/AddCategory.fxml");
    }

    public void deleteCategory(ActionEvent actionEvent) {
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()) {
                try {
                    outputStream.writeUTF("delete category " + item.getId());
                    outputStream.flush();
                    inputStream.readUTF();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        setUpPane();
    }

    public void editCategory(ActionEvent actionEvent) {
        EditDiscountCode.setStage((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        errorLabel.setVisible(false);
        if (checkError()) return;
        setCategory();
        EditCategory.setStage((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
        newPopup(actionEvent, "../../../../fxml/holyManager/EditCategory.fxml");
    }

    public void setCategory() {
        for (CheckBox item : listView.getItems()) {
            if (item.isSelected()) {
                try {
                    outputStream.writeUTF("name of category is " + item.getId());
                    outputStream.flush();
                    Category category = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Category>() {
                    }.getType());
                    EditCategory.setCategory(category);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

