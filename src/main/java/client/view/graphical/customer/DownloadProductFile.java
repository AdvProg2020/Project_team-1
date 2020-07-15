package client.view.graphical.customer;

import client.Main;
import common.model.commodity.Commodity;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadProductFile {

    public Label infoLabel;
    public ProgressBar progressBar;
    public Label messageToUser;
    private Commodity commodity;

    public void initialize(Commodity commodity) {
        this.commodity = commodity;
        String fileName = commodity.getProductFilePathOnSellerClient().substring(
              commodity.getProductFilePathOnSellerClient().lastIndexOf("\\") + 1); //for windows
        infoLabel.setText("Getting " + fileName + " from " + commodity.getSellerUsername());
        //getFile(fileName);
    }

    private void getFile(String fileName) {
        try {
            Main.outputStream.writeUTF("get_file #" + commodity.getProductFilePathOnSellerClient()
                    + "# from " + commodity.getSellerUsername());
            Main.outputStream.flush();
            String response = Main.inputStream.readUTF();
            messageToUser.setText(response);
            if (response.equals("Sending file now")) {
                new File("Downloads").mkdir();
                FileOutputStream fileOutputStream = new FileOutputStream("Downloads/"+fileName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCancelClicked(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onDoneClicked(MouseEvent mouseEvent) {
        onCancelClicked(mouseEvent);
    }
}
