package client;

import client.view.commandline.View;

import java.io.*;
import java.net.Socket;

public class Main {

   public static Socket socket;
   public static Socket socketB;
   public static DataInputStream inputStream;
   public static DataOutputStream outputStream;

    static {
        try {
            socket = new Socket("127.0.0.1",8888);
            socketB = new Socket("127.0.0.1" , 9999);
            DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
            dataInputStream.readUTF();
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}

