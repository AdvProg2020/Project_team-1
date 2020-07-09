package client.view.graphical;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import common.model.commodity.Category;
import common.model.commodity.CategorySpecification;
import common.model.field.Field;
import common.model.field.NumericalField;
import common.model.field.OptionalField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GetCategorySpecs implements Initializable {
    private final ArrayList<Node> values = new ArrayList<>();
    private AddProduct addProduct;
    public VBox dataVBox;
    private Category category;

    public void setAddProduct(AddProduct addProduct) {
        this.addProduct = addProduct;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void populateVBox(Category category) {
        this.category = category;
        dataVBox.setSpacing(10);
        dataVBox.setAlignment(Pos.CENTER);
        for (CategorySpecification fieldOption : category.getFieldOptions()) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.setOpaqueInsets(new Insets(10, 10, 10, 10));
            Label label = new Label(fieldOption.getTitle() + " :");
            label.getStyleClass().add("hint-label");
            hBox.getChildren().add(label);
            if (fieldOption.getOptions() == null) {
                TextField textField = new TextField();
                hBox.getChildren().add(textField);
                HBox.setHgrow(textField, Priority.ALWAYS);
                values.add(textField);
            } else {
                ComboBox<String> options = new ComboBox<>();
                ObservableList<String> observableList
                        = FXCollections.observableList(new ArrayList<>(fieldOption.getOptions()));
                options.setItems(observableList);
                options.getStyleClass().add("text-field");
                hBox.getChildren().add(options);
                HBox.setHgrow(options, Priority.ALWAYS);
                values.add(options);
            }
            dataVBox.getChildren().add(hBox);
        }
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onConfirmClick(MouseEvent mouseEvent) {
        ArrayList<Field> productCategorySpecification = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Field field;
            CategorySpecification categorySpecification = category.getFieldOptions().get(i);
            if (categorySpecification.getOptions() == null) {
                field = new NumericalField(categorySpecification.getTitle(),
                        Integer.parseInt(((TextField) values.get(i)).getText()));
            } else {
                field = new OptionalField(categorySpecification.getTitle(),
                        ((ComboBox<String>) values.get(i)).getValue());
            }
            productCategorySpecification.add(field);
        }
        addProduct.setProductCategorySpecification(productCategorySpecification);
        onCloseClick(mouseEvent);
    }
}
