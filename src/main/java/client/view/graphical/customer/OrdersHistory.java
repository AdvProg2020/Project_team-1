package client.view.graphical.customer;

import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import common.model.log.BuyLog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import server.data.YaDataManager;

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
            for (int commodityId : buyLog.getCommoditiesId()) {
                try {
                    Commodity commodity = YaDataManager.getCommodityById(commodityId);
                    TreeItem<String> commodityTreeItem = new TreeItem<>(commodity.getName());
                    if (commodity.getProductFilePathOnSellerClient() != null) {
                        TreeItem<String> downloadFile = new TreeItem<>("download");
                        downloadFile.addEventHandler(MouseEvent.MOUSE_CLICKED, this::downloadProductFile);
                        commodityTreeItem.getChildren().add(downloadFile);
                    }
                    commoditiesTreeItem.getChildren().add(commodityTreeItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String username : buyLog.getSellersUsername()) {
                sellersTreeItem.getChildren().add(new TreeItem<>(username));
            }
            ordersHistoryRootItem.getChildren().add(buyLogTreeItem);
        }
        ordersHistoryTreeView.setRoot(ordersHistoryRootItem);
    }

    private void downloadProductFile(MouseEvent mouseEvent) {
        new Thread(() -> {

        }).start();
    }

    public void onCloseClick(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
