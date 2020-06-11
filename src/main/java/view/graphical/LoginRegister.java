package view.graphical;

import controller.share.LoginRegisterMenu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.exception.InvalidAccessException;
import model.exception.InvalidAccountInfoException;
import model.exception.InvalidLoginInformationException;
import view.commandline.View;

import java.io.IOException;
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

    public void onLoginButtonClick(MouseEvent mouseEvent) {
        try {
            loginRegisterMenu.login(loginUsernameTf.getText(), loginPasswordTf.getText());
            Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
        } catch (InvalidLoginInformationException e) {
            loginMessageLabel.setText(e.getMessage());
        }
    }

    public void onRegisterButtonClick(MouseEvent mouseEvent) {
        try {
            loginRegisterMenu.checkUserNameAvailability(registerUsernameTf.getText());
            if (accountType.getValue() == null) {
                registerMessageLabel.setText("Select an account type");
                return;
            }
            if (accountType.getValue().equals("manager")) {
                loginRegisterMenu.isThereManagerAccount();
            }
            switch (accountType.getValue()) {
                case "personal":
                    loginRegisterMenu.registerPersonalAccount(registerUsernameTf.getText(),
                            registerFirstNameTf.getText(), registerLastNameTf.getText(), registerEmailTf.getText(),
                            registerPhoneNumberTf.getText(), registerPassword.getText());
                    break;

                case "reseller":
                    loginRegisterMenu.registerResellerAccount(registerUsernameTf.getText(),
                            registerFirstNameTf.getText(), registerLastNameTf.getText(), registerEmailTf.getText(),
                            registerPhoneNumberTf.getText(), registerPassword.getText(), registerBusinessNameTf.getText());
                    break;

                case "manager":
                    loginRegisterMenu.registerManagerAccount(registerUsernameTf.getText(),
                            registerFirstNameTf.getText(), registerLastNameTf.getText(), registerEmailTf.getText(),
                            registerPhoneNumberTf.getText(), registerPassword.getText());
            }
            registerMessageLabel.setText("You registered successfully");
        } catch (InvalidLoginInformationException | InvalidAccessException | InvalidAccountInfoException e) {
            registerMessageLabel.setText(e.getMessage());
        }
    }
}
