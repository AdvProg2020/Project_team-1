package view.graphical;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.filter.NumericalFilter;
import view.commandline.View;

public class FilterByCategorySpecificationNumericalField {
    public TextField endRangeTextField;
    public TextField startRangeTextField;
    public TextField correspondingFieldNumberTextField;
    public TextField categoryNameTextField;

    public void filterByNumericalField(ActionEvent actionEvent) {
        String categoryName = categoryNameTextField.getText();
        int correspondingField = 0;
        int startRange = 0;
        int endRange = 0;
        try {
            correspondingField = Integer.parseInt(correspondingFieldNumberTextField.getText());
            startRange = Integer.parseInt(startRangeTextField.getText());
            endRange = Integer.parseInt(endRangeTextField.getText());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Corresponding field number and start range and end range should be numeric");
            alert.setContentText("Corresponding field number and start range and end range should be numeric");
            alert.show();
            categoryNameTextField.getScene().getWindow().hide();
            return;
        }
        try {
            View.filteringMenu.filter(new model.filter.FilterByCategory("Filter by category "+categoryName , View.manageCategoryMenu.getCategory(categoryName)));
            String filterName = "Filter by category specification " + categoryName + " " + correspondingField;
            View.filteringMenu.filter(new NumericalFilter(filterName,startRange,endRange,correspondingField));
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid category name or numerical field");
            alert.setContentText("Invalid category name");
            alert.show();
            try {
                View.filteringMenu.disableFilter("Filter by category "+categoryName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            categoryNameTextField.getScene().getWindow().hide();
    }
}
