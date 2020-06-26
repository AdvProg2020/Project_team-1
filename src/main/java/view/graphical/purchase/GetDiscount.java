package view.graphical.purchase;

import controller.customer.CartMenu;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.commodity.DiscountCode;
import view.commandline.View;

public class GetDiscount {
    private final CartMenu cartMenu = View.cartMenu;
    public Label error;
    public TextField discountField;

    public void back(ActionEvent actionEvent) {
        //go to get info page
    }

    public void purchase(ActionEvent actionEvent) {
        try {
            DiscountCode discountCode = cartMenu.getDiscountCodeWithCode(discountField.getText());
            cartMenu.purchase(discountCode);
            //go to finished business
        } catch (Exception e) {
            error.setText(e.getMessage() + '\n' + "please enter a discount code or enter nothing");
        }
    }
}
