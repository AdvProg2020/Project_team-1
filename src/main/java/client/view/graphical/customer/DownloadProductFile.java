package client.view.graphical.customer;

import client.Main;
import common.Constants;
import common.model.commodity.Commodity;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            int port = getFileServerPort();
            if (port == -1) {
                messageToUser.setText("Error : no free port found on your system to receive file");
                return;
            }
            Socket fileDataSocket = new Socket(Constants.SERVER_IP, Constants.FILE_SERVER_PORT);
            DataOutputStream outputStream = new DataOutputStream(fileDataSocket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(fileDataSocket.getInputStream());
            outputStream.writeUTF("get file #" + commodity.getProductFilePathOnSellerClient()
                    + "# from " + commodity.getSellerUsername() + " now listening on port" + port);
            String response = inputStream.readUTF();
            messageToUser.setText(response);
            if (response.equals("Sending file now")) {
                new File("Downloads").mkdir();
                FileOutputStream fileOutputStream = new FileOutputStream("Downloads/"+fileName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getFileServerPort() {
        ServerSocket serverSocket = null;
        int port = 1024;
        do {
            try {
                serverSocket = new ServerSocket(1024);
                serverSocket.close();
                return port;
            } catch (IOException e) {
                //BK
            }
            ++port;
        } while (port <= 49151);
        return -1;
    }

    public void onCancelClicked(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void onDoneClicked(MouseEvent mouseEvent) {
        onCancelClicked(mouseEvent);
    }
}
