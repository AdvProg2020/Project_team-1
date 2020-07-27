package client;

import common.Constants;

import java.io.*;
import java.net.Socket;

public class Main {

    public static Socket socket;
    public static Socket socketB;
    public static DataInputStream inputStream;
    public static DataOutputStream outputStream;

    static {
        try {
            socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            socketB = new Socket(Constants.BANK_SERVER_IP, Constants.BANK_SERVER_PORT);
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

