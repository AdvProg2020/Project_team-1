package client.view.graphical;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static client.Main.socketB;

public class BankAccountPage {
    public TextField username;
    public PasswordField password;
    public TextField firstName;
    public TextField lastName;
    public Label error;


    public void register(ActionEvent actionEvent) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        dataOutputStream.writeUTF("create_account " +firstName.getText() + " " +
                lastName.getText() + " " + username.getText() + " " + password.getText()+ " "+ password.getText());
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        String respond = dataInputStream.readUTF();
        error.setText(respond);
        if (!(respond.equals("password do not match") || respond.equals("username is not available"))){
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }

    }
}
