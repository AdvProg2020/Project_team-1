package main;

import commands.*;
import commands.get_info_to_purchase.GetAddress;
import commands.get_info_to_purchase.GetDiscountCode;
import commands.get_info_to_purchase.GetPhone;
import commands.get_info_to_purchase.GetPostalCode;
import controller.*;
import controller.get_info_to_purchase.GetAddressMenu;
import controller.get_info_to_purchase.GetDiscountCodeMenu;
import controller.get_info_to_purchase.GetPhoneMenu;
import controller.get_info_to_purchase.GetPostalCodeMenu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static controller.Filtering.filteringCommands;
import static controller.LoginRegisterMenu.registerAndLoginCommands;
import static controller.ManageRequestMenu.manageRequestMenuCommands;
import static controller.ProductsMenu.getProductsMenuCommands;
import static controller.ProductsMenu.productsMenuCommands;

public class Main {
    private static Scanner consoleScanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ProductsMenu.getProductsMenuCommands().add(new ViewCategoryCommand());
        ProductsMenu.getProductsMenuCommands().add(new FilteringCommand());
        ProductsMenu.getProductsMenuCommands().add(new ShowAvailableFiltersCommand());
        ProductsMenu.getProductsMenuCommands().add(new ShowProductsCommand());
        ProductMenu.getProductMenuCommand().add(new DigestCommand());
        DigestMenu.getDigestMenuCommand().add(new AddToCartCommand());
        filteringCommands.add(new ShowAvailableFiltersCommand());
        filteringCommands.add(new Filter());
        filteringCommands.add(new CurrentFilter());
        filteringCommands.add(new DisableFilter());
        ManagerMenu.getCommands().add(new ManageUsersCommand());
        CreateDiscountCodeMenu.getCommands().add(new CreateDiscountCodeCommand());
        ManagerMenu.getCommands().add(new ViewPersonalInfoCommand());
        ManagerMenu.getCommands().add(new DiscountCodeCommand());
        ManagerMenu.getCommands().add(new ViewDiscountCodeCommand());
        ManagerMenu.getCommands().add(new ManageProducts());
        ManagerMenu.getCommands().add(new ManageCategoryCommand());
        ManageCategoryMenu.manageCategoryMenuCommands.add(new EditCategoryCommand());
        ManageCategoryMenu.manageCategoryMenuCommands.add(new RemoveCategory());
        ManageProductsMenu.getCommands().add(new RemoveCommodity());
        GetDiscountCodes.getDiscountCodeCommands().add(new ViewDiscountCodeCommand());
        GetDiscountCodes.getDiscountCodeCommands().add(new EditDiscountCode());
        manageRequestMenuCommands.add(new Details());
        manageRequestMenuCommands.add(new Accept());
        manageRequestMenuCommands.add(new Decline());
        registerAndLoginCommands.add(new LoginCommand());
        registerAndLoginCommands.add(new RegisterCommand());
        ManageUsersMenu.getCommands().add(new DeleteUser());
        PersonalAccountMenu.personalAccountMenuCommands.add(new ViewCartCommand());
        PersonalAccountMenu.personalAccountMenuCommands.add(new Purchase());
        GetCartMenu.getCartMenuCommands.add(new ShowProductsCommand());
        GetCartMenu.getCartMenuCommands.add(new ViewProductCommand());
        ManageUsersMenu.getCommands().add(new ViewUser());
        ManageUsersMenu.getCommands().add(new CreateManager());
        GetCartMenu.getCartMenuCommands.add(new IncreaseCommand());
        GetCartMenu.getCartMenuCommands.add(new DecreaseCommand());
        GetCartMenu.getCartMenuCommands.add(new GetTotalPrice());
        GetCartMenu.getCartMenuCommands.add(new Purchase());
        GetAddressMenu.commands.add(new GetAddress());
        GetPhoneMenu.commands.add(new GetPhone());
        GetPostalCodeMenu.commands.add(new GetPostalCode());
        GetDiscountCodeMenu.commands.add(new GetDiscountCode());

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
