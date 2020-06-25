package view.graphical;

import controller.customer.CustomerMenu;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Session;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.commodity.Commodity;
import model.commodity.DiscountCode;
import model.log.BuyLog;
import view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPanel implements Initializable {
    private final CustomerMenu customerMenu = View.customerMenu;
    public TreeView ordersHistoryTreeView;
    public TreeView discountCodesTreeView;
    public Label balanceLabel;
    Popup popupMenu = new Popup();

    public void onBackButtonClick(MouseEvent mouseEvent) {
        customerMenu.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCartButtonClick(MouseEvent mouseEvent) {
        customerMenu.goToCartMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onDiscountClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/customer/DiscountCodes.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeItem<String> discountRootItem = new TreeItem<>("Discount Codes");
        for (DiscountCode discountCode : ((PersonalAccount) Session.getOnlineAccount()).getDiscountCodes()) {
            TreeItem<String> discountCodesTreeItem = new TreeItem<>(discountCode.getCode());
            discountCodesTreeItem.getChildren().addAll(new TreeItem<>("Discount Percentage: " + discountCode.
                            getMaximumDiscountPrice()),
                    new TreeItem<>("Discount price limit: " + discountCode.getMaximumDiscountPrice()),
                    new TreeItem<>("Start Date" + discountCode.getStartDate().toString()),
                    new TreeItem<>("Finish Date: " + discountCode.getFinishDate().toString()),
                    new TreeItem<>("Number of uses limit: " + discountCode.getMaximumNumberOfUses()),
                    new TreeItem<>("Number of times used: " + ((PersonalAccount) Session.getOnlineAccount()).
                            getNumberOfTimesUsed(discountCode)));
            discountRootItem.getChildren().add(discountCodesTreeItem);
        }
        discountCodesTreeView.setRoot(discountRootItem);
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onPersonalInfoClick(MouseEvent mouseEvent) {
        new Reseller().onPersonalInfoClick(mouseEvent);
    }

    public void onOrdersHistoryClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/customer/OrdersHistory.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeItem<String> ordersHistoryRootItem = new TreeItem<>("Orders history");
        for (BuyLog buyLog : ((PersonalAccount) Session.getOnlineAccount()).getBuyLogs()) {
            TreeItem<String> buyLogTreeItem = new TreeItem<>(String.valueOf(buyLog.getLogId()));
            TreeItem<String> commoditiesTreeItem = new TreeItem<>("Commodities");
            TreeItem<String> sellersTreeItem = new TreeItem<>("Sellers");
            buyLogTreeItem.getChildren().addAll(new TreeItem<>("Payed money: " + buyLog.getPayedMoney()),
                    new TreeItem<>("Deducted money: " + buyLog.getDeductedMoney()),
                    new TreeItem<>(buyLog.getCommodityDelivered() ? "Delivered" : "Not delivered yet"),
                    new TreeItem<>("Discount Code: " + (buyLog.getDiscountByCode() == null ? "No discount" : buyLog.
                            getDiscountByCode().getCode())),
                    new TreeItem<>("Buy date: " + buyLog.getDate().toString()),
                    commoditiesTreeItem, sellersTreeItem);
            for (Commodity commodity : buyLog.getCommodities()) {
                commoditiesTreeItem.getChildren().add(new TreeItem<>(commodity.getName()));
            }
            for (BusinessAccount seller : buyLog.getSellers()) {
                sellersTreeItem.getChildren().add(new TreeItem<>(seller.getUsername()));
            }
            ordersHistoryRootItem.getChildren().add(buyLogTreeItem);
        }
        ordersHistoryTreeView.setRoot(ordersHistoryRootItem);
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onLogOutClick(MouseEvent mouseEvent) throws Exception {
        try {
            customerMenu.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onProductsClick(MouseEvent mouseEvent) {
        //go to products page
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceLabel.setText("Your balance is " + ((PersonalAccount) Session.getOnlineAccount()).getCredit() + " Rials");
    }
}
