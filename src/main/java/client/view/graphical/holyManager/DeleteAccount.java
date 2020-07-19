package client.view.graphical.holyManager;

import client.Session;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static client.Main.socket;

import static client.view.commandline.View.manageUsersMenu;

public class DeleteAccount {
    public TextField username;

    public void delete(ActionEvent actionEvent) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Delete account " + username.getText() + " " + Session.getOnlineAccount().getUsername());
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String respond = dataInputStream.readUTF();
        System.out.println(respond);
            if (!respond.equals("Account successfully deleted.")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            }
            ((((Node) actionEvent.getSource()).getScene().getWindow())).hide();
    }
}
