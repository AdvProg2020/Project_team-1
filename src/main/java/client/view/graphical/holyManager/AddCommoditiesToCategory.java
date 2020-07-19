package client.view.graphical.holyManager;

import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Commodity;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class AddCommoditiesToCategory implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private static ArrayList<Commodity> commodities = new ArrayList<>();
    public AnchorPane pane;
    ListView<CheckBox> checkBoxListView = new ListView<>();

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
            outputStream.writeUTF("send all commodities");
            outputStream.flush();
            ArrayList<Commodity> commodities = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Commodity>>() {
            }.getType());
            for (Commodity commodity : commodities) {
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
