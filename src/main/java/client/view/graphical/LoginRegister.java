package client.view.graphical;

import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import server.controller.share.LoginRegisterMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import client.Session;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import client.view.commandline.View;

import static client.Main.socket;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginRegister implements Initializable {

    public TextField registerBusinessNameTf;
    private final LoginRegisterMenu loginRegisterMenu = View.loginRegisterMenu;
    public ChoiceBox<String> accountType;
    public Label loginMessageLabel;
    public TextField loginUsernameTf;
    public PasswordField loginPasswordTf;
    public Button loginButton;
    public TextField registerUsernameTf;
    public TextField registerFirstNameTf;
    public TextField registerLastNameTf;
    public TextField registerPhoneNumberTf;
    public TextField registerEmailTf;
    public PasswordField registerPassword;
    public Label registerMessageLabel;
    public Button registerButton;
    public ImageView userPhotoImageView;
    private String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAccountTypeChoiceBox();
    }

    private void initializeAccountTypeChoiceBox() {
        List<String> accountTypesList = new ArrayList<>(Arrays.asList("personal", "reseller", "manager"));
        ObservableList<String> observableList = FXCollections.observableList(accountTypesList);
        accountType.setItems(observableList);
        registerBusinessNameTf.setDisable(true);
        accountType.valueProperty().addListener(
                (observableValue, s, t1) -> registerBusinessNameTf.setDisable(!t1.equals("reseller")));
    }

    public void onBackButtonClick(MouseEvent mouseEvent) {
        loginRegisterMenu.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onLoginButtonClick(MouseEvent mouseEvent) throws IOException {
        try {
            loginRegisterMenu.login(loginUsernameTf.getText(), loginPasswordTf.getText());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("account logged in: " + loginUsernameTf.getText());
            dataOutputStream.flush();
            Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
        } catch (InvalidLoginInformationException e) {
            loginMessageLabel.setText(e.getMessage());
        }
    }

    public void onRegisterButtonClick() throws IOException {

        if (accountType.getValue() == null) {
            registerMessageLabel.setText("Select an account type");
            return;
        }
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Register");
        dataOutputStream.flush();
        String information;
        switch (accountType.getValue()) {
            case "personal":
                PersonalAccount personalAccount = null;
                information = "personal " + registerUsernameTf.getText() + " " +
                        registerFirstNameTf.getText() + " " + registerLastNameTf.getText() + " " + registerEmailTf.getText() + " " +
                        registerPhoneNumberTf.getText() + " " + registerPassword.getText() + " " + imagePath;
                dataOutputStream.writeUTF(information);
                dataOutputStream.flush();
                break;

            case "reseller":
                ManagerAccount managerAccount = null;
                information = "business " +  registerUsernameTf.getText() + " " +
                        registerFirstNameTf.getText() + " " + registerLastNameTf.getText() + " " + registerEmailTf.getText() + " " +
                        registerPhoneNumberTf.getText() + " " + registerPassword.getText() + " " + imagePath;
                dataOutputStream.writeUTF(information);
                dataOutputStream.flush();
                dataOutputStream.writeUTF(information);
                dataOutputStream.flush();
                break;

            case "manager":
                information = "manager " + registerUsernameTf.getText() + " " +
                        registerFirstNameTf.getText() + " " + registerLastNameTf.getText() + " " + registerEmailTf.getText() + " " +
                        registerPhoneNumberTf.getText() + " " + registerPassword.getText() + " " + imagePath;
                dataOutputStream.writeUTF(information);
                dataOutputStream.flush();
        }
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String respond = dataInputStream.readUTF();
        registerMessageLabel.setText(respond);
    }

    public void onPickAPhotoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        imagePath = file.getAbsolutePath();
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userPhotoImageView.setImage(image);
    }

    public void onExitButtonClick() {
        System.exit(0);
    }
}
