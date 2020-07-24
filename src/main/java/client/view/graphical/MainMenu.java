package client.view.graphical;

import client.Session;
import client.view.AudioPlayer;
import client.view.commandline.View;
import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import client.controller.share.MenuHandler;
import server.dataManager.YaDataManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button nextImage;
    public Button backImage;
    public ImageView imageView;
    public Label label;
    public AnchorPane pane;
    private int count = 0;
    private int blockIncrement = 3;

    public static void goToUserPanel(ActionEvent actionEvent) {
        if (Session.getOnlineAccount() == null) {
            View.loginRegisterPanel.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.loginRegisterPanel);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        } else if (Session.getOnlineAccount() instanceof ManagerAccount) {
            View.managerPanel.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.managerPanel);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        } else if (Session.getOnlineAccount() instanceof PersonalAccount) {
            View.customerPanel.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.customerPanel);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        } else if (Session.getOnlineAccount() instanceof BusinessAccount) {
            View.sellerPanel.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.sellerPanel);
            Session.getSceneHandler().updateScene((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        }
    }

    public void products(ActionEvent actionEvent) {
        View.productsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.productsMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void userPanel(ActionEvent actionEvent) {
        goToUserPanel(actionEvent);
    }

    public void offs(ActionEvent actionEvent) {
        View.offMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
        MenuHandler.getInstance().setCurrentMenu(View.offMenu);
        Session.getSceneHandler().updateScene((Stage) (((Node) actionEvent.getSource()).getScene().getWindow()));
    }

    public void pause(ActionEvent actionEvent) {
        AudioPlayer.mediaPlayer.pause();
    }

    public void play(ActionEvent actionEvent) {
        AudioPlayer.mediaPlayer.play();
    }

    public void previousImage(ActionEvent actionEvent) {
        try {
            if (YaDataManager.getCommodities().size() == 0)
                return;
            if (YaDataManager.getCommodities().size() >= blockIncrement) {
                if (count == YaDataManager.getCommodities().size() - blockIncrement)
                    count = YaDataManager.getCommodities().size() - 1;
                else count--;
            } else {
                if (count == 0)
                    count = YaDataManager.getCommodities().size() - 1;
                else count--;
            }
            setImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next(ActionEvent actionEvent) {
        try {
            if (YaDataManager.getCommodities().size() == 0)
                return;
            if (YaDataManager.getCommodities().size() >= blockIncrement) {
                if (count == YaDataManager.getCommodities().size() - 1)
                    count = YaDataManager.getCommodities().size() - blockIncrement;
                else count++;
            } else {
                if (count == YaDataManager.getCommodities().size() - 1)
                    count = 0;
                else count++;
            }
            setImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (YaDataManager.getCommodities().size() > 0) {
                if (YaDataManager.getCommodities().size() >= blockIncrement) {
                    count = YaDataManager.getCommodities().size() - blockIncrement;
                }
                setImage();
            } else label.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setImage() throws IOException {
//        FileInputStream inputStream = new FileInputStream(YaDataManager.getCommodities().get(count).getImagePath());
//        Image image = new Image(inputStream);
//        ImageView imageViewTmp = new ImageView(image);
//        imageViewTmp.setFitHeight(222);
//        imageViewTmp.setFitWidth(333);
//        imageViewTmp.relocate(272, 64);
//        pane.getChildren().add(imageViewTmp);
//        pane.getChildren().remove(imageView);
//        pane.getChildren().add(imageView);
//        label.setVisible(false);
//
//        Timeline timeLine = new Timeline();
//        KeyValue keyValue = new KeyValue(imageView.translateYProperty() , 200 , Interpolator.EASE_IN);
//        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1),keyValue);
//        timeLine.getKeyFrames().add(keyFrame);
//        timeLine.setOnFinished(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                pane.getChildren().remove(imageView);
//                imageView = imageViewTmp ;
//                pane.getChildren().remove(imageViewTmp);
//                pane.getChildren().add(imageView);
//            }
//        });
//        timeLine.play();
    }
}
