package client.view.graphical;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TextFieldPopup {
    public TextField textField;
    private ShowEditProduct showEditProduct;

    public void setShowEditProduct(ShowEditProduct showEditProduct) {
        this.showEditProduct = showEditProduct;
    }

    public void onConfirmClick(MouseEvent mouseEvent) {
        if (textField.getText().equals("")) {
            return;
        }
        showEditProduct.setNewValue(textField.getText());
        onCancelClick(mouseEvent);
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
