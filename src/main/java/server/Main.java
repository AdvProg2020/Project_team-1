package server;

import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.Constants;
import common.model.account.*;
import common.model.commodity.*;
import common.model.exception.InvalidAccessException;
import common.model.exception.InvalidAccountInfoException;
import common.model.exception.InvalidLoginInformationException;
import common.model.share.Request;
import server.controller.Statistics;
import server.dataManager.YaDataManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static client.view.commandline.View.loginRegisterMenu;


public class Main {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public static Socket socketB;
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static HashMap<Socket, String> onlineAccountsUserNames = new HashMap<>();
    private static HashMap<String, Socket> onlineFileTransferClients = new HashMap<>();
    private static String bankAccountID;

    static {
        try {
            socketB = new Socket("127.0.0.1", 9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.FILE_SERVER_PORT); // for p2p file transfer
        new FileTransferMetadataServer(serverSocket).start();
        ServerSocket server = new ServerSocket(Constants.SERVER_PORT); // for clients request
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        System.out.println(dataInputStream.readUTF());
        dataOutputStream.writeUTF("create_account bank bank bank bank bank");

        bankAccountID = dataInputStream.readUTF();
        System.out.println(bankAccountID + " = bank id");
        bankAccountID = "10001";
        System.out.println(bankAccountID);
        for (Auction auction : YaDataManager.getAuctions()) {
            if (auction.getDeadline().after(new Date())) {
                new Thread(() -> {
                    Timer timer = new Timer();
                    timer.schedule(new AuctionCloser(auction), auction.getDeadline());
                }).start();
            } else {
                //purchase
            }
        }
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
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
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
            register(socket);
        } else if (input.startsWith("login")) {
            System.out.println(socket);
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
        } else if (input.equals("send all offs")) {
            sendAllOffs(socket);
        } else if (input.startsWith("Deposit to wallet")) {
            depositToWallet(socket, input);
        } else if (input.startsWith("Withdraw from wallet")) {
            withdrawFromWallet(socket, input);
        } else if (input.startsWith("add to cart ")) {
            addToCart(onlineAccountsUserNames.get(socket), Integer.parseInt(input.split(" ")[3]));
        } else if (input.startsWith("remove from cart")) {
            removeFromCart(onlineAccountsUserNames.get(socket), Integer.parseInt(input.split(" ")[3]));
        } else if (input.startsWith("add to product views")) {
            addToVisits(Integer.parseInt(input.split(" ")[4]));
        } else if (input.equals("get total price")) {
            sendTotalPrice(socket);
        } else if (input.startsWith("add score ")) {
            rateProduct(Integer.parseInt(input.split(" ")[2]), Integer.parseInt(input.split(" ")[3]),
                    socket);
        } else if (input.startsWith("Set min currency")) {
            setMinimumCurrency(input);
        } else if (input.startsWith("Set wage")) {
            setWage(input);
        } else if (input.startsWith("send off with id ")) {
            sendOffWithId(socket, Integer.parseInt(input.split(" ")[4]));
        } else if (input.startsWith("remove commodity with id ")) {
            removeCommodity(Integer.parseInt(input.split(" ")[4]));
        } else if (input.equals("has an auction?")) {
            hasAnAuction(socket);
        } else if (input.startsWith("new auction")) {
            makeNewAuction(socket, input);
        } else if (input.startsWith("is commodity in auction? ")) {
            isCommodityInAuction(socket, Integer.parseInt(input.split(" ")[4]));
        } else if (input.startsWith("update auction ")) {
            sendAuction(socket, Integer.parseInt(input.split(" ")[2]));
        } else if (input.equals("send blocked money")) {
            sendBlockedMoney(socket);
        } else if (input.startsWith("new bid ")) {
            newBid(socket, Integer.parseInt(input.split(" ")[2]), Integer.parseInt(input.split(" ")[4]));
        }
    }

    private static void newBid(Socket socket, int money, int id) throws IOException {
        for (Auction auction : YaDataManager.getAuctions()) {
            if (auction.getCommodityId() == id) {
                auction.newBid(onlineAccountsUserNames.get(socket), money);
                return;
            }
        }
    }

    private static void sendBlockedMoney(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        int money = 0;
        for (Auction auction : YaDataManager.getAuctions()) {
            if (auction.getTopBidder().equals(onlineAccountsUserNames.get(socket))) {
                money += auction.getTopBid();
            }
        }
        dataOutputStream.write(money);
    }

