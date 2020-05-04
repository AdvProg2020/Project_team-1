package main;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import commands.*;
import controller.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static controller.Filtering.filteringCommands;

public class Main {
    private static Scanner consoleScanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {\
        filteringCommands.add(new ShowAvailableFiltersCommand());
        filteringCommands.add(new Filter());
        filteringCommands.add(new CurrentFilter());
        filteringCommands.add(new DisableFilter());
        ManagerMenu.getCommands().add(null);
        CreateDiscountCodeMenu.getCommands().add(new CreateDiscountCodeCommand());
        ManagerMenu.getCommands().add(new ViewPersonalInfoCommand());
        ManagerMenu.getCommands().add(new DiscountCodeCommand());
        ManagerMenu.getCommands().add(new ViewDiscountCodeCommand());
        GetDiscountCodes.getDiscountCodeCommands().add(new ViewDiscountCodeCommand());
        GetDiscountCodes.getDiscountCodeCommands().add(new EditDiscountCode());
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

    public static void setConsoleScanner(Scanner consoleScanner) {
        Main.consoleScanner = consoleScanner;
    }

    public static String scan() {
        return consoleScanner.nextLine();
    }

    public static void print(String output) {
        System.out.println(output);
    }

    public static ArrayList<String> getAllOptions(HashSet<String> allOptions) {
        ArrayList<String> options = new ArrayList<String>();
        System.out.println(allOptions.toString());
        while (true) {
            String option = consoleScanner.nextLine();
            if (option.equals("end"))
                return options;
            if (Filter.isOptionAvailable(option, allOptions)) {
                options.add(option);
            }
        }
    }

    public static int getStartRange() {
        return consoleScanner.nextInt();
    }

    public static int getEndRange() {
        return consoleScanner.nextInt();
    }
}
