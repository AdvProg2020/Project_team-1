package client.view.graphical;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Commodity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class Auction implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public ChoiceBox<String> commoditiesList;
    public TextField deadlineTF;
    public Label infoLabel;
    public Button addButton;
    private ArrayList<Commodity> commodities;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            outputStream.writeUTF("has an auction?");
            outputStream.flush();
            if (inputStream.readUTF().equals("yes")) {
                commoditiesList.setDisable(true);
                deadlineTF.setDisable(true);
                infoLabel.setText("You have an active auction, you can't make another one right now");
                addButton.setDisable(true);
                return;
            }
            outputStream.writeUTF("send seller commodities");
            outputStream.flush();
            commodities = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Commodity>>() {
            }.getType());
            ArrayList<String> commoditiesInfo = new ArrayList<>();
            for (Commodity commodity : commodities) {
                commoditiesInfo.add("Name: " + commodity.getName() + " ,Brand: " + commodity.getBrand());
            }
            ObservableList<String> observableList = FXCollections.observableList(commoditiesInfo);
            commoditiesList.setItems(observableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void addAuction(ActionEvent actionEvent) throws IOException {
        if (commoditiesList.getValue() == null) {
            infoLabel.setText("Select a commodity");
            return;
        }
        String date = deadlineTF.getText();
        if (!date.matches("\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            infoLabel.setText("Enter a valid deadline");
            return;
        }
        int day = Integer.parseInt(date.split(" ")[0].split("-")[0]);
        int month = Integer.parseInt(date.split(" ")[0].split("-")[1]);
        int year = Integer.parseInt(date.split(" ")[0].split("-")[2]);
        int hour = Integer.parseInt(date.split(" ")[1].split(":")[0]);
        int minute = Integer.parseInt(date.split(" ")[1].split(":")[1]);
        int second = Integer.parseInt(date.split(" ")[1].split(":")[2]);
        Date deadline;
        try {
            deadline = new Date(year, month, day, hour, minute, second);
        } catch (Exception e) {
            infoLabel.setText("Enter a valid deadline");
            return;
        }
        if (new Date(deadline.getTime() - 10000).before(new Date())) {
            infoLabel.setText("Enter a valid deadline");
            return;
        }
        for (Commodity commodity : commodities) {
            if (commoditiesList.getValue().equals("Name: " + commodity.getName() + " ,Brand: " + commodity.getBrand())) {
                outputStream.writeUTF("new auction " + commodity.getCommodityId() +
                        " deadline " + yaGson.toJson(deadline, new TypeToken<Date>() {
                }.getType()));
                outputStream.flush();
                return;
            }
        }
    }
}