    private static void sendAuction(Socket socket, int id) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        for (Auction auction : YaDataManager.getAuctions()) {
            if (id == auction.getCommodityId()) {
                dataOutputStream.writeUTF(yaGson.toJson(auction, new TypeToken<Auction>() {
                }.getType()));
                return;
            }
        }
    }

    private static void isCommodityInAuction(Socket socket, int id) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        for (Auction auction : YaDataManager.getAuctions()) {
            if (auction.getCommodityId() == id) {
                dataOutputStream.writeUTF("yes");

                return;
            }
        }
        dataOutputStream.writeUTF("no");

    }

    private static void login(String input, Socket socket) throws IOException {
        String[] splitInput = input.split(" ");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        try {
            View.loginRegisterMenu.login(splitInput[1], splitInput[2]);
            onlineAccountsUserNames.put(socket, splitInput[1]);
            System.out.println(socket);
            dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getAccountWithUserName(splitInput[1]), new TypeToken<SimpleAccount>() {
            }.getType()));

        } catch (InvalidLoginInformationException e) {
            dataOutputStream.writeUTF("error:" + e.getMessage());

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
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(accounts, new TypeToken<ArrayList<SimpleAccount>>() {
        }.getType()));

    }

    private static void newSupportAccount(Socket socket) throws IOException, ClassNotFoundException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Send object");

        ObjectInputStream objectInputStream = new ObjectInputStream((socket.getInputStream()));
        SupportAccount supportAccount = (SupportAccount) objectInputStream.readObject();
        try {
            View.manageUsersMenu.createNewSupport(supportAccount);
            dataOutputStream.writeUTF("Support account successfully added");

        } catch (Exception e) {
            dataOutputStream.writeUTF(e.getMessage());

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
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String[] information = dataInputStream.readUTF().split(" ");
            System.out.println(information[1] + " as");

            try {
                loginRegisterMenu.checkUserNameAvailability(information[1]);
            } catch (InvalidLoginInformationException e) {
                dataOutputStream.writeUTF(e.getMessage());

                return;
            }
            if (information[0].equals("manager")) {
                try {
                    loginRegisterMenu.isThereManagerAccount();
                } catch (InvalidAccessException e) {
                    dataOutputStream.writeUTF(e.getMessage());

                }
            }
            String bankToken;
            if (information[0].equals("personal")) {
                registerPersonal(dataOutputStream, dataInputStream, information);
            }
            if (information[0].equals("business")) {
                registerReseller(dataOutputStream, dataInputStream, information);
            }
            if (information[0].equals("manager")) {
                registerManager(dataOutputStream, information);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerManager(DataOutputStream dataOutputStream, String[] information) throws IOException {
        try {
            loginRegisterMenu.registerManagerAccount(information[1], information[2], information[3]
                    , information[4], information[5], information[6], information[7]);
            dataOutputStream.writeUTF("You have registered successfully.");

        } catch (InvalidAccountInfoException e) {
            dataOutputStream.writeUTF(e.getMessage());

        }
    }

    private static void registerPersonal(DataOutputStream dataOutputStream, DataInputStream dataInputStream, String[] information) throws IOException {
        try {
            loginRegisterMenu.registerPersonalAccount(information[1], information[2], information[3]
                    , information[4], information[5], information[6], information[7]);
            initializePersonalBankAccount(dataOutputStream, dataInputStream, information);
        } catch (InvalidAccountInfoException e) {
            dataOutputStream.writeUTF(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializePersonalBankAccount(DataOutputStream dataOutputStream, DataInputStream dataInputStream, String[] information) throws Exception {
        String accountID;
        String bankToken;
        dataOutputStream.writeUTF("You have registered successfully.");

        accountID = dataInputStream.readUTF();
        bankToken = dataInputStream.readUTF();
        System.out.println("Bank token " + bankToken + " " + accountID);
        System.out.println(information[1]);
        System.out.println(YaDataManager.getAccountWithUserName(information[1]));
        loginRegisterMenu.setAccountId(YaDataManager.getAccountWithUserName(information[1]), accountID);
        System.out.println(YaDataManager.getAccountWithUserName(information[1]).getAccountID());
        String receiptID = createReceipt(bankToken, "deposit", "1000", "-1", accountID, "");
        System.out.println(payReceipt(receiptID));
    }

    private static void registerReseller(DataOutputStream dataOutputStream, DataInputStream dataInputStream, String[] information) throws IOException {
        try {
            loginRegisterMenu.registerResellerAccount(information[1], information[2], information[3]
                    , information[4], information[5], information[6], information[7], information[8]);
            initializeResellerBankAccount(dataOutputStream, dataInputStream, information);
        } catch (InvalidAccountInfoException e) {
            dataOutputStream.writeUTF(e.getMessage());

        }
    }

    private static void initializeResellerBankAccount(DataOutputStream dataOutputStream, DataInputStream dataInputStream, String[] information) throws IOException {
        String accountID;
        String bankToken;
        dataOutputStream.writeUTF("You have registered successfully.");

        accountID = dataInputStream.readUTF();
        bankToken = dataInputStream.readUTF();
        for (Request request : YaDataManager.getRequests()) {
            if (request.getObj() instanceof BusinessAccount &&
                    ((BusinessAccount) request.getObj()).getUsername().equals(information[1])) {
                loginRegisterMenu.setAccountIDRequest(request, accountID);
            }
        }
        String receiptID = createReceipt(bankToken, "deposit", "1000", "-1", accountID, "");
        System.out.println(payReceipt(receiptID));
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

            System.out.println("  ss");
        } catch (Exception e) {

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(e.getMessage());

        }
    }

    private static void addCommodity(Socket socket) throws IOException{
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        dos.writeUTF("send");
        Request request = yaGson.fromJson(dis.readUTF(), new TypeToken<Request>() {
        }.getType());
        dos.writeUTF("send picture");
        long fileSize = Long.parseLong(dis.readUTF());
        FileOutputStream file = new FileOutputStream("data\\media\\products\\"  + Integer.toString(((Commodity) request.getObj()).getCommodityId()));
        byte[] buffer = new byte[Constants.FILE_BUFFER_SIZE];
        long counter = 0;
        while (counter < fileSize) {
            dis.read(buffer);
            System.out.println("kire khar");
            file.write(buffer);
            counter+= Constants.FILE_BUFFER_SIZE;
        }
        System.out.println("mamal koonie");
        file.close();
        System.out.println(request.toString());
        YaDataManager.addRequest(request);
    }

    private static void createDiscountCode(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
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

            return;
        }
        dataOutputStream.writeUTF("Discount code successfully created");

    }

    public static void sendDiscountCodes(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getDiscountCodes(), new TypeToken<ArrayList<DiscountCode>>() {
        }.getType()));

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

        } catch (Exception e) {
            e.printStackTrace();
            dataOutputStream.writeUTF("Invalid entry");

        }
    }

    public static void deleteDiscountCode(Socket socket, String input) throws Exception {
        String[] splitInput = input.split(" ");
        DiscountCode discountCode = YaDataManager.getDiscountCodeWithCode(splitInput[3]);
        View.getDiscountCode.deleteDiscountCode(discountCode);
    }

    private static void sendCategoriesName(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(yaGson.toJson(YaDataManager.getCategories().stream().map(Category::getName)
                .collect(Collectors.toCollection(ArrayList::new)), new TypeToken<ArrayList<String>>() {
        }.getType()));

    }

    private static void sendSellerCommodities(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        try {
            BusinessAccount seller = YaDataManager.getSellerWithUserName(onlineAccountsUserNames.get(socket));
            ArrayList<Commodity> commodities = new ArrayList<>();
            for (Integer commodityId : seller.getCommoditiesId()) {
                commodities.add(YaDataManager.getCommodityById(commodityId));
            }
            dos.writeUTF(yaGson.toJson(commodities, new TypeToken<ArrayList<Commodity>>() {
            }.getType()));

        } catch (Exception e) {
            dos.writeUTF("error:" + e.getMessage());

        }
    }

    private static void sendCategoryWithName(Socket socket, String name) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(yaGson.toJson(YaDataManager.getCategoryWithName(name), new TypeToken<Category>() {
        }.getType()));

    }

    private static void sendRequests(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getRequests(), new TypeToken<ArrayList<Request>>() {
        }.getType()));

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
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(yaGson.toJson(YaDataManager.getCategories(), new TypeToken<ArrayList<Category>>() {
        }.getType()));

    }

    private static void sendAllAccounts(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getManagers(),
                new TypeToken<ArrayList<ManagerAccount>>() {
                }.getType()));

        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getPersons(),
                new TypeToken<ArrayList<PersonalAccount>>() {
                }.getType()));

        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getBusinesses(),
                new TypeToken<ArrayList<BusinessAccount>>() {
                }.getType()));

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

        } catch (Exception e) {
            e.printStackTrace();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("error");

        }
    }

    private static void deleteCategory(Socket socket, String name) throws IOException {
        try {
            View.manageCategoryMenu.removeCategory(name);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("deleted");

        }
    }

    private static void checkCategoryName(Socket socket, String name) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        if (YaDataManager.isCategoryExist(name)) {
            dos.writeUTF("yes");
        } else {
            dos.writeUTF("no");
        }

    }

    private static void addCategory(Socket socket, String json) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        Category category = yaGson.fromJson(json, new TypeToken<Category>() {
        }.getType());
        YaDataManager.addCategory(category);
        dos.writeUTF("send commodities");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String input = dis.readUTF();
        while (input.startsWith("change category for commodity ")) {
            int commodityId = Integer.parseInt(input.split(" ")[4]);
            try {
                Commodity commodity = YaDataManager.getCommodityById(commodityId);
                commodity.setCategoryName(category.getName());
                YaDataManager.removeCommodity(commodity);
                YaDataManager.addCommodity(commodity);
                dos.writeUTF("next");

                input = dis.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendAllCommodities(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        ArrayList<Commodity> commodities = YaDataManager.getCommodities();
        dos.writeUTF(yaGson.toJson(commodities, new TypeToken<ArrayList<Commodity>>() {
        }.getType()));
        if (dis.readUTF().equals("send pictures")) {
            for (Commodity commodity : commodities) {
                FileInputStream file = new FileInputStream("data\\media\\product\\" + commodity.getCommodityId());
                dos.writeUTF(Integer.toString(commodity.getCommodityId()));
                byte[] buffer = new byte[Constants.FILE_BUFFER_SIZE];
                while (file.read(buffer) > 0) {
                    dos.write(buffer);
                }
                file.close();
            }
        }
    }

    private static void sendCommodityWithId(Socket socket, int id) throws Exception {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(yaGson.toJson(YaDataManager.getCommodityById(id), new TypeToken<Commodity>() {
        }.getType()));

    }

    private static void sendAllOffs(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(yaGson.toJson(YaDataManager.getOffs(), new TypeToken<ArrayList<Off>>() {
        }.getType()));

    }


    private static void addToCart(String username, int id) throws Exception {
        PersonalAccount personalAccount = YaDataManager.getPersonWithUserName(username);
        Objects.requireNonNull(personalAccount).addToCart(id);
        YaDataManager.removePerson(personalAccount);
        YaDataManager.addPerson(personalAccount);
    }

    private static void removeFromCart(String username, int id) throws Exception {
        PersonalAccount personalAccount = YaDataManager.getPersonWithUserName(username);
        Objects.requireNonNull(personalAccount).removeFromCart(id);
        YaDataManager.removePerson(personalAccount);
        YaDataManager.addPerson(personalAccount);
    }

    private static void addToVisits(int id) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(id);
        commodity.setNumberOfVisits(commodity.getNumberOfVisits() + 1);
        YaDataManager.removeCommodity(commodity);
        YaDataManager.addCommodity(commodity);
    }

    private static void sendTotalPrice(Socket socket) throws Exception {
        PersonalAccount personalAccount = YaDataManager.getPersonWithUserName(onlineAccountsUserNames.get(socket));
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("Total price: " + View.cartMenu.calculateTotalPrice(personalAccount) + " Rials");

    }

    private static void rateProduct(int id, int rate, Socket socket) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(id);
        commodity.updateAverageScore(new Score(onlineAccountsUserNames.get(socket), rate, id));
        YaDataManager.removeCommodity(commodity);
        YaDataManager.addCommodity(commodity);
    }

    private static String createReceipt(String bankToken, String receipt_type, String money, String sourceID, String destID, String description) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        dataOutputStream.writeUTF("create_receipt " + bankToken + " " + receipt_type + " " + money + " " +
                sourceID + " " + destID);

        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        return dataInputStream.readUTF();
    }

    private static String payReceipt(String receiptID) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socketB.getOutputStream());
        dataOutputStream.writeUTF("pay " + receiptID);

        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        return dataInputStream.readUTF();
    }

    private static void depositToWallet(Socket socket, String input) throws Exception {
        String[] splitInput = input.split(" ");
        String clientAccountID = YaDataManager.getAccountWithUserName(splitInput[4]).getAccountID();
        String receipt = createReceipt(splitInput[3], "move", splitInput[5], clientAccountID, bankAccountID, "");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        System.out.println("Recipet" + receipt);
        String respond = payReceipt(receipt);
        System.out.println("respond " + respond);
        dataOutputStream.writeUTF(respond);

        if (respond.equals("done successfully")) {
            if (YaDataManager.getAccountWithUserName(splitInput[4]) instanceof BusinessAccount) {
                View.resellerMenu.walletTransaction(Double.parseDouble(splitInput[5]), YaDataManager.getSellerWithUserName(splitInput[4]));
            } else
                View.customerMenu.walletTransaction(Double.parseDouble(splitInput[5]), YaDataManager.getPersonWithUserName(splitInput[4]));
        }
    }

    private static void withdrawFromWallet(Socket socket, String input) throws Exception {
        String[] splitInput = input.split(" ");
        String clientAccountID = YaDataManager.getAccountWithUserName(splitInput[4]).getAccountID();
        Double credit = YaDataManager.getSellerWithUserName(splitInput[4]).getCredit();
        Double minCurrency = Statistics.updatedStats.getMinimumCurrency();
        Double money = Double.parseDouble(splitInput[5]);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        if (credit - money < minCurrency) {
            dataOutputStream.writeUTF("You must have " + minCurrency + " in your wallet.");

            return;
        }
        DataOutputStream dataOutputStream1 = new DataOutputStream(socketB.getOutputStream());
        dataOutputStream1.writeUTF("get_token bank bank");
        dataOutputStream1.flush();
        DataInputStream dataInputStream = new DataInputStream(socketB.getInputStream());
        String receipt = createReceipt(dataInputStream.readUTF(), "move", splitInput[5], bankAccountID, clientAccountID, "");
        System.out.println(receipt);
        String respond = payReceipt(receipt);
        dataOutputStream.writeUTF(respond);

        if (respond.equals("done successfully")) {
            View.resellerMenu.walletTransaction(-(Double.parseDouble(splitInput[5])), YaDataManager.getSellerWithUserName(splitInput[4]));
        }
    }

    private static void sendOffWithId(Socket socket, int id) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(yaGson.toJson(YaDataManager.getOffWithId(id), new TypeToken<Off>() {
        }.getType()));

    }

    private static void setMinimumCurrency(String input) throws IOException {
        String[] splitInput = input.split(" ");
        Statistics.updatedStats.setMinimumCurrency(Double.parseDouble(splitInput[3]));
    }

    private static void setWage(String input) throws IOException {
        String[] splitInput = input.split(" ");
        Statistics.updatedStats.setWage(Double.parseDouble(splitInput[2]));
    }

    private static void removeCommodity(int id) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(id);
        BusinessAccount seller = YaDataManager.getSellerWithUserName(commodity.getSellerUsername());
        YaDataManager.removeBusiness(seller);
        YaDataManager.addBusiness(seller);
        YaDataManager.removeCommodity(commodity);
    }

    private static void hasAnAuction(Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        for (Auction auction : YaDataManager.getAuctions()) {
            if (auction.getOwnerUsername().equals(onlineAccountsUserNames.get(socket))) {
                dos.writeUTF("yes");

                return;
            }
        }
        dos.writeUTF("no");

    }

    private static void makeNewAuction(Socket socket, String input) throws Exception {
        Commodity commodity = YaDataManager.getCommodityById(Integer.parseInt(input.split(" ")[2]));
        Date deadline = yaGson.fromJson(input.split(" ", 5)[4], new TypeToken<Date>() {
        }.getType());
        Auction auction = new Auction(onlineAccountsUserNames.get(socket), commodity.getCommodityId(), deadline,
                commodity.getPrice());
        new Thread(() -> {
            Timer timer = new Timer();
            timer.schedule(new AuctionCloser(auction), deadline);
        }).start();
        YaDataManager.addAuction(auction);
    }

    private static class AuctionCloser extends TimerTask {
        private Auction auction;

        public AuctionCloser(Auction auction) {
            this.auction = auction;
        }

        @Override
        public void run() {
            if (auction.getTopBidder() != null) {
                //purchase and deduct money from buyer wallet and add to seller wallet
            }
            try {
                YaDataManager.removeAuction(this.auction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
