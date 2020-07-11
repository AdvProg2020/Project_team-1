package server;

import client.view.commandline.View;
import common.model.account.SupportAccount;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Socket> sockets = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            Socket socket = server.accept();
            handleClient(socket);
        }
    }

    public static void handleClient(Socket socket) {
        new Thread(() -> {
            while (true) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    String input = dataInputStream.readUTF();
                    System.out.println(input);
                    handleInput(input , socket);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void handleInput(String input , Socket socket) throws IOException, ClassNotFoundException {
        if (input.equalsIgnoreCase("new support account")){
            System.out.println("salm");
            newSupportAccount(socket);
        }
    }

    public static void newSupportAccount(Socket socket) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("Send object");
        dataOutputStream.flush();
        ObjectInputStream objectInputStream = new ObjectInputStream((socket.getInputStream()));
        SupportAccount supportAccount = (SupportAccount) objectInputStream.readObject();
        try {
            View.manageUsersMenu.createNewSupport(supportAccount);
            dataOutputStream.writeUTF("Support account successfully added");
            dataOutputStream.flush();
        } catch (Exception e) {
            dataOutputStream.writeUTF(e.getMessage());
            dataOutputStream.flush();
        }
    }
}
