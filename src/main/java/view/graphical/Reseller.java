package view.graphical;

import controller.reseller.ResellerMenu;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import view.commandline.View;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Reseller implements Initializable {

    public Label resellerBalanceLabel;
    public final ResellerMenu resellerMenu = View.resellerMenu;
    public Label tableViewPopupTitleLabel;
    public Label phoneNumberLabel;
    public Label emailLabel;
    public Label lastNameLabel;
    public Label firstNameLabel;
    public ImageView userPhotoImageView;
    public Label usernameLabel;
    Popup popupMenu = new Popup();

    public void onPersonalInfoClick(MouseEvent mouseEvent) {
        SimpleAccount simpleAccount = Session.getOnlineAccount();
        usernameLabel.setText(simpleAccount.getUsername());
        firstNameLabel.setText(simpleAccount.getFirstName());
        lastNameLabel.setText(simpleAccount.getLastName());
        emailLabel.setText(simpleAccount.getEmail());
        phoneNumberLabel.setText(simpleAccount.getPhoneNumber());
        //userPhotoImageView.setImage(simpleAccount.getUserPhoto());
        View.managerMenu.viewPersonalInfo();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }


    public void onCompanyInfoClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/reseller/CompanyInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onSalesHistoryClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/reseller/SalesHistoryPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onChangeUserPhotoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        Image image = new Image(file.toURI().toString());
        userPhotoImageView.setImage(image);
        //Session.getOnlineAccount().setUserPhoto(image);
    }

    public void onCategoriesClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/CategoriesPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onManageProductsClick(MouseEvent mouseEvent) {
        try {
            resellerMenu.manageCommodities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resellerBalanceLabel.setText("Account balance : " + ((BusinessAccount) Session.getOnlineAccount()).getCredit());
    }

    public void onLogoutClick(MouseEvent mouseEvent) {
        try {
            resellerMenu.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onManageOffsClick(MouseEvent mouseEvent) {
        try {
            resellerMenu.manageOffs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onMainMenuClick(MouseEvent mouseEvent) {
    }

    public void onProductsClick(MouseEvent mouseEvent) {
    }
}
