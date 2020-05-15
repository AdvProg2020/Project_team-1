package view;

import controller.*;
import controller.commodity.CommentsMenu;
import controller.commodity.DigestMenu;
import controller.customer.CartMenu;
import controller.customer.OrderMenu;
import controller.reseller.ManageResellerOffsMenu;
import controller.reseller.ManageResellerProductsMenu;
import controller.reseller.ResellerMenu;
import model.*;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;
import model.account.SimpleAccount;
import model.field.Field;
import model.log.BuyLog;
import model.log.SellLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    public static final GetDiscountCode getDiscountCode = new GetDiscountCode();
    public static final ManagerMenu managerMenu = new ManagerMenu();
    public static final ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
    public static final ManageUsersMenu manageUsersMenu = new ManageUsersMenu();
    public static final CustomerMenu customerMenu = new CustomerMenu();
    public static final CartMenu cartMenu = new CartMenu();
    public static final CommodityMenu commodityMenu = new CommodityMenu();
    public static final OrderMenu orderMenu = new OrderMenu();
    public static final LoginRegisterMenu loginRegisterMenu = new LoginRegisterMenu();
    public static final ManageRequestMenu manageRequestMenu = new ManageRequestMenu();
    public static final ResellerMenu resellerMenu = new ResellerMenu();
    public static final ManageResellerProductsMenu manageResellerProductsMenu = new ManageResellerProductsMenu();
    public static final ManageResellerOffsMenu manageResellerOffMenu = new ManageResellerOffsMenu();
    public static final OffMenu offMenu = new OffMenu();
    public static final DigestMenu digestMenu = new DigestMenu();
    public static final CommentsMenu commentsMenu = new CommentsMenu();
    public static final ProductsMenu productsMenu = new ProductsMenu();
    public static final ManageCategoryMenu manageCategoryMenu = new ManageCategoryMenu();
    private final Scanner scanner = new Scanner(System.in);

    public View() {
        initializeManageRequestMenuCommandProcessor();
        initializeGetDiscountCodeMenu();
        initializeManageUsersMenu();
        initializeViewPersonalMenu();
        initializeLoginRegisterMenu();
        initializeManageUsersMenu();
        initializeViewPersonalInfoMenu();
        initializeManagerMenu();
        initializeCustomerMenu();
        initializeCartMenu();
        initializeOrderMenu();
        initializeOffMenu();
        initializeCommodityMenu();
        initializeDigestMenu();
        initializeManageCategoryMenu();
        initializeCommentsMenu();
    }

    private void initializeCommentsMenu() {
        digestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("add comment")) {
                    addComment();
                }
            }
        };
    }

    private void addComment() {
        System.out.println("enter title:");
        String title = scanner.nextLine();
        System.out.println("enter your comment");
        String content = scanner.nextLine();
        commentsMenu.addComment(title, content);
    }

    private void initializeDigestMenu() {
        digestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("add to cart")) {
                    addToCart();
                }
            }
        };
    }

    private void addToCart() {
        try {
            digestMenu.addToCart();
            System.out.println("successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeCommodityMenu() {
        commodityMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("digest")) {
                    digest();
                } else if (command.equals("attributes")) {
                    attributes();
                } else if (command.matches("compare (?<id>\\d+)")) {
                    compare(command);
                } else if (command.equals("comments")) {
                    comments();
                }
            }
        };
    }

    private void compare(String command) {
        Matcher matcher = Pattern.compile("compare (?<id>\\d+)").matcher(command);
        int id = Integer.parseInt(matcher.group("id"));
        try {
            Commodity comparingCommodity = commodityMenu.compare(id);
            Commodity commodity = commodityMenu.getCommodity();
            Category category = commodity.getCategory();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void comments() {
        Commodity commodity = commodityMenu.getCommodity();
        String output = "";
        for (Comment comment : commodity.getAllComments()) {
            if (comment.getStatus() == Status.VERIFIED) {
                output += comment.getInformation() + '\n';
            }
        }
        System.out.print(output);
        commodityMenu.goToCommentsMenu(commentsMenu);
    }

    private void attributes() {
        Commodity commodity = commodityMenu.getCommodity();
        String respond = commodity.toString();
        for (Field categorySpecification : commodity.getCategorySpecifications()) {
            respond += "[" + categorySpecification.toString() + "]";
        }
        System.out.println(respond);
    }

    private void digest() {
        Commodity commodity = commodityMenu.getCommodity();
        System.out.println(commodity.toString());
        commodityMenu.goToDigestMenu(digestMenu);
    }

    private void manageRequests(ManagerMenu managerMenu) throws IOException {
        String output = "";
        Request[] allRequests = managerMenu.getAllRequests();
        for (Request request : allRequests) {
            output += "[" + request.getSimpleAccount().getUsername() + "]";
        }
        System.out.println(output);
    }

    private void deleteDiscountCode(DiscountCode discountCode) {
        try {
            getDiscountCode.deleteDiscountCode(discountCode);
            System.out.println("discount code successfully deleted");
        } catch (Exception e) {
            System.out.println("Cant delete discount code");
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
        getDiscountCode.changeStartDate(startDate, discountCode);
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
        getDiscountCode.changeFinishDate(finishDate, discountCode);
    }


    private void editManagerAccountFields(ViewPersonalInfoMenu viewPersonalInfoMenu, String command) throws Exception {
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

    private void initializeGetDiscountCodeMenu() {
        getDiscountCode.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("^view discount code (?<code>\\S+)$")) {
                    Matcher matcher = Pattern.compile("^view discount code (?<code>\\S+)$").matcher(command);
                    viewDiscountCode(getDiscountCode, matcher.group("code"));
                }
                if (command.equals("^edit discount code (?<code>\\S+) (?<field>\\S+ ?\\S+ \\S+) (?<newField> \\S+)$")) {
                    Matcher matcher = Pattern.compile("^edit discount code (?<code>\\S+) (?<field>\\S+ ?\\S+ \\S+) (?<newField> \\S+)$").matcher(command);
                    DiscountCode discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    editDiscountCode(discountCode, command);
                }
                if (command.equals("remove discount code (?<code>\\S+)")) {
                    Matcher matcher = Pattern.compile("remove discount code (?<code>\\S+)").matcher(command);
                    DiscountCode discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    deleteDiscountCode(discountCode);
                }
            }
        };
    }

    private void initializeViewPersonalMenu() {
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$")) {
                    editManagerAccountFields(viewPersonalInfoMenu, command);
                }
            }
        };
    }


    private void initializeManagerMenu() {
        managerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("view personal info")) {
                    viewPersonalInfo();
                }
                if (command.matches("^manage users$")) {
                    manageUsers(manageUsersMenu);
                }
                if (command.matches("^create discount code$")) {
                    createDiscountCode(managerMenu);
                }
                if (command.matches("^view discount codes$")) {
                    viewDiscountCodes(managerMenu);
                }
                if (command.matches("^manage requests$")) {
                    manageRequests(managerMenu);
                }
                if (command.matches("^manage categories$")) {
                    manageCategories();
                }
            }
        };
    }

    private void editDiscountCode(DiscountCode discountCode, String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<code>\\S+) (?<field>\\S+ ?\\S+ ?\\S+ ?\\S+) (?<newfield> \\S+)$").matcher(command);
        if (matcher.group("field").equals("code")) {
            getDiscountCode.changeCode(matcher.group("new field"), discountCode);
        }
        if (matcher.group("field").equals("maximum discount price")) {
            try {
                getDiscountCode.changeMaximumDiscountPrice(Integer.parseInt(matcher.group("new field")), discountCode);
            } catch (Exception e) {
                System.out.println("invalid maximum discount price");
            }
        }
        if (matcher.group("field").equals("maximum number of uses")) {
            try {
                getDiscountCode.changeMaximumNumberOfUses(Integer.parseInt(matcher.group("new field")), discountCode);
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
                getDiscountCode.addAccount(userName, discountCode);
                System.out.println("Account added successfully");
            } catch (Exception e) {
                System.out.println("Invalid user name");
            }
        }
        if (matcher.group("field").equals("delete account")) {
            String userName = matcher.group("new field");
            try {
                getDiscountCode.deleteAccount(userName, discountCode);
                System.out.println("Account deleted successfully");
            } catch (Exception e) {
                System.out.println("Invalid user name");
            }
        }
    }

    private void initializeViewPersonalInfoMenu() {
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield> \\S+)$")) {
                    editFields(viewPersonalInfoMenu, command);
                }
            }
        };
    }

    private void initializeManageUsersMenu() {
        manageUsersMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("view (?<username>\\S+)")) {
                    Matcher matcher = Pattern.compile("^view (?<username>\\S+)$").matcher(command);
                    SimpleAccount simpleAccount = manageUsersMenu.getAccountWithUserNameFromDatabase(matcher.group("username"));
                    System.out.println(simpleAccount.toString());
                }
                if (command.matches("^delete user (?<username> \\S+)")) {
                    deleteUser(command, manageUsersMenu);
                }
                if (command.matches("^create manager profile$")) {
                    createManagerProfile(manageUsersMenu);
                }
            }
        };
    }

    private void initializeOrderMenu() {
        orderMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("show order (?<id>\\S+)")) {
                    showOrder(command);
                } else if (command.matches("rate (?<id>\\d+) (?<rate>[1-5])")) {
                    rate(command);
                }
            }
        };
    }

    private void rate(String command) {
        Matcher matcher = Pattern.compile("rate (?<id>\\d+) (?<rate>[1-5])").matcher(command);
        int id = Integer.parseInt(matcher.group("id"));
        int rate = Integer.parseInt(matcher.group("rate"));
        try {
            orderMenu.rateProduct(id, rate);
            System.out.println("thanks for your contribution");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showOrder(String command) {
        Matcher matcher = Pattern.compile("show order (?<id>\\S+)").matcher(command);
        String id = matcher.group("id");
        try {
            BuyLog log = orderMenu.getOrderWithId(id);
            System.out.println(log.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeCustomerMenu() {
        customerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.equals("view personal info")) {
                    viewPersonalInfo();
                } else if (command.equals("view cart")) {
                    goToCartMenu(cartMenu, customerMenu);
                } else if (command.equals("purchase")) {
                    purchase(cartMenu);
                } else if (command.equals("view orders")) {
                    viewOrders(orderMenu, customerMenu);
                } else if (command.equals("view balance")) {
                    System.out.println("your balance is " + customerMenu.getBalance() + "\n" +
                            "enter your command");
                } else if (command.equals("view discount codes"))
                    viewMyDiscountCodes(customerMenu);
            }
        };
    }

    private void initializeCartMenu() {
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
                    System.out.println("total price is " + cartMenu.calculateTotalPrice() + "\n" +
                            "enter next command");
                } else if (command.equals("purchase")) {
                    purchase(cartMenu);
                }
            }
        };
    }

    private void viewOrders(OrderMenu orderMenu, CustomerMenu customerMenu) {
        String output = "your orders:";
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            output += "\n" + log.toString();
        }
        System.out.println(output + "\nenter your command:");
        HandleMenu.setMenu(orderMenu);
        orderMenu.setPreviousMenu(customerMenu);
    }

    private void viewMyDiscountCodes(CustomerMenu customerMenu) {
        String output = "your discount codes:";
        for (DiscountCode discount : customerMenu.getMyDiscounts()) {
            output += "\n" + discount.toString();
        }
        System.out.println(output);
    }

    private void goToCartMenu(CartMenu cartMenu, CustomerMenu customerMenu) {
        System.out.println("enter your command");
        cartMenu.setPreviousMenu(customerMenu);
        HandleMenu.setMenu(cartMenu);
    }

    private void purchase(CartMenu cartMenu) {
        System.out.println("please enter your address");
        String address = scanner.nextLine();
        System.out.println("please enter your phone number");
        String phone = scanner.nextLine();
        while (!phone.matches("0\\d{10}")) {
            System.out.println("please enter a valid phone number");
            phone = scanner.nextLine();
        }
        System.out.println("please enter your postal code");
        String postalCode = scanner.nextLine();
        while (!postalCode.matches("\\d{10}")) {
            System.out.println("please enter a valid postal code");
            postalCode = scanner.nextLine();
        }
        System.out.println("please enter a discount code or enter nothing");
        String code = scanner.nextLine();
        boolean done = false;
        int price = cartMenu.calculateTotalPrice();
        DiscountCode discountCode = null;
        while (!done) {
            try {
                discountCode = cartMenu.checkDiscountCode(code);
                if (discountCode.getMaximumDiscountPrice() <= discountCode.getDiscountPercentage() * price / 100) {
                    price -= discountCode.getMaximumDiscountPrice();
                } else {
                    price -= discountCode.getDiscountPercentage() * price / 100;
                }
                done = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        if (price <= account.getCredit()) {
            account.addToCredit(-price);
            BuyLog buyLog = new BuyLog(new Date(), account.getCart().keySet(), price, discountCode, address, phone,
                    postalCode);
            account.addBuyLog(buyLog);
            Set<BusinessAccount> sellers = buyLog.getSellers();
            for (BusinessAccount seller : sellers) {
                Set<Commodity> commodities = new HashSet<>();
                int received = 0;
                for (Commodity commodity : account.getCart().keySet()) {
                    if (commodity.getSeller().getUsername().equals(seller.getUsername())) {
                        commodities.add(commodity);
                        received += commodity.getPrice();
                    }
                }
                // to do
                seller.addSellLog(new SellLog(new Date(), received, 0, commodities, account));
            }
            return;
        }
        System.out.println("you don't have enough money to pay");
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

    private void viewPersonalInfo() {
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
        resellerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.equalsIgnoreCase("view personal info")) {
                        viewPersonalInfo();
                    } else if (command.equalsIgnoreCase("view company info")) {
                        viewCompanyInfo();
                    } else if (command.equalsIgnoreCase("view sales history")) {
                        viewSalesHistory();
                    } else if (command.equalsIgnoreCase("manage products")) {
                        manageResellerProduct();
                    } else if (command.equalsIgnoreCase("add product")) {
                        addProduct();
                    } else if (command.matches("^remove product \\d+$")) {
                        removeProduct(command);
                    } else if (command.equalsIgnoreCase("show categories")) {
                        showCategories();
                    } else if (command.equalsIgnoreCase("view offs")) {
                        viewResellerOff();
                    } else if (command.equalsIgnoreCase("view balance")) {
                        viewResellerBalance();
                    } else if (command.equalsIgnoreCase("back")) {
                        resellerMenu.goToPreviousMenu();
                    } else {
                        throw new Exception("Invalid command");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    private void viewCompanyInfo() {
        System.out.println(resellerMenu.getBusinessAccount().getBusinessName());
    }

    private void viewSalesHistory() {
        BusinessAccount businessAccount = resellerMenu.getBusinessAccount();
        for (SellLog sellLog : businessAccount.getSellLogs()) {
            System.out.println(sellLog.toString());
        }
    }

    private void manageResellerProduct() throws Exception {
        for (Commodity commodity : resellerMenu.manageCommodities()) {
            System.out.println(commodity.toString());
        }
    }

    private void addProduct() throws Exception {
        System.out.println("enter product brand:");
        String brand = scanner.nextLine();
        System.out.println("enter product name:");
        String name = scanner.nextLine();
        System.out.println("enter product price:");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter product category:");
        String categoryString = scanner.nextLine();
        Category category = resellerMenu.getCategoryByName(categoryString);
        System.out.println("enter product specification:");
        ArrayList<Field> productCategorySpecification = new ArrayList<>();
        for (int i = 0; i < category.getFieldOptions().size(); ++i) {
            System.out.println("Enter product " + category.getFieldOptions() + ":");
        }
        System.out.println("enter product description:");
        String description = scanner.nextLine();
        System.out.println("enter product amount:");
        int amount = scanner.nextInt();
        scanner.nextLine();
    }

    private void removeProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^remove product (?<productId>\\d+)$").matcher(command);
        int productId = Integer.parseInt(matcher.group("productId"));
        resellerMenu.removeProduct(productId);
    }

    private void showCategories() throws Exception {
        for (Category category : DataManager.getAllCategories()) {
            System.out.println(category.toString());
        }
    }

    private void viewResellerBalance() {
        System.out.println(resellerMenu.getBusinessAccount().getCredit());
    }

    private void initializeManageResellerProductMenu() {
        manageResellerProductsMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("^view (\\d+)$")) {
                        viewResellerProduct(command);
                    } else if (command.matches("^view buyers (\\d+)$")) {
                        viewResellerProductBuyers(command);
                    } else if (command.matches("^edit (\\d+)$")) {
                        editResellerProduct(command);
                    } else if (command.equalsIgnoreCase("back")) {
                        manageResellerProductsMenu.goToPreviousMenu();
                    } else {
                        throw new Exception("Invalid command");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    public void viewResellerProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^view (?<productId>\\d+)$").matcher(command);
        int productId = Integer.parseInt(matcher.group("productId"));
        System.out.println(manageResellerProductsMenu.getCommodityById(productId).toString());
    }

    public void viewResellerProductBuyers(String command) throws Exception {
        Matcher matcher = Pattern.compile("^view buyers (?<productId>\\d+)$").matcher(command);
        int productId = Integer.parseInt(matcher.group("productId"));
        for (SimpleAccount simpleAccount : manageResellerProductsMenu.getBuyersById(productId)) {
            System.out.println(simpleAccount.getUsername());
        }
    }

    public void editResellerProduct(String command) {
        Matcher matcher = Pattern.compile("^edit (?<productId>\\d+)$").matcher(command);
        int productId = Integer.parseInt(matcher.group("productId"));

    }

    private void initializeManageResellerOffMenu() {
        manageResellerOffMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("^view \\w+$")) {

                    } else if (command.matches("^edit \\w+$")) {

                    } else if (command.equalsIgnoreCase("add off")) {

                    } else if (command.equalsIgnoreCase("back")) {
                        manageResellerOffMenu.goToPreviousMenu();
                    } else {
                        throw new Exception("Invalid command");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    public void viewResellerOff() {

    }

    public void editResellerOff(String command) {

    }

    public void addOff() {

    }

    private void initializeOffMenu() {
        offMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.equalsIgnoreCase("offs")) {
                        showOffs();
                    } else if (command.matches("^show product \\w+$")) {
                        showOffProduct(command);
                    } else if (command.equalsIgnoreCase("back")) {
                        offMenu.goToPreviousMenu();
                    } else {
                        throw new Exception("Invalid command");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    private void showOffs() throws Exception {
        for (Off off : DataManager.getAllOffs()) {
            System.out.println(off.toString());
        }
    }

    private void showOffProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^show product (?<productId>\\w+)$").matcher(command);
        int productId = Integer.parseInt(matcher.group("productId"));
        DataManager.getCommodityById(productId).toString();
    }

    private void initializeManageRequestMenuCommandProcessor() {
        manageRequestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^details (?<requestId> \\S+)")) {
                    Matcher matcher = Pattern.compile("^details (?<requestId> \\S+)").matcher(command);
                    viewRequestDetails(Integer.parseInt(matcher.group("requestId")));
                }
                if (command.matches("^accept (?<requestId> \\S+)")) {
                    Matcher matcher = Pattern.compile("^accept (?<requestId> \\S+)").matcher(command);
                    acceptRequest(Integer.parseInt(matcher.group("requestId")));
                }
                if (command.matches("^decline (?<requestId> \\S+)")) {
                    Matcher matcher = Pattern.compile("^decline (?<requestId> \\S+)").matcher(command);
                    declineRequest(Integer.parseInt(matcher.group("requestId")));
                }
            }
        };
    }


    private void viewRequestDetails(int id) {
        try {
            System.out.println(manageRequestMenu.getRequestById(id).getSimpleAccount().getUsername());
        } catch (Exception e) {
            System.out.println("invalid id");
        }
    }

    private void acceptRequest(int id) {
        try {
            manageRequestMenu.accept(id);
            System.out.println("request accepted");
        } catch (Exception e) {
            System.out.println("invalid id");
        }
    }

    private void declineRequest(int id) {
        try {
            manageRequestMenu.decline(id);
            System.out.println("request declined");
        } catch (Exception e) {
            System.out.println("invalid id");
        }
    }

    private void initializeProductsMenu() {
        productsMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("^view categories$")) {

                }
                if (command.matches("^filtering$")) {

                }
                if (command.matches("^$")) {

                }
                if (command.matches("^$")) {

                }
                if (command.matches("^$")) {

                }
                if (command.matches("^$")) {

                }
                if (command.matches("^$")) {

                }
            }
        };
    }

    private void manageCategories() throws FileNotFoundException {
        Category[] categories = managerMenu.manageCategory();
        System.out.println("all categories:");
        for (Category category : categories) {
            System.out.println("\n" + category.getName());
        }
    }

    private void initializeManageCategoryMenu() {
        manageCategoryMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("edit (?<category> \\S+)")) {
                    Matcher matcher = Pattern.compile("edit (?<category> \\S+)").matcher(command);
                    editCategory(matcher.group("category"));
                }
                if (command.matches("add (?<category> \\S+)")) {
                    Matcher matcher = Pattern.compile("add (?<category> \\S+)").matcher(command);
                    addCategory(matcher.group("category"));
                }
                if (command.matches("remove (?<category> \\S+)")) {
                    Matcher matcher = Pattern.compile("remove (?<category> \\S+)").matcher(command);
                }
            }
        };
    }

    private void removeCategory(String categoryName) throws IOException {
        if (!manageCategoryMenu.checkCategoryName(categoryName)) {
            System.out.println("invalid category name");
            return;
        }
        manageCategoryMenu.removeCategory(categoryName);
    }

    private void addCategory(String categoryName) throws Exception {
        if (manageCategoryMenu.checkCategoryName(categoryName)) {
            System.out.println("This name is not available");
            return;
        }
        ArrayList<CategorySpecification> categorySpecifications = new ArrayList<>();
        getCategorySpecificationFromUser(categorySpecifications);
        System.out.println("Enter commodities id in a line");
        ArrayList<Integer> commoditiesId = new ArrayList<Integer>();
        while (scanner.hasNextInt())
            commoditiesId.add(scanner.nextInt());
        manageCategoryMenu.addCategory(categoryName, commoditiesId, categorySpecifications);
    }

    private void getCategorySpecificationFromUser(ArrayList<CategorySpecification> categorySpecifications) {
        String tmp = "NO";
        while (tmp.equalsIgnoreCase("NO")) {
            System.out.println("Enter title");
            String title = scanner.nextLine();
            System.out.println("Enter options");
            HashSet<String> options = new HashSet<String>();
            while (scanner.hasNext())
                options.add(scanner.next());
            categorySpecifications.add(manageCategoryMenu.createCategorySpecification(title, options));
            System.out.println("Do you want to add another category specification");
            tmp = scanner.nextLine();
        }
    }

    private void editCategory(String categoryName) throws FileNotFoundException {
        if (!manageCategoryMenu.checkCategoryName(categoryName)) {
            System.out.println("invalid category name");
            return;
        }
        Category category = manageCategoryMenu.getCategory(categoryName);
        System.out.println("Which field do you want to edit");
        String field = scanner.nextLine();
        if (field.equalsIgnoreCase("name")) {
            System.out.println("Enter your new name");
            String newName = scanner.nextLine();
            manageCategoryMenu.changeName(newName, category);
        }
        if (field.equalsIgnoreCase("add category specification")) {
            addCategorySpecification(category);
        }
        if (field.equalsIgnoreCase("remove category specification")) {
            removeCategorySpecification(category);
        }
        if (field.equalsIgnoreCase("add commodity")) {
            addCommodityToCategory(category);
        }
        if (field.equalsIgnoreCase("remove commodity")) {
            removeCommodityFromCategory(category);
        }
    }

    private void addCategorySpecification(Category category) {
        System.out.println("Enter your new title");
        String title = scanner.nextLine();
        System.out.println("Enter options");
        HashSet<String> options = new HashSet<String>();
        while (scanner.hasNext())
            options.add(scanner.next());
        manageCategoryMenu.addCategorySpecification(options, title, category);
    }

    private void removeCategorySpecification(Category category) {
        System.out.println("Enter category specification title");
        String title = scanner.nextLine();
        manageCategoryMenu.removeCategorySpecification(title, category);
    }

    private void addCommodityToCategory(Category category) {
        System.out.println("Enter commodityID");
        int id = scanner.nextInt();
        try {
            manageCategoryMenu.addCommodity(id, category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeCommodityFromCategory(Category category) {
        System.out.println("Enter commodityID");
        int id = scanner.nextInt();
        try {
            manageCategoryMenu.removeCommodity(id, category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void run() {

    }
}
