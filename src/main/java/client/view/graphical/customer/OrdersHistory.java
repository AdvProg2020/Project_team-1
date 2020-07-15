package client.view.graphical.customer;

import client.Main;
import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.Commodity;
import common.model.log.BuyLog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import server.data.YaDataManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrdersHistory implements Initializable {
    public TreeView<Node> ordersHistoryTreeView;
    private Popup popup;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<Node> ordersHistoryRootItem = new TreeItem<>(new Label("Orders history"));
        for (BuyLog buyLog : ((PersonalAccount) Session.getOnlineAccount()).getBuyLogs()) {
            TreeItem<Node> buyLogTreeItem = new TreeItem<>(new Label(String.valueOf(buyLog.getLogId())));
            TreeItem<Node> commoditiesTreeItem = new TreeItem<>(new Label("Commodities"));
            TreeItem<Node> sellersTreeItem = new TreeItem<>(new Label("Sellers"));
            buyLogTreeItem.getChildren().addAll(new TreeItem<>(new Label("Payed money: " + buyLog.getPayedMoney())),
                    new TreeItem<>(new Label("Deducted money: " + buyLog.getDeductedMoney())),
                    new TreeItem<>(new Label(buyLog.getCommodityDelivered() ? "Delivered" : "Not delivered yet")),
                    new TreeItem<>(new Label("Buy date: " + buyLog.getDate().toString())),
                    commoditiesTreeItem, sellersTreeItem);
            for (int commodityId : buyLog.getCommoditiesId()) {
                try {
                    Commodity commodity = YaDataManager.getCommodityById(commodityId);
                    TreeItem<Node> commodityTreeItem = new TreeItem<>(new Label(commodity.getName()));
                    if (commodity.getProductFilePathOnSellerClient() != null) {
                        Button downloadButton = new Button("Download product file");
                        TreeItem<Node> downloadFile = new TreeItem<>(downloadButton);
                        downloadButton.setOnMouseClicked(mouseEvent -> {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                                    "../../../../fxml/customer/DownloadProductFile.fxml"));
                            Parent root = null;
                            try {
                                root = fxmlLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            DownloadProductFile controller = fxmlLoader.getController();
                            controller.initialize(commodity);
                            popup.getContent().clear();
                            popup.getContent().add(root);
                            popup.show(stage);
                        });
                        commodityTreeItem.getChildren().add(downloadFile);
                    }
                    commoditiesTreeItem.getChildren().add(commodityTreeItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String username : buyLog.getSellersUsername()) {
                sellersTreeItem.getChildren().add(new TreeItem<>(new Label(username)));
            }
            ordersHistoryRootItem.getChildren().add(buyLogTreeItem);
        }
        ordersHistoryTreeView.setRoot(ordersHistoryRootItem);
    }

    public void onCloseClick(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
