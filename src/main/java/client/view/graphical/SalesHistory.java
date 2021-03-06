package client.view.graphical;

import client.Session;
import common.model.account.BusinessAccount;
import common.model.log.SellLog;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import server.dataManager.YaDataManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesHistory implements Initializable {
    public TreeView<String> salesHistoryTreeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> salesHistoryRootItem = new TreeItem<>("Sales history");
        for (SellLog sellLog : ((BusinessAccount) Session.getOnlineAccount()).getSellLogs()) {
            TreeItem<String> sellLogTreeItem = new TreeItem<>(String.valueOf(sellLog.getLogId()));
            TreeItem<String> commoditiesTreeItem = new TreeItem<>("Commodities");
            sellLogTreeItem.getChildren().addAll(new TreeItem<>("Received money : " + sellLog.getReceivedMoney()),
                    new TreeItem<>("Deducted money on sale : " + sellLog.getDeductedMoneyOnSale()),
                    new TreeItem<>("Buyer : " + sellLog.getBuyerUsername()),
                    new TreeItem<>(sellLog.getCommodityDelivered() ? "Delivered" : "Not delivered yet"),
                    new TreeItem<>("Sell date : " + sellLog.getDate().toString()),
                    commoditiesTreeItem);
            for (int commodityId : sellLog.getCommoditiesId()) {
                try {
                    commoditiesTreeItem.getChildren().add(new TreeItem<>(YaDataManager.getCommodityById(commodityId).getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            salesHistoryRootItem.getChildren().add(sellLogTreeItem);
        }
        salesHistoryTreeView.setRoot(salesHistoryRootItem);
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
