package view.graphical;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.time.format.DateTimeFormatter;

public class DatePickerPopup {
    public DatePicker datePicker;
    private ShowEditOff showEditOff;
    private boolean startDate;

    public void setStartDate(boolean startDate) {
        this.startDate = startDate;
    }

    public void setShowEditOff(ShowEditOff showEditOff) {
        this.showEditOff = showEditOff;
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onConfirmClick(MouseEvent mouseEvent) {
        if (startDate) {
            showEditOff.startTime.setText(datePicker.getValue().format(DateTimeFormatter.ofPattern("MMM d yyyy")));
        } else {
            showEditOff.endTime.setText(datePicker.getValue().format(DateTimeFormatter.ofPattern("MMM d yyyy")));
        }
        onCancelClick(mouseEvent);
    }
}
