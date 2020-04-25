package main;

import commands.Filter;
import controller.CommandProcess;
import controller.HandleMenu;
import controller.ProductsMenu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static Scanner consoleScanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            String command = getConsoleScanner().nextLine();
            CommandProcess menu = HandleMenu.getMenu();
            String respond = menu.commandProcessor(command);
            if (respond != null)
                System.out.println(respond);
        }
    }

    public static Scanner getConsoleScanner() {
        return consoleScanner;
    }

    public static String scan(){
        return consoleScanner.nextLine();
    }

    public static ArrayList<String> getAllOptions(HashSet<String> allOptions){
        ArrayList<String> options = new ArrayList<String>();
        System.out.println(allOptions.toString());
        while (true){
            String option = consoleScanner.nextLine();
            if (option.equals("end"))
                return options;
            if (Filter.isOptionAvailable(option , allOptions)){
                options.add(option);
            }
        }
    }

    public static int getStartRange(){
            return consoleScanner.nextInt();
    }

    public static int getEndRange(){
        return consoleScanner.nextInt();
    }

    public static void setConsoleScanner(Scanner consoleScanner) {
        Main.consoleScanner = consoleScanner;
    }
}
