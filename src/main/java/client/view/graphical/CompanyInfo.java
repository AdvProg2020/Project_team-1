package client.view.graphical;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import client.Session;
import common.model.account.BusinessAccount;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyInfo implements Initializable {
    public Label businessNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        businessNameLabel.setText(((BusinessAccount) Session.getOnlineAccount()).getBusinessName());
    }

    public void onCloseClick(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
