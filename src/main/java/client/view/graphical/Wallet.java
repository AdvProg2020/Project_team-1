package client.view.graphical;

import client.Session;
import common.model.account.BusinessAccount;
import common.model.account.PersonalAccount;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Main.*;

public class Wallet implements Initializable {
    public TextField money;
    public Label balance;
    public Label error;
    public Button withdrawButton;
    public TextField bankUsername;
    public PasswordField password;

    public void deposit(ActionEvent actionEvent) throws IOException {
        String token = getToken();
        if (token == null) return;
        try {
            Double.parseDouble(money.getText());
            String respond = sendTransaction(token, "Deposit to wallet ");
            if (respond.equals("done successfully")){
                if (Session.getOnlineAccount() instanceof BusinessAccount)
                    ((BusinessAccount)Session.getOnlineAccount()).addToCredit(Double.parseDouble(money.getText()));
                else ((PersonalAccount) Session.getOnlineAccount()).addToCredit(Double.parseDouble(money.getText()));
                initializePane();
            }
        }catch (Exception e){
            error.setText("Invalid money");
        }
    }

    public void withdraw(ActionEvent actionEvent) throws IOException {
        String token = getToken();
        if (token == null) return;
        if (Double.parseDouble(balance.getText()) < Double.parseDouble(money.getText())){
            error.setVisible(true);
            error.setText("You dont have enough money in your wallet.");
            return;
        }
        try {
            String respond = sendTransaction(token, "Withdraw from wallet ");
            if (respond.equals("done successfully")){
                ((BusinessAccount) Session.getOnlineAccount()).addToCredit(-Double.parseDouble(money.getText()));
                initializePane();
        }
        }catch (Exception e){
            error.setText("Invalid money");
        }
    }

    public String sendTransaction(String token, String string) throws IOException {
        DataOutputStream dataOutputStream1 = new DataOutputStream(socket.getOutputStream());
        dataOutputStream1.writeUTF(string + token + " "
                + Session.getOnlineAccount().getUsername() + " " + money.getText());
        dataOutputStream1.flush();
        DataInputStream dataInputStream1 = new DataInputStream(socket.getInputStream());
        String respond = dataInputStream1.readUTF();
        error.setVisible(true);
        error.setText(respond);
        return respond;
    }

    public String getToken() throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        dataOutputStream.writeUTF("get_token " + bankUsername.getText() + " " + password.getText());
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        String token = dataInputStream.readUTF();
        if (token.equals("invalid username or password")) {
            error.setVisible(true);
            error.setText("invalid username or password");
            return null;
        }
        return token;
    }

    public void close(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePane();
    }

    public void initializePane() {
        if (Session.getOnlineAccount() instanceof PersonalAccount) {
            withdrawButton.setDisable(true);
            balance.setText(String.valueOf(((PersonalAccount) Session.getOnlineAccount()).getCredit()));
        }else balance.setText(String.valueOf(((BusinessAccount)Session.getOnlineAccount()).getCredit()));
    }
}
