package view.graphical.holyManager;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static view.commandline.View.manageUsersMenu;

public class DeleteAccount {
    public TextField username;

    public void delete(ActionEvent actionEvent) {
        try {
            System.out.println(username.getText());
            manageUsersMenu.deleteUser(username.getText());
            ((((Node) actionEvent.getSource()).getScene().getWindow())).hide();
        } catch (Exception e) {
            ((((Node) actionEvent.getSource()).getScene().getWindow())).hide();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }

    }
}
