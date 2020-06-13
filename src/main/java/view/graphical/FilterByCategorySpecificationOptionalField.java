package view.graphical;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.commodity.Category;
import model.commodity.CategorySpecification;
import model.filter.NumericalFilter;
import model.filter.OptionalFilter;
import view.commandline.View;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterByCategorySpecificationOptionalField {
    public TextField categoryNameTextField;
    public TextField optionsTextField;
    public AnchorPane pane;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    private  String categoryName = categoryNameTextField.getText();
    private Category category;

    public void filterByOptionalField(ActionEvent actionEvent) {

        ArrayList<String> acceptableOptions = new ArrayList<String>(Arrays.asList(optionsTextField.getText().split(" ")));
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected())
                filter(categoryName, i, category, acceptableOptions);
        }
        categoryNameTextField.getScene().getWindow().hide();
    }

    private void filter(String categoryName, int correspondingField, Category category, ArrayList<String> acceptableOptions) {
        String filterName = "Filter by category specification " + categoryName + " " + correspondingField;

        try {
            View.filteringMenu.filter(new model.filter.FilterByCategory("Filter by category " + categoryName, category));
            View.filteringMenu.filter(new OptionalFilter(filterName, acceptableOptions, correspondingField));
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            try {
                View.filteringMenu.disableFilter(filterName);
                View.filteringMenu.disableFilter("Filter by category " + categoryName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showFields(ActionEvent actionEvent) {
        try {
            category = View.manageCategoryMenu.getCategory(categoryName);
            if (category == null)
                throw new Exception();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid category name");
            alert.setContentText("invalid category name");
            alert.show();
            categoryNameTextField.getScene().getWindow().hide();
            return;
        }
        int counter = 0;
        for (CategorySpecification fieldOption : category.getFieldOptions()) {
            if (fieldOption.getOptions() !=  null) {
                CheckBox fieldOptionCheckBox =  new CheckBox(fieldOption.getTitle());
                fieldOptionCheckBox.setLayoutX(categoryNameTextField.getLayoutX() + counter);
                fieldOptionCheckBox.setLayoutY(categoryNameTextField.getLayoutY()+50);
                checkBoxes.add(fieldOptionCheckBox);
                pane.getChildren().add(fieldOptionCheckBox);
                counter+=150;
            }
        }
    }

}

