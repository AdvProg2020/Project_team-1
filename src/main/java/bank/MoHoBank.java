package bank;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class MoHoBank {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Invalid arguments passed");
        }
        int port = Integer.parseInt(args[0]);
        boolean debug = !args[1].equals("0");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        ServerSocket bankServerSocket = new ServerSocket(port);
        System.out.printf("Starting bank server in %s mode on port %d\n", debug ? "debug": "normal", port);
        System.out.println("Your IP addresses : ");
        System.out.println("\t127.0.0.1 (On this machine)");
        System.out.printf("\t%s (In local network)\n", InetAddress.getLocalHost().getHostAddress());
        System.out.println("Use Ctrl+C to stop bank server");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        while (debug) {
            try {
                new HandleBankClient(bankServerSocket.accept(), debug).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: Unable to create data stream from socket");
            }
        }
    }

    private static class HandleBankClient extends Thread {
        private static int lastClientId = 0;
        private static int onlineClientsNumber = 0;
        private Socket socket;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;
        private boolean debug;
        private int clientId;
        private static BankDataBase bankDataBase = new BankDataBase();

        public HandleBankClient(Socket socket, boolean debug) throws IOException {
            this.socket = socket;
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            this.debug = debug;
            clientId = lastClientId++;
            if (debug) {
                System.out.printf("hello %d\n", clientId);
            }
            ++onlineClientsNumber;
        }

        @Override
        public void run() {
            boolean run = true;
            while (run) {
                String request = "";
                try {
                    request = inputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error: Unable to get request from client");
                }
                try {
                    if (request.startsWith("create_account")) {
                        createAccount(request.split(" "));
                    } else if (request.startsWith("get_token")) {

                    } else if (request.startsWith("create_receipt")) {

                    } else if (request.startsWith("get_transactions")) {

                    } else if (request.startsWith("pay")) {

                    } else if (request.startsWith("get_balance")) {

                    } else if (request.equals("exit")) {
                        run = false;
                    } else {
                        sendInvalidInput();
                    }
                } catch (SQLException e) {
                    sendDatabaseError();
                }
            }
            closeConnectionToClient();
        }

        private void closeConnectionToClient() {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: Unable to close client socket");
            }
        }

        private void createAccount(String[] separatedInput) throws SQLException {
            if (!separatedInput[4].equals(separatedInput[5])) {
                sendResponse("password do not match");
                return;
            }
            if (bankDataBase.getAccount(separatedInput[3]) != null) {
                sendResponse("username is not available");
                return;
            }
            int id = bankDataBase.addAccount(
                    new BankAccount(separatedInput[3], separatedInput[4], separatedInput[1], separatedInput[2]));
            sendResponse(Integer.toString(id));
        }

        private void sendDatabaseError() {
            sendResponse("database error");
        }

        private void sendInvalidInput() {
            sendResponse("invalid input");
        }

        private void sendResponse(String response) {
            try {
                outputStream.writeUTF(response);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: Unable to send response to client");
            }
        }
    }
}

