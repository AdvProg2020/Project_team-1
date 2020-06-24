package view.graphical;

import controller.data.YaDataManager;
import controller.reseller.ManageResellerProductsMenu;
import controller.reseller.ResellerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
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
import view.commandline.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class Reseller implements Initializable {

    public Label textFieldPopupTitle;
    public TreeView<String> salesHistoryTreeView;
    public Label resellerBalanceLabel;
    public TreeView<String> categoriesTreeView;
    public AnchorPane manageProductsAnchorPane;
    public final ResellerMenu resellerMenu = View.resellerMenu;
    public final ManageResellerProductsMenu manageResellerProductsMenu = View.manageResellerProductsMenu;
    public Label businessNameLabel;
    public Label tableViewPopupTitleLabel;
    public Label phoneNumberLabel;
    public Label emailLabel;
    public Label lastNameLabel;
    public Label firstNameLabel;
    public ImageView userPhotoImageView;
    public Label usernameLabel;
    public ChoiceBox<String> manageProductsSortField;
    public ToggleButton manageProductsSortOrderToggleButton;
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

    public void onAddProductClick(MouseEvent mouseEvent) {
    }

    public void onManageProductsClick(MouseEvent mouseEvent) {
        initializeSortFieldChoiceBox();
        onSortButtonClick(mouseEvent);
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    private void initializeSortFieldChoiceBox() {
        List<String> fieldsList = new ArrayList<>(Arrays.asList("brand", "name", "id", "price", "visits", "number of scores", "average score"));
        ObservableList<String> observableList = FXCollections.observableList(fieldsList);
        manageProductsSortField.setItems(observableList);
    }

    public void onSortButtonClick(MouseEvent mouseEvent) {
        try {
            manageProductsAnchorPane.getChildren().clear();
            ArrayList<Commodity> commodities;
            if (manageProductsSortField.getValue() == null) {
                commodities = resellerMenu.manageCommodities();
            } else {
                commodities = manageResellerProductsMenu.sort(manageProductsSortField.getValue());
            }
            int increment, startPoint, endPoint;
            if (manageProductsSortOrderToggleButton.isSelected()) {
                increment = -1;
                startPoint = commodities.size() - 1;
                endPoint = 0;
            } else {
                increment = 1;
                startPoint = 0;
                endPoint = commodities.size() - 1;
            }
            for (int i = startPoint; i != endPoint; i += increment) {
                AnchorPane productAnchorPane = new AnchorPane();
                productAnchorPane.setMaxHeight(300);
                productAnchorPane.setMaxWidth(300);
                ImageView productImage = new ImageView(new Image(new FileInputStream(commodities.get(i).getImagePath())));
                productImage.maxWidth(250);
                productImage.maxHeight(250);
                Label productName = new Label(commodities.get(i).getName());
                HBox actions = new HBox();
                actions.setAlignment(Pos.CENTER);
                Button show = new Button("Show"), edit = new Button("Edit"), remove = new Button("Remove");
                int finalI = i;
                show.setOnMouseClicked(mouseEvent1 -> {
                    // Show
                });
                edit.setOnMouseClicked(mouseEvent1 -> {
                    // edit
                });
                remove.setOnMouseClicked(mouseEvent12 -> {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this product?",
                            ButtonType.NO, ButtonType.YES);
                    confirmation.setResizable(false);
                    confirmation.setHeight(200);
                    confirmation.setWidth(500);
                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        try {
                            resellerMenu.removeProduct(commodities.get(finalI).getCommodityId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                actions.getChildren().addAll(show, edit, remove);
                productAnchorPane.getChildren().addAll(productImage, productName, actions);
                manageProductsAnchorPane.getChildren().add(productAnchorPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
