package server;

import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import common.model.account.SimpleAccount;
import common.model.account.SupportAccount;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import javafx.css.Match;
import server.data.YaDataManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.view.commandline.View.loginRegisterMenu;


public class Main {
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static HashMap<Socket, String> onlineAccountsUsernames = new HashMap<>();
    private static HashMap<String, Socket> onlineFileTransferClients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(88881); // for p2p file transfer
        new Thread(() -> {
            while (true) {
                Socket socket = null;
                DataInputStream dis = null;
                DataOutputStream dos = null;
                try {
                    socket = serverSocket.accept();
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataInputStream inputStream = dis;
                DataOutputStream outputStream = dos;
                Socket finalSocket = socket;
                Socket finalSocket1 = socket;
                new Thread(() -> {
                    String request = "";
                    try {
                        assert inputStream != null;
                        request = inputStream.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (request.startsWith("add me")) {
                        String[] separatedRequest = request.split(" ");
                        try {
                            if (separatedRequest.length == 3 &&
                                    YaDataManager.isUsernameExist(separatedRequest[2])) {
                                onlineFileTransferClients.put(separatedRequest[2], finalSocket);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (request.startsWith("get file")) {
                        Pattern pattern = Pattern.compile("$get file #(?<filePath>.+)# from (?<sellerUsername>\\S+)^");
                        Matcher matcher = pattern.matcher(request);
                        if (matcher.matches()) {
                            String filePath = matcher.group("filePath");
                            String sellerUsername = matcher.group("sellerUsername");
                            if (onlineFileTransferClients.containsKey(sellerUsername)) {
                                try {
                                    DataOutputStream dataOutputStream = new DataOutputStream(onlineFileTransferClients.
                                            get(sellerUsername).getOutputStream());
                                    dataOutputStream.writeUTF("send #" + filePath + "# to " +
                                            finalSocket1.getInetAddress().getHostAddress());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    outputStream.writeUTF("Error : seller " + sellerUsername + "is not online");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                outputStream.writeUTF("Error : invalid request");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            assert outputStream != null;
                            outputStream.writeUTF("request not valid");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }).start();
        ServerSocket server = new ServerSocket(8888); // for clients request
        while (true) {
            Socket socket = server.accept();
            sockets.add(socket);
            System.out.println(socket);
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
                    System.out.println("in ghat shod");
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
        } else if (input.startsWith("Register")) {
            System.out.println("dasd");
            register(socket);
        } else if (input.startsWith("login")) {
            login(input, socket);
        } else if (input.startsWith("Edit")){
            editPersonalInfo(input , socket);
        }
    }

    public static void login(String input, Socket socket) throws IOException {
        String[] splitInput = input.split(" ");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        try {
            View.loginRegisterMenu.login(splitInput[1], splitInput[2]);
            onlineAccountsUsernames.put(socket, splitInput[1]);
            objectOutputStream.writeUTF("logged in");
            objectOutputStream.flush();
            dataInputStream.readUTF();
            objectOutputStream.writeObject(YaDataManager.getAccountWithUserName(splitInput[1]));
            objectOutputStream.flush();
        } catch (InvalidLoginInformationException e) {
            objectOutputStream.writeUTF("error:" + e.getMessage());
            objectOutputStream.flush();
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
            System.out.println(message);
            dataOutputStreamSupportAccount.writeUTF(message);
            dataOutputStreamSupportAccount.flush();
            if (message.startsWith("end chat")) {
                break;
            }
        }
    }

    private static void updateOnlineAccounts(String input, Socket socket) {
        String[] splitInput = input.split(" ");
        onlineAccountsUsernames.put(socket, splitInput[3]);
    }

    private static void sendOnlineAccounts(Socket socket) throws IOException {
        String usernamesString = "";
        for (Socket socket1 : onlineAccountsUsernames.keySet()) {
            usernamesString += onlineAccountsUsernames.get(socket1) + "\n";
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        System.out.println(onlineAccountsUsernames + "l");
        dataOutputStream.writeUTF(usernamesString);
        dataOutputStream.flush();
    }

    private static void newSupportAccount(Socket socket) throws IOException, ClassNotFoundException {
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
            System.out.println(message);
            String[] splitMessage = message.split(" ");
            if (message.startsWith("new chat")) {
                simpleAccountSocket = getSocket(splitInput, simpleAccountSocket, 3);
                message = getString(splitInput, splitMessage, 4);
            } else if (!message.startsWith("Start chat with")) {
                simpleAccountSocket = getSocket(splitMessage, simpleAccountSocket, 0);
                message = getString(splitInput, splitMessage, 1);
            }
            System.out.println(message);
            DataOutputStream dataOutputStreamSupportAccount = new DataOutputStream(new BufferedOutputStream(simpleAccountSocket.getOutputStream()));
            if (!message.startsWith("Start chat with")) {
                dataOutputStreamSupportAccount.writeUTF(message);
                dataOutputStreamSupportAccount.flush();
            }
        }
    }
    private static Socket getSocket(String[] splitInput, Socket simpleAccountSocket, int i) {
        for (Socket socket : onlineAccountsUsernames.keySet()) {
            if (onlineAccountsUsernames.get(socket).equals(splitInput[i]))
                simpleAccountSocket = socket;
        }
        return simpleAccountSocket;
    }

    private static String getString(String[] splitInput, String[] splitMessage, int origin) {
        String message;
        message = "";
        for (int i = origin; i < splitMessage.length; i++) {
            if (i == origin)
                message += splitMessage[i];
            else message += " " + splitMessage[i];
        }
        return message;
    }

    private static void register(Socket socket) throws IOException, ClassNotFoundException {
        try {
            System.out.println("salamss");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String[] information = dataInputStream.readUTF().split(" ");
            try {
                loginRegisterMenu.checkUserNameAvailability(information[1]);
            } catch (InvalidLoginInformationException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
            if (information[0].equals("manager")) {
                try {
                    loginRegisterMenu.isThereManagerAccount();
                } catch (InvalidAccessException e) {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                }
            }
            if (information[0].equals("personal")) {
                try {
                    loginRegisterMenu.registerPersonalAccount(information[1] , information[2] , information[3]
                            ,information[4] ,information[5] ,information[6] , information[7]);
                    dataOutputStream.writeUTF("You have registered successfully.");
                    dataOutputStream.flush();
                } catch (InvalidAccountInfoException e) {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                }
            }
            if (information[0].equals("business")) {
                try {
                    loginRegisterMenu.registerResellerAccount(information[1] , information[2] , information[3]
                            ,information[4] ,information[5] ,information[6] , information[7]); dataOutputStream.writeUTF("You have registered successfully.");
                    dataOutputStream.flush();
                } catch (InvalidAccountInfoException e) {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                }
            }
            if (information[0].equals("manager")) {
                try {
                    loginRegisterMenu.registerManagerAccount(information[1] , information[2] , information[3]
                            ,information[4] ,information[5] ,information[6] , information[7]); dataOutputStream.writeUTF("You have registered successfully.");
                    dataOutputStream.flush();
                } catch (InvalidAccountInfoException e) {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void editPersonalInfo(String input , Socket socket) throws IOException {
        String[] splitInput = input.split(" ");
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String username = splitInput[2];
            SimpleAccount simpleAccount = YaDataManager.getAccountWithUserName(username);
            if (splitInput[1].equals("password")) {
                View.viewPersonalInfoMenu.editPassword(splitInput[3] , simpleAccount );
            } else if (splitInput[1].equals("first")) {
                View.viewPersonalInfoMenu.editFirstName(splitInput[3] , simpleAccount );
            } else if (splitInput[1].equals("last")) {
                View.viewPersonalInfoMenu.editLastName(splitInput[3] , simpleAccount );
            } else if (splitInput[1].equals("email")) {
                View.viewPersonalInfoMenu.editEmail(splitInput[3] , simpleAccount );
            } else if (splitInput[1].equals("phone")) {
                View.viewPersonalInfoMenu.editPhoneNumber(splitInput[3] , simpleAccount );
            }
            dataOutputStream.writeUTF("successfully changed");
            dataOutputStream.flush();
        }catch (Exception e){
            System.out.println(e.getMessage());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(e.getMessage());
            dataOutputStream.flush();
        }
    }

}
