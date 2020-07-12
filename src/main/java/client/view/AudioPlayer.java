package client.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {

    public static MediaPlayer mediaPlayer;
    public static int count = 1;
    private static String[] paths = new String[10];

    static {
        for (int i = 1 ; i <= 8 ; i++){
            paths[i] = "../../Songs/" + i + ".mp3";
        }
        music();
    }

    public static void music(){
        System.out.println(count);
        Media media =  new Media(AudioPlayer.class.getResource(paths[count]).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.025);
        //mediaPlayer.play();
        System.out.println("salam");
    }
}
