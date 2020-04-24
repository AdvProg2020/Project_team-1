package main;

import controller.ProductsMenu;

import java.util.Scanner;

public class Main {
    private static Scanner consoleScanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            String command = getConsoleScanner().nextLine();
            ProductsMenu menu = ProductsMenu.getMenu();
            menu.commandProcessor(command);
        }
    }

    public static Scanner getConsoleScanner() {
        return consoleScanner;
    }

    public static void setConsoleScanner(Scanner consoleScanner) {
        Main.consoleScanner = consoleScanner;
    }
}
