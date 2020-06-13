package view.graphical;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import model.Session;
import model.account.BusinessAccount;
import model.log.SellLog;

import java.io.IOException;

public class Reseller {

    public Label businessNameLabel;
    public Label tableViewPopupTitleLabel;
    public Label salesHistoryPopupTitleLabel;
    public TableView salesHistoryTableView;
    Popup popupMenu = new Popup();

    public void onPersonalInfoClick(MouseEvent mouseEvent) {

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

    public void onFirstNameClick(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../fxml/CompanyInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        businessNameLabel.setText(((BusinessAccount) Session.getOnlineAccount()).getBusinessName());
        popupMenu.getContent().add(parent);
        popupMenu.show(((Node) mouseEvent.getSource()).getScene().getWindow());
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
}
