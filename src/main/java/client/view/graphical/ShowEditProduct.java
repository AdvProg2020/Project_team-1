package client.view.graphical;

import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.field.Field;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import client.controller.reseller.ManageResellerProductsMenu;
import server.dataManager.YaDataManager;
import static client.Main.socket;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ShowEditProduct {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public ToggleButton productAvailability;
    private ManageResellerProductsMenu manageResellerProductsMenu = View.manageResellerProductsMenu;
    private Commodity commodity;
    private Stage stage;
    public Label brandLabel;
    public Label nameLabel;
    public Label priceLabel;
    public Label descriptionLabel;
    public Label amountLabel;
    public Label errorMessageLabel;
    private Label changeLabel;
    private ArrayList<Field> categorySpecification;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initScene(Commodity commodity) {
        categorySpecification = commodity.getCategorySpecifications();
        this.commodity = commodity;
        brandLabel.setText(commodity.getBrand());
        nameLabel.setText(commodity.getName());
        priceLabel.setText(String.valueOf(commodity.getPrice()));
        descriptionLabel.setText(commodity.getDescription());
        amountLabel.setText(String.valueOf(commodity.getInventory()));
        productAvailability.setSelected(commodity.getCommodityAvailable());
    }

    public void onCancelClick(MouseEvent mouseEvent) {
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void onSaveClick(MouseEvent mouseEvent) {
        try {
            manageResellerProductsMenu.editProduct(commodity, brandLabel.getText(), nameLabel.getText(),
                    Integer.parseInt(priceLabel.getText()), productAvailability.isSelected(),
                    Objects.requireNonNull(YaDataManager.getCategoryWithName(commodity.getCategoryName())),
                    categorySpecification, descriptionLabel.getText(), Integer.parseInt(amountLabel.getText()));
        } catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
        onCancelClick(mouseEvent);
    }

    public void onBrandLabelClick() {
        changeLabel = brandLabel;
        openTextFieldPopup();
    }

    public void onNameLabelClick() {
        changeLabel = nameLabel;
        openTextFieldPopup();
    }

    public void onPriceLabelClick() {
        changeLabel = priceLabel;
        openTextFieldPopup();
    }

    public void onDescriptionLabelClick() {
        changeLabel = descriptionLabel;
        openTextFieldPopup();
    }

    public void onAmountLabelClick() {
        changeLabel = amountLabel;
        openTextFieldPopup();
    }

    private void openTextFieldPopup() {
        Popup popup = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/TextFieldPopup.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            // What should I do? If I suddenly feel ...
        }
        TextFieldPopup textFieldPopup = loader.getController();
        textFieldPopup.setShowEditProduct(this);
        popup.getContent().add(parent);
        popup.show(stage);
    }

    public void setNewValue(String value) {
        changeLabel.setText(value);
    }

    public void onEditCategorySpecificationClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("../../../fxml/reseller/EditCategorySpecification.fxml"));
        Parent parent = null;
        Popup popup = new Popup();
        try {
            parent = loader.load();
        } catch (IOException e) {
            // Sorry. I can not do anything :(
        }
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF("name of category is " + commodity.getCategoryName());
        dos.flush();
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        Category category = yaGson.fromJson(dis.readUTF(), new TypeToken<Category>(){}.getType());
        EditCategorySpecs editCategorySpecs = loader.getController();
        editCategorySpecs.setShowEditProduct(this);
        editCategorySpecs.initVBox(commodity.getCategorySpecifications(), category.getFieldOptions());
        popup.getContent().add(parent);
        popup.show(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void setCategorySpecification(ArrayList<Field> categorySpecification) {
        this.categorySpecification = categorySpecification;
    }
}
