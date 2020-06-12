package view.graphical;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import view.commandline.View;

public class FilterByName {
    public TextField name;

    public void filterByName(ActionEvent actionEvent) {
        String nameString = name.getText();
        try {
            View.filteringMenu.filter(new model.filter.FilterByName("Filter by name " + nameString, nameString));
            name.getScene().getWindow().hide();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
