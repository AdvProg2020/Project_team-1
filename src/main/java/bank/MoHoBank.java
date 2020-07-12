package bank;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        while (true) {
            try {
                new HandleBankClient(bankServerSocket.accept(), debug).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: Unable to create data stream from socket");
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
                    if (debug) {
                        System.out.println("request from " + clientId + " : " + request);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error: Unable to get request from client. Closing socket ...");
                    break;
                }
                try {
                    if (request.startsWith("create_account")) {
                        createAccount(request.split(" "));
                    } else if (request.startsWith("get_token")) {
                        getToken(request.split(" "));
                    } else if (request.startsWith("create_receipt")) {
                        createReceipt(request.split(" "));
                    } else if (request.startsWith("get_transactions")) {
                        getTransactions(request.split(" "));
                    } else if (request.startsWith("pay")) {
                        pay(request.split(" "));
                    } else if (request.startsWith("get_balance")) {
                        getBalance(request.split(" "));
                    } else if (request.equals("exit")) {
                        run = false;
                    } else {
                        sendInvalidInput();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    sendDatabaseError();
                }
            }
            closeConnectionToClient();
        }

        private void getBalance(String[] separatedInput) throws SQLException {
            AuthenticationToken authToken = bankDataBase.getAuthTokenByUuid(separatedInput[1]);
            if (authToken == null) {
                sendTokenIsInvalid();
                return;
            }
            if (authToken.getExpired() != 0 || isTokenExpired(authToken)) {
                sendTokenExpired();
                return;
            }
            sendResponse(Integer.toString(bankDataBase.getAccountById(authToken.getAccountId()).getBalance()));
        }

        private void sendTokenExpired() {
            sendResponse("token expired");
        }

        private boolean isTokenExpired(AuthenticationToken authToken) throws SQLException {
            if (System.currentTimeMillis() - authToken.getCreateTime() < 3.6e6) {
                return false;
            }
            bankDataBase.setAuthTokenExpire(authToken.getUuid());
            return true;
        }

        private void sendTokenIsInvalid() {
            sendResponse("token is invalid");
        }

        private void pay(String[] separatedInput) {

        }

        private void getTransactions(String[] separatedInput) throws SQLException {
            AuthenticationToken authToken = bankDataBase.getAuthTokenByUuid(separatedInput[1]);
            if (authToken == null) {
                sendTokenIsInvalid();
                return;
            }
            if (authToken.getExpired() != 0 || isTokenExpired(authToken)) {
                sendTokenExpired();
                return;
            }
            StringBuilder result = new StringBuilder("");
            boolean secondParameterIsId = false;
            switch (separatedInput[2]) {
                case "+":
                    result = bankDataBase.getTransactionsToIdJson(authToken.getAccountId());
                    break;
                case "-":
                    result = bankDataBase.getTransactionsFromIdJson(authToken.getAccountId());
                    break;
                case "*":
                    result = bankDataBase.getIdAllTransactionsJson(authToken.getAccountId());
                    break;
                default:
                    secondParameterIsId = true;
            }
            if (secondParameterIsId) {
                int id;
                try {
                    id = Integer.parseInt(separatedInput[2]);
                } catch (NumberFormatException e) {
                    sendResponse("invalid receipt id");
                    return;
                }
                Receipt receipt = bankDataBase.getReceiptById(id);
                if (receipt == null || (receipt.getSourceAccountID() != authToken.getAccountId()
                        && receipt.getDestAccountID() != authToken.getAccountId())) {
                    sendResponse("invalid receipt id");
                    return;
                }
                result.append(receipt.getJson());
            }
            sendResponse(result.toString());
        }

        private void createReceipt(String[] separatedInput) throws SQLException {
            if (separatedInput.length != 5 && separatedInput.length != 6) {
                sendResponse("invalid parameters passed");
                return;
            }
            if (separatedInput[4].equals(separatedInput[5])) {
                sendResponse("equal source and dest account");
                return;
            }
            AuthenticationToken authToken = bankDataBase.getAuthTokenByUuid(separatedInput[1]);
            if (authToken == null) {
                // bardash too
                sendTokenIsInvalid();
                return;
            }
            if (authToken.getExpired() != 0 || isTokenExpired(authToken)) {
                sendTokenExpired();
                return;
            }


            //if (separatedInput.)




            if (!separatedInput[2].equals("deposit") && !separatedInput[2].equals("withdraw")
                    && !separatedInput[2].equals("move")) {
                sendResponse("invalid receipt type");
                return;
            }
            int money = -1;
            try {
                money = Integer.parseInt(separatedInput[3]);
            } catch (ArithmeticException e) {
                sendInvalidMoney();
                return;
            }
            if (money <= 0) {
                sendInvalidMoney();
                return;
            }

            //if ()
//            AuthenticationToken authToken = bankDataBase.getAuthTokenByUuid(separatedInput[1]);
//            if (authToken == null || authToken.getExpired() == 1) {
//
//            }
        }

        private void sendInvalidMoney() {
            sendResponse("invalid money");
        }

        private void getToken(String[] separatedInput) throws SQLException {
            BankAccount bankAccount = bankDataBase.getAccount(separatedInput[1]);
            if (bankAccount == null || !bankAccount.getPassword().equals(separatedInput[2])) {
                sendResponse("invalid username or password");
                return;
            }
            sendResponse(generateNewToken(bankAccount.getId()));
        }

        private String generateNewToken(int accountId) throws SQLException {
            AuthenticationToken authToken = new AuthenticationToken(accountId);
            bankDataBase.addAuthenticationToken(authToken);
            return authToken.getUuid();
        }

        private void closeConnectionToClient() {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: Unable to close client socket");
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
                if (debug) {
                    System.out.println("response to " + clientId + " : " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: Unable to send response to client. Closing socket ...");
                closeConnectionToClient();
            }
        }
    }
}

