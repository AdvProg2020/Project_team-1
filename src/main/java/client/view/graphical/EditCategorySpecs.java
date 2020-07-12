package client.view.graphical;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import common.model.commodity.CategorySpecification;
import common.model.field.Field;
import common.model.field.NumericalField;
import common.model.field.OptionalField;

import java.util.ArrayList;

public class EditCategorySpecs {
    public VBox dataVBox;
    private ShowEditProduct showEditProduct;
    ArrayList<Field> specs;

    public void setShowEditProduct(ShowEditProduct showEditProduct) {
        this.showEditProduct = showEditProduct;
    }

    public void initVBox(ArrayList<Field> specs, ArrayList<CategorySpecification> categorySpecifications) {
        this.specs = specs;
        dataVBox.setSpacing(10);
        dataVBox.setAlignment(Pos.CENTER);
        int counter = 0;
        for (CategorySpecification fieldOption : categorySpecifications) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.setOpaqueInsets(new Insets(10, 10, 10, 10));
            Label label = new Label(fieldOption.getTitle() + " :");
            label.getStyleClass().add("hint-label");
            hBox.getChildren().add(label);
            int finalCounter = counter;
            if (fieldOption.getOptions() == null) {
                TextField textField = new TextField();
                textField.setText(((NumericalField) specs.get(counter)).getValue().toString());
                textField.textProperty().addListener((observableValue, s, t1) ->
                        ((NumericalField) specs.get(finalCounter)).setValue(Integer.parseInt(t1)));
                hBox.getChildren().add(textField);
                HBox.setHgrow(textField, Priority.ALWAYS);
            } else {
                ComboBox<String> options = new ComboBox<>();
                ObservableList<String> observableList
                        = FXCollections.observableList(new ArrayList<>(fieldOption.getOptions()));
                options.setItems(observableList);
                options.getStyleClass().add("text-field");
                options.getSelectionModel().select(((OptionalField) specs.get(counter)).getValue());
                options.valueProperty().addListener(
                        (observableValue, s, t1) -> ((OptionalField) specs.get(finalCounter)).setValue(t1));
                hBox.getChildren().add(options);
                HBox.setHgrow(options, Priority.ALWAYS);
            }
            dataVBox.getChildren().add(hBox);
            ++counter;
        }
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onSaveClick(MouseEvent mouseEvent) {
        showEditProduct.setCategorySpecification(specs);
        onCloseClick(mouseEvent);
    }
}
