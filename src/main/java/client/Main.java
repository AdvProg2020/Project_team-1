package client;

import client.view.commandline.View;

import java.io.IOException;
import java.net.Socket;

public class Main {

   public static Socket socket;

    static {
        try {
            socket = new Socket("127.0.0.1",8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new View().run();
    }
}

