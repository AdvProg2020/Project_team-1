package view.graphical;

import controller.customer.CustomerMenu;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.PersonalAccount;
import view.commandline.View;

import java.net.URL;
import java.util.ResourceBundle;

public class Customer implements Initializable {
    private final CustomerMenu customerMenu = View.customerMenu;
    public Label usernameLabel;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label emailLabel;
    public Label phoneLabel;
    public Label balanceLabel;
    public Label resellerBalanceLabel;
    Popup popupMenu = new Popup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        usernameLabel.setText(account.getUsername());
        firstNameLabel.setText(account.getFirstName());
        lastNameLabel.setText(account.getLastName());
        emailLabel.setText(account.getEmail());
        phoneLabel.setText(account.getPhoneNumber());
        balanceLabel.setText(customerMenu.getBalance() + " Rials");
    }

    public void onBackButtonClick(MouseEvent mouseEvent) {
        customerMenu.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCartButtonClick(MouseEvent mouseEvent) {
        customerMenu.goToCartMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onDiscountClick(MouseEvent mouseEvent) throws Exception {

    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onPersonalInfoClick(MouseEvent mouseEvent) {
    }

    public void onOrdersHistoryClick(MouseEvent mouseEvent) {
    }

    public void onLogOutClick(MouseEvent mouseEvent) throws Exception {
        customerMenu.logout();

    }

    public void onProductsClick(MouseEvent mouseEvent) {
    }
}
