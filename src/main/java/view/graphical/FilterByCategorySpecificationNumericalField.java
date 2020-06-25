package view.graphical;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.commodity.Category;
import model.commodity.CategorySpecification;
import model.filter.NumericalFilter;
import view.commandline.View;

import java.util.ArrayList;

public class FilterByCategorySpecificationNumericalField {
    public TextField endRangeTextField;
    public TextField startRangeTextField;
    public TextField categoryNameTextField;
    public AnchorPane pane;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    //private String categoryName = categoryNameTextField.getText();
    Category category = null;
    public void filterByNumericalField(ActionEvent actionEvent) {
        int startRange = 0;
        int endRange = 0;
        String categoryName = categoryNameTextField.getText();
        try {
            category = View.manageCategoryMenu.getCategory(categoryName);
            if (category == null)
                throw new Exception();
            startRange = Integer.parseInt(startRangeTextField.getText());
            endRange = Integer.parseInt(endRangeTextField.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("start range and end range should be numeric or invalid category name.");
            alert.setContentText("start range or end range is not numeric or invalid category name.");
            alert.show();
            categoryNameTextField.getScene().getWindow().hide();
            return;
        }
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected())
                filter(categoryName, i, startRange, endRange, category);
        }
        categoryNameTextField.getScene().getWindow().hide();
    }

    private void filter(String categoryName, int correspondingField, int startRange, int endRange, Category category) {
        String filterName = "Filter by category specification " + categoryName + " " + correspondingField;

        try {
            View.filteringMenu.filter(new NumericalFilter(filterName, category , startRange, endRange, correspondingField));
            SceneHandler.getProductsMenuLoad().deleteCommodities(SceneHandler.getProductsMenuLoad().getRoot());
            SceneHandler.getProductsMenuLoad().setCommodities(SceneHandler.getProductsMenuLoad().getRoot());

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            try {
                View.filteringMenu.disableFilter(filterName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showFields(ActionEvent actionEvent) {
        String categoryName = categoryNameTextField.getText();
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
            if (fieldOption.getOptions() == null) {
                CheckBox fieldOptionCheckBox = new CheckBox(fieldOption.getTitle());
                fieldOptionCheckBox.setLayoutX(categoryNameTextField.getLayoutX() + 50 + counter);
                fieldOptionCheckBox.setLayoutY(categoryNameTextField.getLayoutY() + 50);
                checkBoxes.add(fieldOptionCheckBox);
                pane.getChildren().add(fieldOptionCheckBox);
                counter += 150;
            }
        }
    }
}
