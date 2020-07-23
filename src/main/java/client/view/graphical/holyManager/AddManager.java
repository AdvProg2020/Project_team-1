package client.view.graphical.holyManager;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import common.model.account.ManagerAccount;
import client.view.commandline.View;

import java.io.File;

public class AddManager {
    public TextField registerUsernameTf;
    public TextField registerFirstNameTf;
    public TextField registerLastNameTf;
    public TextField registerPhoneNumberTf;
    public TextField registerEmailTf;
    public PasswordField registerPassword;
    public Label registerMessageLabel;
    public ImageView userPhotoImageView;

    public void pickPhoto(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                    "*.png", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
            Image image = new Image(file.toURI().toString());
            userPhotoImageView.setImage(image);
        }catch (Exception e){
            registerMessageLabel.setText("Please pick a photo again.");
        }
    }

    public void createManagerAccount(ActionEvent actionEvent) {
        try {
            ManagerAccount managerAccount = new ManagerAccount(registerUsernameTf.getText(),registerFirstNameTf.getText() , registerLastNameTf.getText(), registerEmailTf.getText()
                    , registerPhoneNumberTf.getText() , registerPassword.getText() , userPhotoImageView.getImage().getUrl());
            View.manageUsersPanel.createNewManager(managerAccount);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }catch (Exception e){
            registerMessageLabel.setText(e.getMessage());
        }

    }
}
