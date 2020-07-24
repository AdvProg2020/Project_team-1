package client.controller.share;

import client.controller.share.Menu;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.Constants;
import common.model.commodity.Commodity;
import common.model.filter.Filter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static client.Main.socket;


public class FilteringMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    public static ArrayList<Filter> currentFilters = new ArrayList<>();
    private static ArrayList<Commodity> filteredCommodities;

   /* static {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            outputStream.writeUTF("send all commodities");
            filteredCommodities = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Commodity>>() {
            }.getType());
            outputStream.writeUTF("send pictures");
            System.out.println("salam");
            int pictureName = 0;
            byte[] buffer = new byte[Constants.FILE_BUFFER_SIZE];
            new File("tmp").mkdir();
            int commodityRecievedAmount = Integer.parseInt(inputStream.readUTF());
            System.out.println(commodityRecievedAmount);
            System.out.println("salam");
            while (commodityRecievedAmount > 0) {
                pictureName = Integer.parseInt(inputStream.readUTF());
                System.out.println(pictureName);
                FileOutputStream file = new FileOutputStream("tmp\\" + pictureName);
                long counter = 0;
                long fileSize = Long.parseLong(inputStream.readUTF());
                System.out.println(fileSize);
                while (counter < fileSize) {
                    inputStream.read(buffer);
                    file.write(buffer);
                    counter += Constants.FILE_BUFFER_SIZE;
                }
                commodityRecievedAmount--;
                file.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static ArrayList<Commodity> getFilteredCommodities() {
        return filteredCommodities;
    }

    public static ArrayList<Filter> getCurrentFilters() {
        return currentFilters;
    }

    public static void updateFilteredCommodities() throws Exception {
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        filteredCommodities = new ArrayList<>();
        outputStream.writeUTF("send all commodities");
        filteredCommodities = yaGson.fromJson(inputStream.readUTF(), new TypeToken<ArrayList<Commodity>>() {
        }.getType());
        outputStream.writeUTF("send pictures");
        System.out.println("salam");
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
    }

    public void disableFilter(String filterName) throws Exception {
        currentFilters.remove(getFilterByName(filterName));
        updateFilteredCommodities();
    }

    private Filter getFilterByName(String name) throws Exception {
        for (Filter filter : currentFilters) {
            if (filter.getFilterName().equalsIgnoreCase(name))
                return filter;
        }
        throw new Exception("invalid filter name");

    }

    public void filter(Filter filter) throws Exception {
        currentFilters.add(filter);
        updateFilteredCommodities();
    }
}
