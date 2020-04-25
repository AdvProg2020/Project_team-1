package main;

import controller.CommandProcess;
import controller.HandleMenu;
import controller.ProductsMenu;

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

    public static void setConsoleScanner(Scanner consoleScanner) {
        Main.consoleScanner = consoleScanner;
    }
}
