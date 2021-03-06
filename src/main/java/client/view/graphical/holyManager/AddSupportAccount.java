package client.view.graphical.holyManager;

import client.view.commandline.View;
import common.model.account.ManagerAccount;
import common.model.account.SupportAccount;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import static client.Main.socket;

import java.io.*;
import java.net.Socket;

public class AddSupportAccount {
    public TextField registerUsernameTf;
    public TextField registerFirstNameTf;
    public TextField registerLastNameTf;
    public TextField registerPhoneNumberTf;
    public TextField registerEmailTf;
    public PasswordField registerPassword;
    public Label registerMessageLabel;
    public ImageView userPhotoImageView;

    public void createSupportAccount(ActionEvent actionEvent) {
        try {
            SupportAccount supportAccount = new SupportAccount(registerUsernameTf.getText(), registerFirstNameTf.getText(), registerLastNameTf.getText(), registerEmailTf.getText()
                    , registerPhoneNumberTf.getText(), registerPassword.getText(), userPhotoImageView.getImage().getUrl());
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("new support account");
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataInputStream.readUTF();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((socket.getOutputStream()));
            objectOutputStream.writeObject(supportAccount);
            objectOutputStream.flush();
            String respond = dataInputStream.readUTF();
            if (respond.equalsIgnoreCase("Support account successfully added"))
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            else registerMessageLabel.setText(respond);
        } catch (Exception e) {
            registerMessageLabel.setText(e.getMessage());
        }
    }

    public void onPickAPhotoClick(MouseEvent mouseEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                    "*.png", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
            Image image = new Image(file.toURI().toString());
            userPhotoImageView.setImage(image);
        } catch (Exception e) {
            registerMessageLabel.setText("Please pick a photo again.");
        }
    }
}
