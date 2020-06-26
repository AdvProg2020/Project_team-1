package view.graphical;

import controller.reseller.ManageResellerOffsMenu;
import controller.reseller.ManageResellerProductsMenu;
import controller.reseller.ResellerMenu;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.commodity.Commodity;
import view.commandline.View;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddOff implements Initializable {
    private final ResellerMenu resellerMenu = View.resellerMenu;
    private final ManageResellerOffsMenu manageResellerOffsMenu = View.manageResellerOffMenu;
    private final ManageResellerProductsMenu manageResellerProductsMenu = View.manageResellerProductsMenu;
    public Label errorMessageLabel;
    public DatePicker startDateDp;
    public DatePicker endDateDp;
    public Slider percentSlider;
    public Label percentLabel;
    public ListView<CheckBox> productsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Commodity commodity : resellerMenu.getBusinessAccount().getCommodities()) {
            CheckBox checkBox = new CheckBox(commodity.getName() + " - " + commodity.getCommodityId());
            checkBox.setId(String.valueOf(commodity.getCommodityId()));
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
                commodities.add(manageResellerProductsMenu.getCommodityById(Integer.parseInt(item.getId())));
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