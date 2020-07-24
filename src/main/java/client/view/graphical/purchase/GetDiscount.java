package client.view.graphical.purchase;

import client.Main;
import client.Session;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import server.controller.customer.CartMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import common.model.commodity.DiscountCode;
import client.view.commandline.View;

import java.io.IOException;

public class GetDiscount {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    private final CartMenu cartMenu = View.cartMenu;
    public Label error;
    public TextField discountField;

    public void back(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void purchase(ActionEvent actionEvent) {
        try {
            if (!discountField.getText().equals(""))
                 Main.outputStream.writeUTF("purchase " + Session.getOnlineAccount() + " no discount code");
            else Main.outputStream.writeUTF("purchase " + Session.getOnlineAccount() + " " + discountField.getText());
            Main.outputStream.flush();
            Main.outputStream.writeUTF(yaGson.toJson(Session.getOnlineAccount() , new TypeToken<PersonalAccount>(){}.getType()));
            Main.outputStream.flush();
            String errorString = Main.inputStream.readUTF();
            error.setText(errorString);
            if (!errorString.equals("You purchased successfully"))
                throw new Exception(errorString);
            PersonalAccount personalAccount = yaGson.fromJson(Main.inputStream.readUTF() , new TypeToken<PersonalAccount>(){}.getType());
            Session.setOnlineAccount(personalAccount);
            //DiscountCode discountCode = cartMenu.getDiscountCodeWithCode(discountField.getText());
            //cartMenu.purchase(discountCode);
            Parent parent = null;
            Popup popupMenu = new Popup();
            try {
                parent = FXMLLoader.load(getClass().getResource("../../../../fxml/customer/purchase/FinishPurchase.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            popupMenu.getContent().add(parent);
            popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
        } catch (Exception e) {
            error.setText(e.getMessage() + '\n' + "please enter a discount code or enter nothing");
        }
    }
}
