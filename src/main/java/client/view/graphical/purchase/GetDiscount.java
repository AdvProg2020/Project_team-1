package client.view.graphical.purchase;

import client.Main;
import client.Session;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import javafx.scene.control.PasswordField;
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
import static client.Main.socketB;
import static client.Main.inputStream;
import static client.Main.outputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GetDiscount {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    private final CartMenu cartMenu = View.cartMenu;
    public Label error;
    public TextField discountField;
    public PasswordField password;
    public TextField username;
    public Label errorBank;

    public void back(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void purchase(ActionEvent actionEvent) {
        try {
            purchaseInCommon();
            String errorString = Main.inputStream.readUTF();
            System.out.println(errorString);
            error.setText(errorString);
            if (!errorString.equals("You purchased successfully"))
                throw new Exception(errorString);
            PersonalAccount personalAccount = yaGson.fromJson(Main.inputStream.readUTF() , new TypeToken<PersonalAccount>(){}.getType());
            Session.setOnlineAccount(personalAccount);
            //DiscountCode discountCode = cartMenu.getDiscountCodeWithCode(discountField.getText());
            //cartMenu.purchase(discountCode);
            changePage(actionEvent);
        } catch (Exception e) {
            error.setText(e.getMessage() + '\n' + "please enter a discount code or enter nothing");
        }
    }

    private void changePage(ActionEvent actionEvent) {
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../../fxml/customer/purchase/FinishPurchase.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void purchaseViaBank(ActionEvent actionEvent) throws IOException {
        System.out.println("Salam");
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        dataOutputStream.writeUTF("get_token " + username.getText() + " " + password.getText());
        String respond = dataInputStream.readUTF();
        System.out.println(respond);
        if (!(respond.equals("invalid username or password")||respond.equals("invalid input"))) {
            purchaseInCommon();
            Main.outputStream.writeUTF(respond);
            String errorString = Main.inputStream.readUTF();
            System.out.println(errorString);
            errorBank.setText(errorString);
            if (errorString.equals("You purchased successfully")){
                changePage(actionEvent);
                PersonalAccount personalAccount = yaGson.fromJson(Main.inputStream.readUTF() , new TypeToken<PersonalAccount>(){}.getType());
                Session.setOnlineAccount(personalAccount);
            }

        }else errorBank.setText("Invalid entry.");

        return;

    }

    private void purchaseInCommon() throws IOException {
        if (discountField.getText().equals(""))
            Main.outputStream.writeUTF("purchaseWallet " + Session.getOnlineAccount().getUsername() + " no discount code");
        else Main.outputStream.writeUTF("purchaseWallet " + Session.getOnlineAccount().getUsername() + " " + discountField.getText());
        Main.outputStream.flush();
        Main.outputStream.writeUTF(yaGson.toJson(Session.getOnlineAccount() , new TypeToken<PersonalAccount>(){}.getType()));
        Main.outputStream.flush();
    }
}
