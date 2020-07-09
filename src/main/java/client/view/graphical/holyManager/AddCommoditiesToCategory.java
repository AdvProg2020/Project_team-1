package client.view.graphical.holyManager;

import server.data.YaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import common.model.commodity.Commodity;
import client.view.commandline.View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCommoditiesToCategory implements Initializable {

    public AnchorPane pane;
    ListView<CheckBox> checkBoxListView = new ListView<>();
    private static ArrayList<Commodity> commodities = new ArrayList<>();

    public static ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public static void setCommodities(ArrayList<Commodity> commodities) {
        AddCommoditiesToCategory.commodities = commodities;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(checkBoxListView);
        try {
            for (Commodity commodity : YaDataManager.getCommodities()) {
                CheckBox checkBox = new CheckBox(commodity.getInformation());
                checkBox.setId(String.valueOf(commodity.getCommodityId()));
                if (doesCategoryHaveThisCommodity(commodity))
                    checkBox.setSelected(true);
                checkBoxListView.getItems().add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCommodities(ActionEvent actionEvent) {
        for (CheckBox item : checkBoxListView.getItems()) {
            try {
                commodities.add(View.productsMenu.getProducts(Integer.parseInt(item.getId())));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public boolean doesCategoryHaveThisCommodity(Commodity commodity) {
        for (Commodity commodity1 : commodities) {
            if (commodity1.getCommodityId() == commodity.getCommodityId())
                return true;
        }
        return false;
    }
}
