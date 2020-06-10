package view.graphical;

import controller.share.LoginRegisterMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Session;
import model.exception.InvalidLoginInformationException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginRegister implements Initializable {

    private LoginRegisterMenu loginRegisterMenu = new LoginRegisterMenu();
    public ChoiceBox<String> accountType;
    public Label loginMessageLabel;
    public TextField loginUsernameTf;
    public PasswordField loginPasswordTf;
    public Button loginButton;
    public TextField registerUsernameTf;
    public TextField firstNameTf;
    public TextField registerLastNameTf;
    public TextField registerPhoneNumberTf;
    public TextField registerEmailTf;
    public PasswordField registerPassword;
    public Label registerMessageLabel;
    public Button registerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAccountTypeChoiceBox();
    }

    private void initializeAccountTypeChoiceBox() {
        List<String> accountTypesList = new ArrayList<>(Arrays.asList("personal", "reseller", "manager"));
        ObservableList<String> observableList = FXCollections.observableList(accountTypesList);
        accountType.setItems(observableList);
    }

    public void onBackButtonClick(MouseEvent mouseEvent) {
        loginRegisterMenu.goToPreviousMenu();

    }

    public void onLoginButtonClick(MouseEvent mouseEvent) {
        try {
            loginRegisterMenu.login(loginUsernameTf.getText(), loginPasswordTf.getText());
            Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
        } catch (InvalidLoginInformationException e) {
            loginMessageLabel.setText(e.getMessage());
        }
    }

    public void onRegisterButtonClick(MouseEvent mouseEvent) {

    }
}
