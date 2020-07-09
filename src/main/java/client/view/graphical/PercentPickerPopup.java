package client.view.graphical;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class PercentPickerPopup {
    public Label percentLabel;
    public Slider percentSlider;
    private ShowEditOff showEditOff;

    public void setShowEditOff(ShowEditOff showEditOff) {
        this.showEditOff = showEditOff;
        percentSlider.valueProperty().addListener((observableValue, number, t1) ->
                percentLabel.setText(String.valueOf(Math.floor((Double) t1))));
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onConfirmClick(MouseEvent mouseEvent) {
        showEditOff.discountPercent.setText(percentLabel.getText());
        onCancelClick(mouseEvent);
    }
}
