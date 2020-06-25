package view.graphical.customer;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Session;
import model.account.BusinessAccount;
import model.account.PersonalAccount;
import model.commodity.Commodity;
import model.log.BuyLog;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersHistory implements Initializable {
    public TreeView<String> ordersHistoryTreeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> ordersHistoryRootItem = new TreeItem<>("Orders history");
        for (BuyLog buyLog : ((PersonalAccount) Session.getOnlineAccount()).getBuyLogs()) {
            TreeItem<String> buyLogTreeItem = new TreeItem<>(String.valueOf(buyLog.getLogId()));
            TreeItem<String> commoditiesTreeItem = new TreeItem<>("Commodities");
            TreeItem<String> sellersTreeItem = new TreeItem<>("Sellers");
            buyLogTreeItem.getChildren().addAll(new TreeItem<>("Payed money: " + buyLog.getPayedMoney()),
                    new TreeItem<>("Deducted money: " + buyLog.getDeductedMoney()),
                    new TreeItem<>(buyLog.getCommodityDelivered() ? "Delivered" : "Not delivered yet"),
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
    }

    public void onCloseClick(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
