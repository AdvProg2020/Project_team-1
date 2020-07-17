package client.view.graphical.customer;

import client.Session;
import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import server.dataManager.YaDataManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscountCodes implements Initializable {
    public TreeView<String> discountCodesTreeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> discountsRootItem = new TreeItem<>("Discount codes");
        for (String code : ((PersonalAccount) Session.getOnlineAccount()).getDiscountCodes()) {
            TreeItem<String> discountCodesTreeItem = new TreeItem<>(String.valueOf(code));
            DiscountCode discountCode = null;
            try {
                discountCode = YaDataManager.getDiscountCodeWithCode(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
            discountCodesTreeItem.getChildren().addAll(
                    new TreeItem<>("Discount price limit: " + discountCode.getMaximumDiscountPrice()),
                    new TreeItem<>("Discount Percentage: " + discountCode.getDiscountPercentage()),
                    new TreeItem<>("Start Date: " + discountCode.getStartDate().toString()),
                    new TreeItem<>("Finish date: " + discountCode.getFinishDate().toString()),
                    new TreeItem<>("Number of uses limit: " + discountCode.getMaximumNumberOfUses()),
                    new TreeItem<>("Number of times used: " +
                            ((PersonalAccount) Session.getOnlineAccount()).getNumberOfTimesUsed(discountCode)));
            discountsRootItem.getChildren().add(discountCodesTreeItem);
        }
        discountCodesTreeView.setRoot(discountsRootItem);
    }

    public void onCloseClick(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
