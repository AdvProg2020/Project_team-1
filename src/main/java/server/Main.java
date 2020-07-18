package server;

import client.view.commandline.View;
import com.google.gson.internal.$Gson$Preconditions;
import common.Constants;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import common.model.account.SupportAccount;
import common.model.commodity.DiscountCode;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import jdk.net.Sockets;
import server.dataManager.YaDataManager;
import common.model.share.Request;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.view.commandline.View.loginRegisterMenu;


public class Main {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static HashMap<Socket, String> onlineAccountsUsernames = new HashMap<>();
    private static HashMap<String, Socket> onlineFileTransferClients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.FILE_SERVER_PORT); // for p2p file transfer
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
                        System.out.println("File server log : " + request);
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
                        Pattern pattern = Pattern.compile("^get file #(?<filePath>.+)# from (?<sellerUsername>\\S+)" +
                                " now listening on port (?<port>\\d+)$");
                        Matcher matcher = pattern.matcher(request);
                        if (matcher.matches()) {
                            String filePath = matcher.group("filePath");
                            String sellerUsername = matcher.group("sellerUsername");
                            int port = Integer.parseInt(matcher.group("port"));
                            if (onlineFileTransferClients.containsKey(sellerUsername)) {
                                try {
                                    DataOutputStream dataOutputStreamSender = new DataOutputStream(onlineFileTransferClients.
                                            get(sellerUsername).getOutputStream());
                                    DataInputStream dataInputStreamSender = new DataInputStream(onlineFileTransferClients.
                                            get(sellerUsername).getInputStream());
                                    dataOutputStreamSender.writeUTF("send #" + filePath + "# to " +
                                            finalSocket1.getInetAddress().getHostAddress() + ":" + port);
                                    String senderResponse = dataInputStreamSender.readUTF();
                                    outputStream.writeUTF(senderResponse);
                                    String receiverResponse = inputStream.readUTF();
                                    dataOutputStreamSender.writeUTF(receiverResponse);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    outputStream.writeUTF("Error : seller " + sellerUsername + " is not online");
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
        ServerSocket server = new ServerSocket(Constants.SERVER_PORT); // for clients request
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void handleInput(String input, Socket socket) throws Exception {
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
        } else if (input.startsWith("Edit discount code")) {
            editDiscountCode(socket , input);
        } else if (input.startsWith("Edit")) {
            editPersonalInfo(input, socket);
        } else if (input.equals("New Commodity")) {
            System.out.println(0);
            addCommodity(socket);
        } else if (input.equals("new discount code")) {
            createDiscountCode(socket);
        } else if (input.equals("Discount codes")) {
            sendDiscountCodes(socket);
        }else if (input.startsWith("Delete discount code")){
            deleteDiscountCode(socket,input);
        }
    }

    private static void login(String input, Socket socket) throws IOException {
        String[] splitInput = input.split(" ");
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        try {
            View.loginRegisterMenu.login(splitInput[1], splitInput[2]);
            onlineAccountsUsernames.put(socket, splitInput[1]);
            dataOutputStream.writeUTF("logged in");
            dataOutputStream.flush();
            dataInputStream.readUTF();
            dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getAccountWithUserName(splitInput[1]), new TypeToken<SimpleAccount>() {
            }.getType()));
            dataOutputStream.flush();
        } catch (InvalidLoginInformationException e) {
            dataOutputStream.writeUTF("error:" + e.getMessage());
            dataOutputStream.flush();
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
                    loginRegisterMenu.registerPersonalAccount(information[1], information[2], information[3]
                            , information[4], information[5], information[6], information[7]);
                    dataOutputStream.writeUTF("You have registered successfully.");
                    dataOutputStream.flush();
                } catch (InvalidAccountInfoException e) {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                }
            }
            if (information[0].equals("business")) {
                try {
                    loginRegisterMenu.registerResellerAccount(information[1], information[2], information[3]
                            , information[4], information[5], information[6], information[7]);
                    dataOutputStream.writeUTF("You have registered successfully.");
                    dataOutputStream.flush();
                } catch (InvalidAccountInfoException e) {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                }
            }
            if (information[0].equals("manager")) {
                try {
                    loginRegisterMenu.registerManagerAccount(information[1], information[2], information[3]
                            , information[4], information[5], information[6], information[7]);
                    dataOutputStream.writeUTF("You have registered successfully.");
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

    private static void editPersonalInfo(String input, Socket socket) throws IOException {
        String[] splitInput = input.split(" ");
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String username = splitInput[2];
            SimpleAccount simpleAccount = YaDataManager.getAccountWithUserName(username);
            if (splitInput[1].equals("password")) {
                View.viewPersonalInfoMenu.editPassword(splitInput[3], simpleAccount);
            } else if (splitInput[1].equals("first")) {
                View.viewPersonalInfoMenu.editFirstName(splitInput[3], simpleAccount);
            } else if (splitInput[1].equals("last")) {
                View.viewPersonalInfoMenu.editLastName(splitInput[3], simpleAccount);
            } else if (splitInput[1].equals("email")) {
                View.viewPersonalInfoMenu.editEmail(splitInput[3], simpleAccount);
            } else if (splitInput[1].equals("phone")) {
                View.viewPersonalInfoMenu.editPhoneNumber(splitInput[3], simpleAccount);
            }
            dataOutputStream.writeUTF("successfully changed");
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(e.getMessage());
            dataOutputStream.flush();
        }
    }

    private static void addCommodity(Socket socket) throws IOException, ClassNotFoundException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos.writeUTF("send");
        dos.flush();
        Request request = yaGson.fromJson(dis.readUTF(), new TypeToken<Request>() {
        }.getType());
        System.out.println(request.toString());
        YaDataManager.addRequest(request);
        //get image
    }

    private static void createDiscountCode(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String discountCodeInfo = dataInputStream.readUTF();
        ArrayList<PersonalAccount> accounts = yaGson.fromJson(dataInputStream.readUTF(), new TypeToken<ArrayList<PersonalAccount>>() {
        }.getType());
        String[] splitDiscountCodeInfo = discountCodeInfo.split(" ");
        try {
            View.createDiscountCode.createDiscountCodeNC(splitDiscountCodeInfo[0], splitDiscountCodeInfo[1], splitDiscountCodeInfo[2]
                    , splitDiscountCodeInfo[3], splitDiscountCodeInfo[4], splitDiscountCodeInfo[5], accounts);
        } catch (Exception e) {
            e.printStackTrace();
            dataOutputStream.writeUTF(e.getMessage());
            dataOutputStream.flush();
            return;
        }
        dataOutputStream.writeUTF("Discount code successfully created");
        dataOutputStream.flush();
    }

    public static void sendDiscountCodes(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getDiscountCodes(), new TypeToken<ArrayList<DiscountCode>>() {
        }.getType()));
        dataOutputStream.flush();
    }

    public static void editDiscountCode(Socket socket, String input) throws Exception {
        String[] splitInput = input.split(" ");
        DiscountCode discountCode = YaDataManager.getDiscountCodeWithCode(splitInput[3]);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String discountCodeInfo = dataInputStream.readUTF();
        System.out.println(discountCodeInfo);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        ArrayList<PersonalAccount> accounts = yaGson.fromJson(dataInputStream.readUTF(), new TypeToken<ArrayList<PersonalAccount>>() {
        }.getType());
        String[] splitDiscountCodeInfo = discountCodeInfo.split(" ");
        try {
            System.out.println(splitDiscountCodeInfo[0]);
            View.getDiscountCode.changeCode(splitDiscountCodeInfo[0], discountCode);
            View.getDiscountCode.changeDiscountPercentage(Integer.parseInt(splitDiscountCodeInfo[3]), discountCode);
            View.getDiscountCode.changeMaximumNumberOfUses(Integer.parseInt(splitDiscountCodeInfo[5]), discountCode);
            View.getDiscountCode.changeMaximumDiscountPrice(Integer.parseInt(splitDiscountCodeInfo[4]), discountCode);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date startDateAsli = format.parse(splitDiscountCodeInfo[1]);
            Date finishDateASli = format.parse(splitDiscountCodeInfo[2]);
            View.getDiscountCode.changeFinishDate(finishDateASli, discountCode);
            View.getDiscountCode.changeStartDate(startDateAsli, discountCode);
            for (String username : discountCode.getAccountsUsername()) {
                View.getDiscountCode.deleteAccount(username, discountCode);
            }
            for (PersonalAccount account : accounts) {
                View.getDiscountCode.addAccount(account.getUsername(), discountCode);
            }
            dataOutputStream.writeUTF("Discount code successfully edited.");
            dataOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            dataOutputStream.writeUTF("Invalid entry");
            dataOutputStream.flush();
        }
    }

    public static void deleteDiscountCode(Socket socket , String input) throws Exception {
        String[] splitInput = input.split(" ");
        DiscountCode discountCode = YaDataManager.getDiscountCodeWithCode(splitInput[3]);
        View.getDiscountCode.deleteDiscountCode(discountCode);
    }
}
