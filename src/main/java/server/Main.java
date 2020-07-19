package server;

import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.Constants;
import common.model.account.*;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.commodity.DiscountCode;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import common.model.share.Request;
import server.dataManager.YaDataManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static client.view.commandline.View.loginRegisterMenu;


public class Main {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static HashMap<Socket, String> onlineAccountsUserNames = new HashMap<>();
    private static HashMap<String, Socket> onlineFileTransferClients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.FILE_SERVER_PORT); // for p2p file transfer
        new FileTransferMetadataServer(serverSocket).start();
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
                    deleteSocketAndAccount(socket);
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
            editDiscountCode(socket, input);
        } else if (input.startsWith("Edit")) {
            editPersonalInfo(input, socket);
        } else if (input.equals("New Commodity")) {
            addCommodity(socket);
        } else if (input.equals("new discount code")) {
            createDiscountCode(socket);
        } else if (input.equals("Discount codes")) {
            sendDiscountCodes(socket);
        } else if (input.startsWith("Delete discount code")) {
            deleteDiscountCode(socket, input);
        } else if (input.equals("categories name")) {
            sendCategoriesName(socket);
        } else if (input.equals("send seller commodities")) {
            sendSellerCommodities(socket);
        } else if (input.startsWith("name of category is ")) {
            sendCategoryWithName(socket, input.split(" ", 5)[4]);
        } else if (input.startsWith("add request")) {
            YaDataManager.addRequest(yaGson.fromJson(input.split(" ", 3)[2], new TypeToken<Request>() {
            }.getType()));
        } else if (input.equals("Requests")) {
            sendRequests(socket);
        } else if (input.startsWith("Accept request")) {
            acceptRequest(input);
        } else if (input.startsWith("Decline request")) {
            declineRequest(input);
        } else if (input.startsWith("Delete request")) {
            deleteRequest(input);
        } else if (input.equals("Send all accounts")) {
            sendAllAccounts(socket);
        } else if (input.startsWith("Delete account")) {
            deleteAccount(socket, input);
        } else if (input.equals("send categories")) {
            sendCategories(socket);
        } else if (input.startsWith("delete category")) {
            deleteCategory(socket, input.split(" ", 3)[2]);
        } else if (input.startsWith("is category name valid? ")) {
            checkCategoryName(socket, input.split(" ", 5)[4]);
        } else if (input.startsWith("new category")) {
            addCategory(socket, input.split(" ", 3)[2]);
        } else if (input.equals("send all commodities")) {
            sendAllCommodities(socket);
        } else if (input.startsWith("remove category")) {
            YaDataManager.removeCategory(YaDataManager.getCategoryWithName(input.split(" ", 3)[2]));
        } else if (input.startsWith("send commodity with id ")) {
            sendCommodityWithId(socket, Integer.parseInt(input.split(" ")[4]));
        }
    }

    private static void login(String input, Socket socket) throws IOException {
        System.out.println("adk");
        String[] splitInput = input.split(" ");
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        try {
            View.loginRegisterMenu.login(splitInput[1], splitInput[2]);
            onlineAccountsUserNames.put(socket, splitInput[1]);
            dataOutputStream.writeUTF("logged in");
            dataOutputStream.flush();
            System.out.println(dataInputStream.readUTF());
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
        onlineAccountsUserNames.put(socket, splitInput[3]);
    }

    private static void sendOnlineAccounts(Socket socket) throws IOException {
        ArrayList<SimpleAccount> accounts = new ArrayList<>();
        for (Socket socket1 : onlineAccountsUserNames.keySet()) {
            accounts.add(YaDataManager.getAccountWithUserName(onlineAccountsUserNames.get(socket1)));
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF(yaGson.toJson(accounts, new TypeToken<ArrayList<SimpleAccount>>() {
        }.getType()));
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
        for (Socket socket : onlineAccountsUserNames.keySet()) {
            if (onlineAccountsUserNames.get(socket).equals(splitInput[i]))
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
        } catch (Exception e) {
            e.printStackTrace();
            dataOutputStream.writeUTF("Invalid entry");
            dataOutputStream.flush();
        }
    }

    public static void deleteDiscountCode(Socket socket, String input) throws Exception {
        String[] splitInput = input.split(" ");
        DiscountCode discountCode = YaDataManager.getDiscountCodeWithCode(splitInput[3]);
        View.getDiscountCode.deleteDiscountCode(discountCode);
    }

    private static void sendCategoriesName(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF(yaGson.toJson(YaDataManager.getCategories().stream().map(Category::getName)
                .collect(Collectors.toCollection(ArrayList::new)), new TypeToken<ArrayList<String>>() {
        }.getType()));
    }

    private static void sendSellerCommodities(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        try {
            BusinessAccount seller = YaDataManager.getSellerWithUserName(onlineAccountsUserNames.get(socket));
            ArrayList<Commodity> commodities = new ArrayList<>();
            for (Integer commodityId : seller.getCommoditiesId()) {
                commodities.add(YaDataManager.getCommodityById(commodityId));
            }
            dos.writeUTF(yaGson.toJson(commodities, new TypeToken<ArrayList<Commodity>>() {
            }.getType()));
            dos.flush();
        } catch (Exception e) {
            dos.writeUTF("error:" + e.getMessage());
            dos.flush();
        }
    }

    private static void sendCategoryWithName(Socket socket, String name) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF(yaGson.toJson(YaDataManager.getCategoryWithName(name), new TypeToken<Category>() {
        }.getType()));
        dos.flush();
    }

    private static void sendRequests(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getRequests(), new TypeToken<ArrayList<Request>>() {
        }.getType()));
        dataOutputStream.flush();
    }

    private static void acceptRequest(String input) throws Exception {
        String[] splitInput = input.split(" ");
        View.manageRequestMenu.accept(Integer.parseInt(splitInput[2]));
    }

    private static void deleteRequest(String input) throws Exception {
        String[] splitInput = input.split(" ");
        View.manageRequestMenu.deleteRequest(Integer.parseInt(splitInput[2]));
    }

    private static void declineRequest(String input) throws Exception {
        String[] splitInput = input.split(" ");
        View.manageRequestMenu.decline(Integer.parseInt(splitInput[2]));
    }

    private static void sendCategories(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF(yaGson.toJson(YaDataManager.getCategories(), new TypeToken<ArrayList<Category>>() {
        }.getType()));
        dos.flush();
    }

    private static void sendAllAccounts(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getManagers(),
                new TypeToken<ArrayList<ManagerAccount>>() {
                }.getType()));
        dataOutputStream.flush();
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getPersons(),
                new TypeToken<ArrayList<PersonalAccount>>() {
                }.getType()));
        dataOutputStream.flush();
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getBusinesses(),
                new TypeToken<ArrayList<BusinessAccount>>() {
                }.getType()));
        dataOutputStream.flush();
    }

    private static void deleteSocketAndAccount(Socket socket) {
        onlineAccountsUserNames.remove(socket);
        sockets.remove(socket);
    }

    private static void deleteAccount(Socket socket, String input) throws Exception {
        String[] splitInput = input.split(" ");
        try {
            View.manageUsersMenu.deleteUser(splitInput[2], splitInput[3]);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Account successfully deleted.");
            dataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("error");
            dataOutputStream.flush();
        }
    }

    private static void deleteCategory(Socket socket, String name) throws IOException {
        try {
            View.manageCategoryMenu.removeCategory(name);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dos.writeUTF("deleted");
            dos.flush();
        }
    }

    private static void checkCategoryName(Socket socket, String name) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        if (YaDataManager.isCategoryExist(name)) {
            dos.writeUTF("yes");
        } else {
            dos.writeUTF("no");
        }
        dos.flush();
    }

    private static void addCategory(Socket socket, String json) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        Category category = yaGson.fromJson(json, new TypeToken<Category>() {
        }.getType());
        YaDataManager.addCategory(category);
        dos.writeUTF("send commodities");
        dos.flush();
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String input = dis.readUTF();
        while (input.startsWith("change category for commodity ")) {
            int commodityId = Integer.parseInt(input.split(" ")[4]);
            try {
                Commodity commodity = YaDataManager.getCommodityById(commodityId);
                commodity.setCategoryName(category.getName());
                YaDataManager.removeCommodity(commodity);
                YaDataManager.addCommodity(commodity);
                dos.writeUTF("next");
                dos.flush();
                input = dis.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendAllCommodities(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF(yaGson.toJson(YaDataManager.getCommodities(), new TypeToken<ArrayList<Commodity>>() {
        }.getType()));
        dos.flush();
    }

    private static void sendCommodityWithId(Socket socket, int id) throws Exception {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF(yaGson.toJson(YaDataManager.getCommodityById(id), new TypeToken<Commodity>() {
        }.getType()));
        dos.flush();
    }

    private static class FileTransferMetadataServer extends Thread {

        private ServerSocket serverSocket;

        public FileTransferMetadataServer(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                            Pattern pattern = Pattern.compile("^get file #(?<filePath>.+)# from " +
                                    "(?<sellerUsername>\\S+) now listening on port (?<port>\\d+)$");
                            String request = inputStream.readUTF();
                            Matcher matcher = pattern.matcher(request);
                            if (request.startsWith("add me")) {
                                String[] separatedRequest = request.split(" ");
                                if (separatedRequest.length == 3 &&
                                        YaDataManager.isUsernameExist(separatedRequest[2])) {
                                    onlineFileTransferClients.put(separatedRequest[2], socket);
                                }
                            } else if (matcher.matches()) {
                                String filePath = matcher.group("filePath");
                                String sellerUsername = matcher.group("sellerUsername");
                                int port = Integer.parseInt(matcher.group("port"));
                                if (onlineFileTransferClients.containsKey(sellerUsername)) {
                                    Socket sellerSocket = onlineFileTransferClients.get(sellerUsername);
                                    DataOutputStream resellerOutputStream =
                                            new DataOutputStream(sellerSocket.getOutputStream());
                                    DataInputStream resellerInputStream =
                                            new DataInputStream(sellerSocket.getInputStream());
                                    resellerOutputStream.writeUTF("send #" + filePath + "# to " +
                                            socket.getInetAddress().getHostAddress() + ":" + port);
                                    String senderResponse = resellerInputStream.readUTF();
                                    outputStream.writeUTF(senderResponse);
                                    String receiverResponse = inputStream.readUTF();
                                    resellerOutputStream.writeUTF(receiverResponse);
                                } else {
                                    outputStream.writeUTF("Error : seller " + sellerUsername + " is not online");
                                }
                            } else {
                                outputStream.writeUTF("Request is not valid");
                            }
                            //socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
