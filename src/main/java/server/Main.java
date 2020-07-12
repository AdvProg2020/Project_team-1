package server;

import client.view.commandline.View;
import common.model.account.SimpleAccount;
import common.model.account.SupportAccount;
import server.data.YaDataManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static HashMap<Socket, String> onlineAccountsUsernames = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            System.out.println("khobi");
            Socket socket = server.accept();
            System.out.println("salam");
            sockets.add(socket);
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
                    handleInput(input, socket);
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        }).start();
    }

    public static void handleInput(String input, Socket socket) throws IOException, ClassNotFoundException {
        if (input.equalsIgnoreCase("new support account")) {
            newSupportAccount(socket);
        } else if (input.equalsIgnoreCase("get online accounts")) {
            sendOnlineAccounts(socket);
        } else if (input.startsWith("account logged in:")) {
            updateOnlineAccounts(input, socket);
        } else if (input.startsWith("Chat between:")) {
            chat(input, socket);
        } else if (input.startsWith("Start chat")) {
            chatSupport(input, socket);
        }
    }

    public static void chat(String input, Socket simpleAccountSocket) throws IOException {
        String[] splitInput = input.split(" ");
        Socket supportAccountSocket = null;
        supportAccountSocket = getSocket(splitInput, supportAccountSocket, 3);
        DataOutputStream dataOutputStreamSupportAccount = new DataOutputStream(new BufferedOutputStream(supportAccountSocket.getOutputStream()));
        dataOutputStreamSupportAccount.writeUTF("new chat with: " + splitInput[2]);
        dataOutputStreamSupportAccount.flush();
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(simpleAccountSocket.getInputStream()));
        while (true) {
            String message = dataInputStream.readUTF();
            dataOutputStreamSupportAccount.writeUTF(message);
            dataOutputStreamSupportAccount.flush();
            if (message.startsWith("end chat")) {
                break;
            }
        }
    }

    public static void updateOnlineAccounts(String input, Socket socket) {
        String[] splitInput = input.split(" ");
        onlineAccountsUsernames.put(socket, splitInput[3]);
    }

    public static void sendOnlineAccounts(Socket socket) throws IOException {
        String usernamesString = "";
        for (Socket socket1 : onlineAccountsUsernames.keySet()) {
            usernamesString += onlineAccountsUsernames.get(socket1) + "\n";
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        System.out.println(onlineAccountsUsernames + "l");
        dataOutputStream.writeUTF(usernamesString);
        dataOutputStream.flush();
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

    public static void chatSupport(String input, Socket supportAccountSocket) throws IOException {
        String[] splitInput = input.split(" ");
        Socket simpleAccountSocket = null;
        simpleAccountSocket = getSocket(splitInput, simpleAccountSocket, 3);
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(supportAccountSocket.getInputStream()));
        while (true) {
            String message = dataInputStream.readUTF();
            String[] splitMessage = message.split(" ");
            if (message.startsWith("new chat")) {
                simpleAccountSocket = getSocket(splitInput, simpleAccountSocket, 3);
                message = getString(splitInput, splitMessage , 4);
            } else {
                simpleAccountSocket = getSocket(splitMessage, simpleAccountSocket, 0);
                message = getString(splitInput, splitMessage , 1);
            }
            DataOutputStream dataOutputStreamSupportAccount = new DataOutputStream(new BufferedOutputStream(simpleAccountSocket.getOutputStream()));
            System.out.println("Chat support");
            dataOutputStreamSupportAccount.writeUTF(message);
            dataOutputStreamSupportAccount.flush();
        }
    }

    public static Socket getSocket(String[] splitInput, Socket simpleAccountSocket, int i) {
        for (Socket socket : onlineAccountsUsernames.keySet()) {
            if (onlineAccountsUsernames.get(socket).equals(splitInput[i]))
                simpleAccountSocket = socket;
        }
        return simpleAccountSocket;
    }

    public static String getString(String[] splitInput, String[] splitMessage , int origin) {
        String message;
        message = "";
        for (int i = origin ; i < splitInput.length ; i++) {
            if (i == origin)
                message += splitMessage[i];
            else message += " " + splitInput[i];
        }
        return message;
    }
}
