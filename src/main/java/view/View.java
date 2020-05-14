package view;

import controller.*;
import model.DataManager;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    //    public ArrayList<CommandProcess> allMenus = new ArrayList<CommandProcess>();
//
//    public View() {
//        ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
//        viewPersonalInfoMenu.commandProcess1 = new CommandProcess() {
//            @Override
//            public String commandProcessor(String command) throws Exception {
//                try {
//                    ViewPersonalInfoMenu.filter();
//                } catch (Exception e) {
//                    System.out.println("filter nashod");
//                }
//            }
//        };
//        final ProductsMenu productsMenu = new ProductsMenu();
//        productsMenu.commandProcess = new CommandProcess() {
//            @Override
//            public String commandProcessor(String command) throws Exception {
//              if (command.equals("get products")){
//                  try {
//                      ArrayList t = productsMenu.getallProducts;
//                      t.for{
//                          System.out.println(t.name);
//                      }
//                  }catch (Exception e){
//                      System.out.println(" gerfte nashod");
//                  }
//              }
//            }
//        };
//
//        HandleMenu.getMenu().commandProcess.commandProssor;
//
//    }
    private final LoginRegisterMenu loginRegisterMenu = new LoginRegisterMenu();
    Scanner scanner = new Scanner(System.in);

    public View() {
        initializeLoginRegisterMenu();
        final ManagerMenu managerMenu = new ManagerMenu();
        final ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
        final ManageUsersMenu manageUsersMenu = new ManageUsersMenu();
        manageUsersMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("view (?<username>\\S+)")) {
                    Matcher matcher = Pattern.compile("^view (?<username>\\S+)$").matcher(command);
                    SimpleAccount simpleAccount = DataManager.getAccountWithUserName(matcher.group("username"));
                    System.out.println(simpleAccount.toString());
                }
                if (command.matches("^delete user (?<username> \\S+)")) {
                    deleteUser(command,manageUsersMenu);
                }
                if (command.matches("^create manager profile$")) {
                    createManagerProfile(manageUsersMenu);
                }
            }
        };
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$")) {
                    editFields(viewPersonalInfoMenu,command);
                }
            }
        };

        managerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("view personal info")) {
                   viewPersonalInfo(viewPersonalInfoMenu);
                }
                if (command.matches("^manage users$")) {
                    manageUsers(manageUsersMenu);
                }
                if (command.matches("^create discount code$")) {
                    createDiscountCode(managerMenu);
                }
            }
        };
    }

    private void deleteUser(String command,ManageUsersMenu manageUsersMenu) throws Exception {
        Matcher matcher = Pattern.compile("^delete user (?<username> \\S+)").matcher(command);
        try {
            DataManager.deleteAccountWithUserName(matcher.group("username"));
        }catch (Exception e){
            System.out.println("Invalid input please try again");
        }
        System.out.println("user deleted");
    }

    private void createManagerProfile(ManageUsersMenu manageUsersMenu) throws Exception {
        System.out.println("Please enter your user name");
        String userName = scanner.nextLine();
        if (DataManager.isUsernameExist(userName)) {
            System.out.println("invalid username");
            return;
        }
        System.out.println("Please enter your password");
        String password = scanner.nextLine();
        System.out.println("Please enter you first name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter you last name");
        String lastName = scanner.nextLine();
        System.out.println("Please enter you email");
        String email = scanner.nextLine();
        System.out.println("Please enter you phone number");
        String phoneNumber = scanner.nextLine();
        ManagerAccount managerAccount = new ManagerAccount(userName, firstName, lastName, email, phoneNumber, password);
        manageUsersMenu.createNewManager(managerAccount);
    }

    private void editFields(ViewPersonalInfoMenu viewPersonalInfoMenu , String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$").matcher(command);
        if (matcher.group("field").equals("first name")) {
            viewPersonalInfoMenu.editFirstName(matcher.group("new field"), (ManagerAccount) DataManager.getOnlineAccount());
        }
        if (matcher.group("field").equals("last name")) {
            viewPersonalInfoMenu.editLastName(matcher.group("new field"), (ManagerAccount) DataManager.getOnlineAccount());
        }
        if (matcher.group("field").equals("email")) {
            viewPersonalInfoMenu.editEmail(matcher.group("new field"), (ManagerAccount) DataManager.getOnlineAccount());
        }
        if (matcher.group("field").equals("password")) {
            viewPersonalInfoMenu.editPassword(matcher.group("new field"), (ManagerAccount) DataManager.getOnlineAccount());
        }
        if (matcher.group("field").equals("phone number")) {
            viewPersonalInfoMenu.editPhoneNumber(matcher.group("new field"), (ManagerAccount) DataManager.getOnlineAccount());
        }
    }

    private void manageUsers(ManageUsersMenu manageUsersMenu) throws IOException {
        ManagerMenu menu = ((ManagerMenu) HandleMenu.getMenu());
        ManagerAccount[] managerAccounts = DataManager.getAllManagers();
        PersonalAccount[] personalAccounts = DataManager.getAllPersonalAccounts();
        BusinessAccount[] businessAccounts = DataManager.getAllResellers();
        HandleMenu.setMenu(new ManageUsersMenu());
        String output = "Managers";
        for (int i = 0; i < managerAccounts.length; i++) {
            output += "\n" + managerAccounts[i].getUsername();
        }
        output += "\n" + "Business accounts";
        for (int i = 0; i < businessAccounts.length; i++) {
            output += "\n" + businessAccounts[i].getUsername();
        }
        output += "\n" + "Personal accounts";
        for (int i = 0; i < personalAccounts.length; i++) {
            output += "\n" + personalAccounts[i].getUsername();
        }
        System.out.println(output);
        HandleMenu.setMenu(manageUsersMenu);
    }

    private void viewPersonalInfo(ViewPersonalInfoMenu viewPersonalInfoMenu){
        System.out.println(((ManagerAccount) DataManager.getOnlineAccount()).toString());
        HandleMenu.setMenu(viewPersonalInfoMenu);
    }

    private void createDiscountCode(ManagerMenu managerMenu) throws IOException {
        System.out.println("Enter code");
        String code = scanner.nextLine();
        System.out.println("Enter start date in dd-mm-yyyy format");
        String stringStart = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date start = null;
        try {
            start = format.parse(stringStart);
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return;
        }
        System.out.println("Enter finish date in dd-mm-yyyy format");
        String stringFinish = scanner.nextLine();
        Date finish = null;
        try {
            finish = format.parse(stringFinish);
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return;
        }
        System.out.println("Enter discount percentage");
        int discountPercentage;
        try {
            discountPercentage = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("invalid discount percentage");
            return;
        }
        System.out.println("Enter maximum discount price");
        int maximumDiscountPrice;
        try {
            maximumDiscountPrice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("invalid maximum discount price");
            return;
        }
        System.out.println("Enter maximum number of use");
        int maximumNumberOfUse;
        try {
            maximumNumberOfUse = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("invalid maximum number of user");
            return;
        }
        if (managerMenu.checkCreateDiscountCodeErrors(start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse)) {
            System.out.println("invalid input");
            return;
        }
        System.out.println("Enter accounts");
        String accounts = scanner.nextLine();
        String[] splitCommand = accounts.split(" ");
        ArrayList<SimpleAccount> allAccount = new ArrayList<SimpleAccount>();
        for (int i = 0; i < splitCommand.length; i++) {
            SimpleAccount simpleAccount = DataManager.getAccountWithUserName(splitCommand[i]);
            if (simpleAccount == null) {
                System.out.println("user name " + splitCommand[i] + " not found");
                i--;
            }
            allAccount.add(simpleAccount);
        }
        try {
            managerMenu.addDiscountCode(code, start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse, allAccount);
        } catch (Exception e) {
            System.out.println("Invalid entry please try again");
            return;
        }
        System.out.println("Discount code successfully added");
    }


    private void initializeLoginRegisterMenu() {
        loginRegisterMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("create account (?<type>customer|seller|admin) (?<username>\\S+)")) {

                    } else if (command.matches("login (?<username>\\S+) (?<password>\\S+)")) {

                    } else if (command.equalsIgnoreCase("back")) {
                        loginRegisterMenu.goToPreviousMenu();
                    } else {
                        System.out.println("Command not found!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }


    public void run() {

    }
}
