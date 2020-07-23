package client.view.graphical.holyManager;

import client.Session;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import common.model.commodity.CategorySpecification;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class AddCategory extends HolyManager implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private static Stage stage;
    public TextField categoryName;
    public TextField title;
    public CheckBox isOptional;
    public Button addOptionId;
    public TextField option;
    public Label information;
    private HashSet<String> options = null;
    private ArrayList<CategorySpecification> categorySpecifications = new ArrayList<>();

    public static void setStage(Stage stageA) {
        stage = stageA;
    }

    public void addCommodity(ActionEvent actionEvent) {
        AddCommoditiesToCategory.setCommodities(new ArrayList<>());
        newPopup(actionEvent, "../../../../fxml/holyManager/AddCommoditiesToCategory.fxml");
    }

    public void addCategorySpecification(ActionEvent actionEvent) {
        if (!View.manageCategoriesMenu.checkCategorySpecificationTitle(categorySpecifications, title.getText())) {
            setInformationLabel(Color.RED, "Title is unavailable.");
            return;
        }

        if (!title.getText().equals("") && (options == null || options.size() != 0)) {
            categorySpecifications.add(View.manageCategoriesMenu.createCategorySpecification(title.getText(), options));
            setInformationLabel(Color.GREEN, "Category specification successfully added");
            title.setText("");
        } else {
            setInformationLabel(Color.RED, "Invalid entry");
        }
    }

    public void addCategory(ActionEvent actionEvent) {
        try {
            outputStream.writeUTF("is category name valid? " + categoryName.getText());
            outputStream.flush();
            if (inputStream.readUTF().equals("yes")) {
                setInformationLabel(Color.RED, "Category name is not available.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!categoryName.getText().equals("")) {
            try {
                View.manageCategoriesMenu.addCategory(categoryName.getText(), AddCommoditiesToCategory.getCommodities(),
                        categorySpecifications);
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                Session.getSceneHandler().updateScene(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else setInformationLabel(Color.RED, "Invalid category name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isOptional.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (isOptional.isSelected()) {
                    options = new HashSet<String>();
                } else options = null;
                option.setVisible(!option.isVisible());
                addOptionId.setVisible(!addOptionId.isVisible());
            }
        });
    }

    public void addOption(ActionEvent actionEvent) {
        if (option != null && !option.equals("")) {
            options.add(option.getText());
            setInformationLabel(Color.GREEN, "Option successfully added");
            option.setText("");
        } else {
            setInformationLabel(Color.RED, "Invalid entry.");
        }
    }

    public void setInformationLabel(Color color, String text) {
        information.setTextFill(color);
        information.setText(text);
    }
}
