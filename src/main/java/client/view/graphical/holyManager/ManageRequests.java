package client.view.graphical.holyManager;

import server.dataManager.YaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import common.model.share.Request;
import common.model.share.Status;
import client.view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageRequests extends HolyManager implements Initializable {
    public AnchorPane pane;
    ListView<CheckBox> acceptedRequests = new ListView<>();
    ListView<CheckBox> declinedRequests = new ListView<>();
    ListView<CheckBox> unhandledRequests = new ListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unhandledRequests.setPrefHeight(200);
        unhandledRequests.setPrefWidth(300);
        acceptedRequests.setPrefHeight(200);
        acceptedRequests.setPrefWidth(300);
        declinedRequests.setPrefWidth(300);
        declinedRequests.setPrefHeight(200);
        unhandledRequests.relocate(255, 0);
        acceptedRequests.relocate(630, 0);
        declinedRequests.relocate(1005, 0);
        pane.getChildren().add(declinedRequests);
        pane.getChildren().add(acceptedRequests);
        pane.getChildren().add(unhandledRequests);

        try {
            setUpPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpPane() throws IOException {
        unhandledRequests.getItems().removeAll(unhandledRequests.getItems());
        acceptedRequests.getItems().removeAll(acceptedRequests.getItems());
        declinedRequests.getItems().removeAll(declinedRequests.getItems());
        for (Request request : YaDataManager.getRequests()) {
            if (request.getObj().getStatus().equals(Status.VERIFIED)) {
                acceptedRequests.getItems().add(getCheckBox(request));
            } else if (request.getObj().getStatus().equals(Status.DECLINED)) {
                declinedRequests.getItems().add(getCheckBox(request));
            } else if (request.getObj().getStatus().equals(Status.UNDER_CHECKING_FOR_CREATE)) {
                unhandledRequests.getItems().add(getCheckBox(request));
            }
        }
    }

    public CheckBox getCheckBox(Request request) {
        CheckBox checkBox = new CheckBox(request.toString());
        checkBox.setId(String.valueOf(request.getId()));
        return checkBox;
    }


    public void deleteRequest(ActionEvent actionEvent) throws IOException {
        for (CheckBox item : acceptedRequests.getItems()) {
            deleteSelectedItem(item);
        }
        for (CheckBox item : declinedRequests.getItems()) {
            deleteSelectedItem(item);
        }
        setUpPane();
    }

    public void deleteSelectedItem(CheckBox item) {
        if (item.isSelected()) {
            try {
                View.manageRequestMenu.deleteRequest(Integer.parseInt(item.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptRequests(ActionEvent actionEvent) {
        try {
            for (CheckBox item : unhandledRequests.getItems()) {
                if (item.isSelected()) {
                    View.manageRequestMenu.accept(Integer.parseInt(item.getId()));
                }
            }
            setUpPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declineRequests(ActionEvent actionEvent) {
        try {
            for (CheckBox item : unhandledRequests.getItems()) {
                if (item.isSelected()) {
                    View.manageRequestMenu.decline(Integer.parseInt(item.getId()));
                }
            }
            setUpPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
