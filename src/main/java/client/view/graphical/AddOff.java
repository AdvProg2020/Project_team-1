package client.view.graphical;

import client.controller.reseller.ClientResellerMenu;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Commodity;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.controller.reseller.ManageResellerOffsMenu;
import server.controller.reseller.ResellerMenu;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class AddOff implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private final ResellerMenu resellerMenu = View.resellerMenu;
    private final ManageResellerOffsMenu manageResellerOffsMenu = View.manageResellerOffMenu;
    public Label errorMessageLabel;
    public DatePicker startDateDp;
    public DatePicker endDateDp;
    public Slider percentSlider;
    public Label percentLabel;
    public ListView<CheckBox> productsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int commodityId : ClientResellerMenu.getBusinessAccount().getCommoditiesId()) {
            CheckBox checkBox = null;
            try {
                outputStream.writeUTF("send commodity with id ");
                outputStream.flush();
                Commodity commodity = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Commodity>() {
                }.getType());
                checkBox = new CheckBox(commodity.getName() + " - " + commodityId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            checkBox.setId(String.valueOf(commodityId));
            productsListView.getItems().add(checkBox);
        }
        percentSlider.valueProperty().addListener((observableValue, number, t1) ->
                percentLabel.setText(String.valueOf(Math.floor((Double) t1))));
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void onAddClick(MouseEvent mouseEvent) {
        try {
            ArrayList<Commodity> commodities = new ArrayList<>();
            for (CheckBox item : productsListView.getItems()) {
                outputStream.writeUTF("send commodity with id ");
                outputStream.flush();
                commodities.add(yaGson.fromJson(inputStream.readUTF(), new TypeToken<Commodity>() {
                }.getType()));
            }
            manageResellerOffsMenu.addOff(commodities,
                    startDateDp.getValue().format(DateTimeFormatter.ofPattern("MMM d yyyy")),
                    endDateDp.getValue().format(DateTimeFormatter.ofPattern("MMM d yyyy")),
                    (int) Double.parseDouble(percentLabel.getText()));
            onCancelClick(mouseEvent);
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }
}
