package view.graphical;

import controller.reseller.ResellerMenu;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.log.SellLog;
import view.commandline.View;

import java.io.File;
import java.io.IOException;

public class Reseller {

    public Label textFieldPopupTitle;
    private ResellerMenu resellerMenu = View.resellerMenu;
    public Label businessNameLabel;
    public Label tableViewPopupTitleLabel;
    public TableView salesHistoryTableView;
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
        userPhotoImageView.setImage(simpleAccount.getUserPhoto());
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
        businessNameLabel.setText(((BusinessAccount) Session.getOnlineAccount()).getBusinessName());
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onSalesHistoryClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/reseller/SalesHistoryPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<SellLog> data = salesHistoryTableView.getItems();
        data.addAll(((BusinessAccount) Session.getOnlineAccount()).getSellLogs());
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onChangePasswordClick(MouseEvent mouseEvent) {

    }

    public void onFirstNameLabelClick(MouseEvent mouseEvent) {

    }

    public void onLastNameClick(MouseEvent mouseEvent) {
    }

    public void onEmailClick(MouseEvent mouseEvent) {
    }

    public void onPhoneNumberClick(MouseEvent mouseEvent) {
    }

    public void onChangeUserPhotoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        Image image = new Image(file.toURI().toString());
        userPhotoImageView.setImage(image);
        Session.getOnlineAccount().setUserPhoto(image);
    }

    private void openTextFieldPopup(MouseEvent mouseEvent, String title) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/TextFieldPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        textFieldPopupTitle.setText(title);
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }
}
