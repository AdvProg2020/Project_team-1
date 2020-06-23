package view.graphical;

import controller.data.YaDataManager;
import controller.reseller.ResellerMenu;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.commodity.Category;
import model.commodity.CategorySpecification;
import model.commodity.Commodity;
import model.log.SellLog;
import sun.reflect.generics.tree.Tree;
import view.commandline.View;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Reseller implements Initializable {

    public Label textFieldPopupTitle;
    public TreeView<String> salesHistoryTreeView;
    public Label resellerBalanceLabel;
    public TreeView<String> categoriesTreeView;
    private ResellerMenu resellerMenu = View.resellerMenu;
    public Label businessNameLabel;
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
        TreeItem<String> salesHistoryRootItem = new TreeItem<>("Sales history");
        for (SellLog sellLog : ((BusinessAccount) Session.getOnlineAccount()).getSellLogs()) {
            TreeItem<String> sellLogTreeItem = new TreeItem<>(String.valueOf(sellLog.getLogId()));
            TreeItem<String> commoditiesTreeItem = new TreeItem<>("Commodities");
            sellLogTreeItem.getChildren().addAll(new TreeItem<>("Received money : " + sellLog.getReceivedMoney()),
                    new TreeItem<>("Deducted money on sale : " + sellLog.getDeductedMoneyOnSale()),
                    new TreeItem<>("Buyer : " + sellLog.getBuyer().getUsername()),
                    new TreeItem<>(sellLog.getCommodityDelivered()? "Delivered": "Not delivered yet"),
                    new TreeItem<>("Sell date : " + sellLog.getDate().toString()),
                    commoditiesTreeItem);
            for (Commodity commodity : sellLog.getCommodities()) {
                commoditiesTreeItem.getChildren().add(new TreeItem<>(commodity.getName()));
            }
            salesHistoryRootItem.getChildren().add(sellLogTreeItem);
        }
        salesHistoryTreeView.setRoot(salesHistoryRootItem);
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
        //Session.getOnlineAccount().setUserPhoto(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resellerBalanceLabel.setText("Your balance : " + ((BusinessAccount) Session.getOnlineAccount()).getCredit());
    }

    public void onCategoriesClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/CategoriesPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeItem<String> rootTreeItem = new TreeItem<>("All categories");
        try {
            for (Category category : YaDataManager.getCategories()) {
                TreeItem<String> categoryTreeItem = new TreeItem<>(category.getName());
                for (CategorySpecification fieldOption : category.getFieldOptions()) {
                    categoryTreeItem.getChildren().add(new TreeItem<>(fieldOption.getTitle()));
                }
                rootTreeItem.getChildren().add(categoryTreeItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoriesTreeView.setRoot(rootTreeItem);
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }
}
