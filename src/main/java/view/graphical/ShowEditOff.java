package view.graphical;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.commodity.Off;

public class ShowEditOff {

    public Label startTime;
    public Label endTime;
    public Label discountPercent;
    public ListView<HBox> productsInOffListView;
    public Label errorMessageLabel;

    public void initScene(Off off) {
        startTime.setText(off.getStartTime().toString());
        endTime.setText(off.getEndTime().toString());
        discountPercent.setText(String.valueOf(off.getDiscountPercent()));

    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void onSaveClick(MouseEvent mouseEvent) {

        onCancelClick(mouseEvent);
    }

    public void onStartTimeClick(MouseEvent mouseEvent) {
    }

    public void onEndTimeClick(MouseEvent mouseEvent) {
    }

    public void onDiscountPercentClick(MouseEvent mouseEvent) {
    }

    public void onAddProductToOffClick(MouseEvent mouseEvent) {
    }
}
