package client.view.graphical;

import client.controller.reseller.ClientResellerMenu;
import javafx.event.ActionEvent;
import server.controller.reseller.ResellerMenu;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import client.Session;
import common.model.account.BusinessAccount;
import client.view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Reseller implements Initializable {

    public Label resellerBalanceLabel;
    public final ResellerMenu resellerMenu = View.resellerMenu;
    Popup popupMenu = new Popup();

    public void onPersonalInfoClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/customer/ViewInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCompanyInfoClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/reseller/CompanyInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onSalesHistoryClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/reseller/SalesHistoryPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onCategoriesClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../fxml/CategoriesPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().clear();
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onManageProductsClick(MouseEvent mouseEvent) {
        try {
            ClientResellerMenu.manageCommodities();
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
            ClientResellerMenu.manageOffs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onMainMenuClick(MouseEvent mouseEvent) {
        resellerMenu.goToMainMenu(null);
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onProductsClick(MouseEvent mouseEvent) {
        View.manageResellerOffMenu.products();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    protected void newPopup(ActionEvent actionEvent, String filePath) {
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show((((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void wallet(ActionEvent actionEvent) {
       newPopup(actionEvent , "../../../fxml/Wallet.fxml");
    }

    public void addToAuction(ActionEvent actionEvent) {
        newPopup(actionEvent, "../../../fxml/reseller/Auction.fxml");
    }
}
