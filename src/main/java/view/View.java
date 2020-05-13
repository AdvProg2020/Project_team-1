package view;


import controller.*;
import model.SuperMarket;
import model.account.ManagerAccount;
import controller.HandleMenu;
import model.account.PersonalAccount;
import model.account.SimpleAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    Scanner scanner = new Scanner(System.in);

    public View() {
        ManagerMenu managerMenu = new ManagerMenu();
        final ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
        final ManageUsersMenu manageUsersMenu = new ManageUsersMenu();
        manageUsersMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("view (?<username>\\S+)")) {
                    Matcher matcher = Pattern.compile("^view (?<username>\\S+)$").matcher(command);
                    SimpleAccount simpleAccount = manageUsersMenu.getAccountWithUsername(matcher.group("username"));
                    System.out.println(simpleAccount.toString());
                }
                if (command.matches("^delete user (?<username> \\S+)")) {
                    Matcher matcher = Pattern.compile("^delete user (?<username> \\S+)").matcher(command);
                    manageUsersMenu.deleteAccountWithUsername(matcher.group("username"));
                    System.out.println("user deleted");
                }
                if (command.matches("^create manager profile$")) {
                    System.out.println("Please enter your user name");
                    String userName = scanner.nextLine();

                    if (SuperMarket.isThereAnyAccountWithThisUserName(userName)) {
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
                    ManagerAccount managerAccount = new ManagerAccount(firstName, lastName, email, phoneNumber, password);
                    manageUsersMenu.createNewManager(managerAccount);
                }
            }
        };
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$")) {
                    Matcher matcher = Pattern.compile("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$").matcher(command);
                    if (matcher.group("field").equals("first name")) {
                        viewPersonalInfoMenu.editFirstName(matcher.group("new field"), (ManagerAccount) SuperMarket.getOnlineAccount());
                    }
                    if (matcher.group("field").equals("last name")) {
                        viewPersonalInfoMenu.editLastName(matcher.group("new field"), (ManagerAccount) SuperMarket.getOnlineAccount());
                    }
                    if (matcher.group("field").equals("email")) {
                        viewPersonalInfoMenu.editEmail(matcher.group("new field"), (ManagerAccount) SuperMarket.getOnlineAccount());
                    }
                    if (matcher.group("field").equals("password")) {
                        viewPersonalInfoMenu.editPassword(matcher.group("new field"), (ManagerAccount) SuperMarket.getOnlineAccount());
                    }
                    if (matcher.group("field").equals("phone number")) {
                        viewPersonalInfoMenu.editPhoneNumber(matcher.group("new field"), (ManagerAccount) SuperMarket.getOnlineAccount());
                    }
                }
            }
        };
        managerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("view personal info")) {
                    System.out.println(((ManagerAccount) SuperMarket.getOnlineAccount()).toString());
                    HandleMenu.setMenu(viewPersonalInfoMenu);
                }
                if (command.matches("^manage users$")) {
                    ManagerMenu menu = ((ManagerMenu) HandleMenu.getMenu());
                    ArrayList<SimpleAccount> accounts = menu.getAllAccounts();
                    HandleMenu.setMenu(new ManageUsersMenu());
                    String output = accounts.get(0).getUsername();
                    for (int i = 1; i < accounts.size(); i++) {
                        output += "\n" + accounts.get(i).getUsername();
                    }
                    System.out.println(output);
                    HandleMenu.setMenu(manageUsersMenu);
                }
                if (command.matches("^create discount code$")) {
                    System.out.println("Enter start date in dd-mm-yyyy format");
                    String stringStart = scanner.nextLine();
                    if (!stringStart.matches("\\d\\d--\\d\\d-\\d\\d\\d")){
                        System.out.println("invalid date format");
                        return;
                    }
                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                    Date start = format.parse(stringStart);
                    System.out.println("Enter finish date in dd-mm-yyyy format");
                    String stringFinish = scanner.nextLine();
                    if (!stringFinish.matches("\\d\\d--\\d\\d-\\d\\d\\d")){
                        System.out.println("invalid date format");
                        return;
                    }
                    Date finish = format.parse(stringFinish);
                    System.out.println("Enter discount percentage");
                    try {
                        int discountPercentage = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("invalid discount percentage");
                        return;
                    }
                    System.out.println("Enter maximum discount price");
                    try {
                        int maximumDiscountPrice = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("invalid maximum discount price");
                        return;
                    }System.out.println("Enter maximum number of use");
                    try {
                        int maximumNumberOfUse = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("invalid maximum number of user");
                        return;
                    }
                }

            }
        };


    }

    public void run() {

    }
}
