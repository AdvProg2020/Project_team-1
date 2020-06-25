package view;

import javafx.scene.media.AudioClip;

public class AudioPlayer {

    public static AudioClip audioClip;
    public static int count = 1;
    private static String[] paths = new String[10];

    static {
        for (int i = 1 ; i <= 8 ; i++){
            paths[i] = "../Songs/" + i + ".mp3";
        }
        music();
    }

    public static void music(){
        System.out.println(count);
        audioClip = new AudioClip(AudioPlayer.class.getResource(paths[count]).toString());
        audioClip.setVolume(0.025);
        audioClip.play();
        System.out.println("salam");
    }
}
