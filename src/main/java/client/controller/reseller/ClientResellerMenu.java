package client.controller.reseller;

import client.Session;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.Constants;
import common.model.account.BusinessAccount;
import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.commodity.Off;
import common.model.field.Field;
import common.model.log.SellLog;
import common.model.share.Request;
import client.controller.comparator.Sort;
import client.controller.share.Menu;
import client.controller.share.MenuHandler;

import java.io.*;
import java.util.ArrayList;

import static client.Main.*;

public class ClientResellerMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public ClientResellerMenu() {
        fxmlFileAddress = "../../../fxml/Reseller.fxml";
        stageTitle = "Reseller panel";
    }

    public static BusinessAccount getBusinessAccount() {
        return (BusinessAccount) Session.getOnlineAccount();
    }

    public static ArrayList<Commodity> manageCommodities() throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("send seller commodities");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        ArrayList<Commodity> commodityArrayList = yaGson.fromJson(dis.readUTF(), new TypeToken<ArrayList<Commodity>>() {
        }.getType());
        dos.writeUTF("send pictures");
        System.out.println(commodityArrayList.size());
        int pictureName = 0;
        byte[] buffer = new byte[Constants.FILE_BUFFER_SIZE];
        new File("tmp").mkdir();
        int commodityReceivedAmount = Integer.parseInt(inputStream.readUTF());
        System.out.println(commodityReceivedAmount);
        System.out.println("salam");
        while (commodityReceivedAmount > 0) {
            pictureName = Integer.parseInt(inputStream.readUTF());
            System.out.println(pictureName);
            FileOutputStream file = new FileOutputStream("tmp\\" + pictureName + ".png");
            long counter = 0;
            long fileSize = Long.parseLong(inputStream.readUTF());
            System.out.println(fileSize);
            while (counter < fileSize) {
                inputStream.read(buffer);
                file.write(buffer);
                counter += Constants.FILE_BUFFER_SIZE;
            }
            commodityReceivedAmount--;
            file.close();
        }
        if (MenuHandler.getInstance().getCurrentMenu() != View.manageResellerProductsMenu) {
            View.manageResellerProductsMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.manageResellerProductsMenu);
        }
        return commodityArrayList;
    }

    public static ArrayList<SellLog> sortSalesHistory(String field) throws Exception {
        ArrayList<SellLog> sellLogs = getBusinessAccount().getSellLogs();
        Sort.sortSellLogArrayList(sellLogs, field);
        return sellLogs;
    }

    public static ArrayList<Off> manageOffs() throws IOException {
        ArrayList<Off> offArrayList = new ArrayList<>();
        for (Integer offId : getBusinessAccount().getOffsId()) {
            outputStream.writeUTF("send off with id " + offId);
            outputStream.flush();
            offArrayList.add(yaGson.fromJson(inputStream.readUTF(), new TypeToken<Off>() {
            }.getType()));
        }
        if (MenuHandler.getInstance().getCurrentMenu() != View.manageResellerOffMenu) {
            View.manageResellerOffMenu.setPreviousMenu(MenuHandler.getInstance().getCurrentMenu());
            MenuHandler.getInstance().setCurrentMenu(View.manageResellerOffMenu);
        }
        return offArrayList;
    }

    public static Category getCategoryByName(String categoryString) throws Exception {
        outputStream.writeUTF("name of category is " + categoryString);
        outputStream.flush();
        return yaGson.fromJson(inputStream.readUTF(), new TypeToken<Category>() {
        }.getType());
    }

    public static ArrayList<String> getCategoriesName() {
        ArrayList<String> categoriesName = null;
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dos.writeUTF("categories name");
            dos.flush();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            categoriesName = yaGson.fromJson(dis.readUTF(), new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoriesName;
    }

    public static void addProduct(String brand, String name, int price, Category category,
                           ArrayList<Field> categorySpecifications, String description, int amount,
                           String productFilePath, String imagePath) throws Exception {
        BusinessAccount businessAccount = getBusinessAccount();
        System.out.println("BAR");
        Commodity newCommodity = new Commodity(brand, name, price, businessAccount.getUsername(), true,
                category.getName(), categorySpecifications, description, amount);
        System.out.println("MCI");
        newCommodity.setProductFilePathOnSellerClient(productFilePath);
        System.out.println("MUN");
        Request request = new Request(newCommodity, businessAccount.getUsername());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        dos.writeUTF("New Commodity");
        System.out.println("CHE");
        dis.readUTF();
        System.out.println("DOR");
        dos.writeUTF(yaGson.toJson(request, new TypeToken<Request>() {
        }.getType()));
        System.out.println("JUV");
        if (dis.readUTF().equals("send picture")) {
            System.out.println("INT");
            long fileSize = new File(imagePath).length();
            System.out.println("LIV");
            dos.writeUTF(fileSize + "");
            System.out.println("MIL");
            FileInputStream file = new FileInputStream(imagePath);
            System.out.println("AJA");
            byte[] buffer = new byte[Constants.FILE_BUFFER_SIZE];
            System.out.println("LAZ");
            while (file.read(buffer) > 0) {
                dos.write(buffer);
                System.out.println("ATL");
            }
            file.close();
            System.out.println("SCH");
        }
    }

    public static void removeProduct(int productId) throws IOException {
        getBusinessAccount().removeCommodity(productId);
        System.out.println("remove commodity with id " + productId);
        outputStream.writeUTF("remove commodity with id " + productId);
        outputStream.flush();
    }
}
