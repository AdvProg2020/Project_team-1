package view;

import controller.*;
import model.*;
import controller.customer.CartMenu;
import model.Commodity;
import model.DataManager;
import model.DiscountCode;
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
    private final Scanner scanner = new Scanner(System.in);

    public View() {
        initializeLoginRegisterMenu();
        final GetDiscountCode getDiscountCode = new GetDiscountCode();
        final ManagerMenu managerMenu = new ManagerMenu();
        final ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
        final ManageUsersMenu manageUsersMenu = new ManageUsersMenu();
        final CustomerMenu customerMenu = new CustomerMenu();
        final CartMenu cartMenu = new CartMenu();
        final CommodityMenu commodityMenu = new CommodityMenu();
        ManageRequestMenu manageRequestMenu = new ManageRequestMenu();
        initializeManageRequestMenuCommandProcessor(manageRequestMenu);
        initializeGetDiscountCodeMenu(getDiscountCode);
        initializeManageUsers(manageUsersMenu);
        initializeViewPersonalMenu(viewPersonalInfoMenu);
        initializeManagerMenu(managerMenu, viewPersonalInfoMenu, manageUsersMenu);
        initializeCustomerMenu(viewPersonalInfoMenu, customerMenu, cartMenu);
        initializeCartMenu(cartMenu, commodityMenu);
    }

    private void viewRequestDetails(){

    }

    private void initializeManagerMenu(final ManagerMenu managerMenu, final ViewPersonalInfoMenu viewPersonalInfoMenu, final ManageUsersMenu manageUsersMenu) {
        managerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("view personal info")) {
                    viewPersonalInfo(viewPersonalInfoMenu,managerMenu);
                }
                if (command.matches("^manage users$")) {
                    manageUsers(manageUsersMenu);
                }
                if (command.matches("^create discount code$")) {
                    createDiscountCode(managerMenu);
                }
                if (command.matches("^view discount codes$")){
                    viewDiscountCodes(managerMenu);
                }
                if (command.matches("^manage requests$")){
                    manageRequests(managerMenu);
                }
            }
        };
    }

    private void initializeManageRequestMenuCommandProcessor(ManageRequestMenu manageRequestMenu) {
        manageRequestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {

            }
        };
    }

    private void initializeViewPersonalMenu(final ViewPersonalInfoMenu viewPersonalInfoMenu) {
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$")) {
                    editManagerAccountFields(viewPersonalInfoMenu,command);
                }
            }
        };
    }

    private void manageRequests(ManagerMenu managerMenu) throws IOException {
        String output = "";
        Request[] allRequests =  managerMenu.getAllRequests();
        for (Request request : allRequests) {
            output += "["+request.getSimpleAccount().getUsername()+"]";
        }
        System.out.println(output);
    }

    private void initializeGetDiscountCodeMenu(final GetDiscountCode getDiscountCode) {
        getDiscountCode.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("^view discount code (?<code>\\S+)$")){
                    Matcher matcher = Pattern.compile("^view discount code (?<code>\\S+)$").matcher(command);
                    viewDiscountCode(getDiscountCode, matcher.group("code"));
                }
                if (command.equals("^edit discount code (?<code>\\S+) (?<field>\\S+ ?\\S+ \\S+) (?<newField> \\S+)$")){
                    Matcher matcher = Pattern.compile("^edit discount code (?<code>\\S+) (?<field>\\S+ ?\\S+ \\S+) (?<newField> \\S+)$").matcher(command);
                    DiscountCode discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    editDiscountCode(discountCode,getDiscountCode,command);
                }
                if (command.equals("remove discount code (?<code>\\S+)")){
                    Matcher matcher = Pattern.compile("remove discount code (?<code>\\S+)").matcher(command);
                    DiscountCode discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    deleteDiscountCode(discountCode,getDiscountCode);
                }
            }
        };
    }

    private void initializeManageUsers(final ManageUsersMenu manageUsersMenu) {
        manageUsersMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("view (?<username>\\S+)")) {
                    Matcher matcher = Pattern.compile("^view (?<username>\\S+)$").matcher(command);
                    SimpleAccount simpleAccount = manageUsersMenu.getAccountWithUserNameFromDatabase(matcher.group("username"));
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
    }

    private void deleteDiscountCode(DiscountCode discountCode,GetDiscountCode getDiscountCode){
        try {
            getDiscountCode.deleteDiscountCode(discountCode);
            System.out.println("discount code successfully deleted");
        } catch (Exception e) {
            System.out.println("Cant delete discount code");
        }
    }

    private void editDiscountCode(DiscountCode discountCode,GetDiscountCode getDiscountCode,String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<code>\\S+) (?<field>\\S+ ?\\S+ ?\\S+ ?\\S+) (?<newfield> \\S+)$").matcher(command);
        if (matcher.group("field").equals("code")) {
            getDiscountCode.changeCode(matcher.group("new field"),discountCode);
        }
        if (matcher.group("field").equals("maximum discount price")) {
            try {
                getDiscountCode.changeMaximumDiscountPrice(Integer.parseInt(matcher.group("new field")),discountCode);
            } catch (Exception e) {
                System.out.println("invalid maximum discount price");
            }
        }
        if (matcher.group("field").equals("maximum number of uses")) {
            try {
                getDiscountCode.changeMaximumNumberOfUses(Integer.parseInt(matcher.group("new field")),discountCode);
            } catch (Exception e) {
                System.out.println("invalid maximum number of uses");
            }
        }
        if (matcher.group("field").equals("start date")) {
            if (changeFinishDate(discountCode, getDiscountCode, matcher)) return;
        }
        if (matcher.group("field").equals("finish date")) {
            changeStartDate(discountCode, getDiscountCode, matcher);
        }
        if (matcher.group("field").equals("add account")) {
            String userName = matcher.group("new field");
            try {
                getDiscountCode.addAccount(userName,discountCode);
                System.out.println("Account added successfully");
            }catch (Exception e){
                System.out.println("Invalid user name");
            }
        }
        if (matcher.group("field").equals("delete account")) {
            String userName = matcher.group("new field");
            try {
                getDiscountCode.deleteAccount(userName,discountCode);
                System.out.println("Account deleted successfully");
            }catch (Exception e){
                System.out.println("Invalid user name");
            }
        }
    }

    private boolean changeFinishDate(DiscountCode discountCode, GetDiscountCode getDiscountCode, Matcher matcher) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date startDate = null;
        try {
            startDate = format.parse(matcher.group("new field"));
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return true;
        }
        getDiscountCode.changeStartDate(startDate,discountCode);
        return false;
    }

    private void changeStartDate(DiscountCode discountCode, GetDiscountCode getDiscountCode, Matcher matcher) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date finishDate = null;
        try {
            finishDate = format.parse(matcher.group("new field"));
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return;
        }
        getDiscountCode.changeFinishDate(finishDate,discountCode);
    }


    private void editManagerAccountFields(ViewPersonalInfoMenu viewPersonalInfoMenu , String command) throws Exception {
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



    private void viewPersonalInfo(ViewPersonalInfoMenu viewPersonalInfoMenu, ManagerMenu managerMenu){
        System.out.println(managerMenu.getOnlineAccount().toString());
        HandleMenu.setMenu(viewPersonalInfoMenu);
    }


    private void initializeCustomerMenu(final ViewPersonalInfoMenu viewPersonalInfoMenu, CustomerMenu customerMenu) {
        customerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("view personal info")) {
                    viewPersonalInfo(viewPersonalInfoMenu);
                } else if (command.equals("view cart")) {

                } else if (command.equals("purchase")) {

                } else if (command.equals("view orders")) {

                } else if (command.equals("view balance")) {

                } else if (command.equals("view discount codes")) {

                }
            }
        };
    }

    private void initializeCartMenu(final CartMenu cartMenu, final CommodityMenu commodityMenu) {
        cartMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("show products")) {
                    showProducts(cartMenu);
                } else if (command.matches("view (?<id>\\d+)")) {
                    viewProduct(command, commodityMenu, cartMenu);
                } else if (command.matches("increase (?<id>\\d+)")) {
                    increaseCommodityInCart(command, cartMenu);
                } else if (command.matches("decrease (?<id>\\d+)")) {
                    decreaseCommodityInCart(command, cartMenu);
                } else if (command.equals("show total price")) {
                    System.out.println("total price is " + cartMenu.calculateTotalPrice());
                } else if (command.equals("purchase")) {
                    //to do
                }
            }
        };
    }

    private void getDiscountCodeInitialize(final GetDiscountCode getDiscountCode) {
        getDiscountCode.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("^view discount code (?<code>\\S+)$")) {
                    Matcher matcher = Pattern.compile("^view discount code (?<code>\\S+)$").matcher(command);
                    viewDiscountCode(getDiscountCode, matcher.group("code"));
                }
                if (command.equals("^edit discount code (?<field>\\S+ ?\\S+) (?<newField> \\S+)$")) {

                }

            }
        };
    }

    private void decreaseCommodityInCart(String command, CartMenu cartMenu) {
        Matcher matcher = Pattern.compile("decrease (?<id>\\d+)").matcher(command);
        int id = Integer.parseInt(matcher.group("id"));
        try {
            cartMenu.decrease(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void increaseCommodityInCart(String command, CartMenu cartMenu) {
        Matcher matcher = Pattern.compile("increase (?<id>\\d+)").matcher(command);
        int id = Integer.parseInt(matcher.group("id"));
        try {
            cartMenu.increase(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewProduct(String command, CommodityMenu commodityMenu, CartMenu cartMenu) {
        Matcher matcher = Pattern.compile("view (?<id>\\d+)").matcher(command);
        try {
            int id = Integer.parseInt(matcher.group("id"));
            Commodity commodity = DataManager.getCommodityById(id);
            HandleMenu.setMenu(commodityMenu);
            commodityMenu.setCommodity(commodity);
            commodityMenu.setPreviousMenu(cartMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showProducts(CartMenu cartMenu) {
        String respond = "";
        PersonalAccount personalAccount = (PersonalAccount) DataManager.getOnlineAccount();
        for (Commodity commodity : personalAccount.getCart().keySet()) {
            respond += "[" + commodity.toString() + "]";
        }
        System.out.println(respond);
    }

    private void viewDiscountCodes(ManagerMenu managerMenu) throws Exception {
        DiscountCode[] discountCodes = managerMenu.getAllDiscountCodes();
        for (DiscountCode discountCode : discountCodes) {
            System.out.println(discountCode.toString() + "\n");
        }
    }

    private void viewDiscountCode(GetDiscountCode getDiscountCode, String code) {
        try {
            System.out.println(getDiscountCode.getDiscountCode(code).toString());
        } catch (Exception e) {
            System.out.println("Cant view discount code please try again");
        }
    }

    private void deleteUser(String command, ManageUsersMenu manageUsersMenu) throws Exception {
        Matcher matcher = Pattern.compile("^delete user (?<username> \\S+)").matcher(command);
        try {
            manageUsersMenu.deleteUser(matcher.group("username"));
        } catch (Exception e) {
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

    private void editFields(ViewPersonalInfoMenu viewPersonalInfoMenu, String command) throws Exception {
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

    private void viewPersonalInfo(ViewPersonalInfoMenu viewPersonalInfoMenu) {
        System.out.println(DataManager.getOnlineAccount().toString());
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
                    if (command.matches("create account (personal|reseller|manager) (\\S+)")) {
                        registerCommand(command);
                    } else if (command.matches("^login \\S+$")) {
                        loginCommand(command);
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

    private void loginCommand(String command) throws Exception {
        Matcher matcher = Pattern.compile("^login (?<username>\\S+)$").matcher(command);
        String username = matcher.group("username");
        System.out.println("password for " + username + ":");
        String password = scanner.nextLine();
        loginRegisterMenu.login(username, password);
        System.out.println("you successfully loged in as " + username);
    }

    private void registerCommand(String command) throws Exception {

        Pattern pattern = Pattern.compile("create account (?<type>personal|reseller|manager) (?<username>\\S+)");
        Matcher matcher = pattern.matcher(command);
        String accountType = matcher.group("type");
        String username = matcher.group("username");

        loginRegisterMenu.checkUserNameAvailability(username);

        if (accountType.equalsIgnoreCase("manager")) {
            loginRegisterMenu.isThereManagerAccount();
        }

        System.out.println("enter your first name:");
        String firstName = scanner.nextLine();

        System.out.println("enter your last name:");
        String lastName = scanner.nextLine();

        System.out.println("enter your email:");
        String email = scanner.nextLine();

        System.out.println("enter your phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("enter password:");
        String password = scanner.nextLine();

        switch (accountType) {
            case "personal":
                loginRegisterMenu.registerPersonalAccount(username, firstName, lastName, email, phoneNumber, password);
                break;

            case "reseller":
                System.out.println("enter your business name:");
                String businessName = scanner.nextLine();
                loginRegisterMenu.registerResellerAccount(username, firstName, lastName, email, phoneNumber, password, businessName);
                break;

            case "manager":
                loginRegisterMenu.registerManagerAccount(username, firstName, lastName, email, phoneNumber, password);
                break;

            default:
                System.out.println("Invalid account type. Available account types are personal/reseller/manager.");
        }
        System.out.println("You registered successfully.");
    }

    private void initializeResellerMenu() {

    }


    public void run() {

    }
}
