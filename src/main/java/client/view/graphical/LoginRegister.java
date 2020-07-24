package client.view.graphical;

import client.Session;
import client.controller.share.ClientLoginRegisterMenu;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.Constants;
import common.model.account.BusinessAccount;
import common.model.account.SimpleAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import server.controller.share.LoginRegisterMenu;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.Main.socket;

public class LoginRegister implements Initializable {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public TextField registerBusinessNameTf;
    public ChoiceBox<String> accountType;
    public Label loginMessageLabel;
    public TextField loginUsernameTf;
    public PasswordField loginPasswordTf;
    public Button loginButton;
    public TextField registerUsernameTf;
    public TextField registerFirstNameTf;
    public TextField registerLastNameTf;
    public TextField registerPhoneNumberTf;
    public TextField registerEmailTf;
    public PasswordField registerPassword;
    public Label registerMessageLabel;
    public Button registerButton;
    public ImageView userPhotoImageView;
    public TextField bankUserName;
    public PasswordField bankPassword;
    private String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAccountTypeChoiceBox();
    }

    private void initializeAccountTypeChoiceBox() {
        List<String> accountTypesList = new ArrayList<>(Arrays.asList("personal", "reseller", "manager"));
        ObservableList<String> observableList = FXCollections.observableList(accountTypesList);
        accountType.setItems(observableList);
        registerBusinessNameTf.setDisable(true);
        accountType.valueProperty().addListener(
                (observableValue, s, t1) -> registerBusinessNameTf.setDisable(!t1.equals("reseller")));

    }

    public void onBackButtonClick(MouseEvent mouseEvent) {
        View.loginRegisterPanel.goToPreviousMenu();
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onLoginButtonClick(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        System.out.println(socket);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("login " + loginUsernameTf.getText() + " " + loginPasswordTf.getText());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        System.out.println(socket);
        String input = dataInputStream.readUTF();
        System.out.println(input);
        if (input.startsWith("error:")) {
            loginMessageLabel.setText(input.split(":")[1]);
            return;
        }
        SimpleAccount simpleAccount = yaGson.fromJson(input, new TypeToken<SimpleAccount>() {
        }.getType());
        ClientLoginRegisterMenu.login(simpleAccount);
        if (simpleAccount instanceof BusinessAccount) {
            new SetupFileClient(simpleAccount).start();
        }
        Session.getSceneHandler().updateScene((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {
        if (accountType.getValue() == null) {
            registerMessageLabel.setText("Select an account type");
            return;
        }
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Register");
        String information;
        switch (accountType.getValue()) {
            case "personal":
                information = "personal " + registerUsernameTf.getText() + " " +
                        registerFirstNameTf.getText() + " " + registerLastNameTf.getText() + " " + registerEmailTf.getText() + " " +
                        registerPhoneNumberTf.getText() + " " + registerPassword.getText() + " " + imagePath;
                dataOutputStream.writeUTF(information);
                break;

            case "reseller":
                information = "business " + registerUsernameTf.getText() + " " +
                        registerFirstNameTf.getText() + " " + registerLastNameTf.getText() + " " + registerEmailTf.getText() + " " +
                        registerPhoneNumberTf.getText() + " " + registerPassword.getText() + " " + registerBusinessNameTf.getText() + " " + imagePath;
                dataOutputStream.writeUTF(information);
                dataOutputStream.flush();
                break;

            case "manager":
                information = "manager " + registerUsernameTf.getText() + " " +
                        registerFirstNameTf.getText() + " " + registerLastNameTf.getText() + " " + registerEmailTf.getText() + " " +
                        registerPhoneNumberTf.getText() + " " + registerPassword.getText() + " " + imagePath;
                dataOutputStream.writeUTF(information);
                dataOutputStream.flush();
        }
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String respond = dataInputStream.readUTF();
        registerMessageLabel.setText(respond);
        if (respond.equals("You have registered successfully.") && (!accountType.getValue().equals("manager"))) {
            newPopup(actionEvent, "../../../fxml/RegisterBankAccount.fxml");
        }
    }

    public void onPickAPhotoClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image file", "*.jpg",
                "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        imagePath = file.getAbsolutePath();
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userPhotoImageView.setImage(image);
    }

    public void onExitButtonClick() {
        System.exit(0);
    }

    private void newPopup(ActionEvent actionEvent, String filePath) {
        Parent parent = null;
        Popup popupMenu = new Popup();
        try {
            parent = FXMLLoader.load(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        popupMenu.getContent().add(parent);
        popupMenu.show((((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    private static class SetupFileClient extends Thread {

        private SimpleAccount simpleAccount;

        public SetupFileClient(SimpleAccount simpleAccount) {
            this.simpleAccount = simpleAccount;
        }

        @Override
        public void run() {
            try {
                Socket fileTransferSocket = new Socket(Constants.SERVER_IP, Constants.FILE_SERVER_PORT);
                DataOutputStream outputStream = new DataOutputStream(fileTransferSocket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(fileTransferSocket.getInputStream());
                outputStream.writeUTF("add me " + simpleAccount.getUsername());
                Pattern sendFilePattern = Pattern.compile("^send #(?<filePath>.+)# to" +
                        " (?<hostIp>\\d+\\.\\d+\\.\\d+\\.\\d+):(?<port>\\d+)$");
                while (true) {
                    String command = inputStream.readUTF();
                    Matcher matcher = sendFilePattern.matcher(command);
                    if (matcher.matches()) {
                        File file = new File(matcher.group("filePath"));
                        if (!file.exists()) {
                            outputStream.writeUTF("File not found");
                            return;
                        }
                        long fileSize = file.length();
                        outputStream.writeUTF("Sender is ready to send file with size " + fileSize);
                        String confirmation = inputStream.readUTF();
                        if (confirmation.equals("send now")) {
                            Socket fileSocket = new Socket(matcher.group("hostIp"),
                                    Integer.parseInt(matcher.group("port")));
                            byte[] buffer = new byte[Constants.FILE_BUFFER_SIZE];
                            FileInputStream fileInputStream = new FileInputStream(file);
                            DataOutputStream fileOutputStream = new DataOutputStream(fileSocket.getOutputStream());
                            while (fileInputStream.read(buffer) > 0) {
                                fileOutputStream.write(buffer);
                            }
                            fileSocket.close();
                            fileInputStream.close();
                        }
                    } else {
                        outputStream.writeUTF("invalid request");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
