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
import model.field.NumericalField;
import model.field.OptionalField;
import model.filter.*;
import model.log.BuyLog;
import model.log.SellLog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
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
    public static final FilteringMenu filteringMenu = new FilteringMenu();
    public static final SortingMenu sortingMenu = new SortingMenu();
    public static final ManageAllProducts manageAllProducts = new ManageAllProducts();
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
        initializeResellerMenu();
        initializeManageResellerProductMenu();
        initializeManageResellerOffMenu();
        initializeOffMenu();
        initializeCommodityMenu();
        initializeDigestMenu();
        initializeManageCategoryMenu();
        initializeProductsMenu();
        initializeSortingMenu();
        initializeFilteringMenu();
        initializeCommentsMenu();
        initializeManageAllProductsMenu();
    }

    private void initializeCommentsMenu() {
        digestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    commentsMenu.products();
                    return;
                }
                if (command.equals("add comment")) {
                    addComment();
                    return;
                }
                System.out.println("invalid command");
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
                if (command.matches("products")) {
                    digestMenu.products();
                    return;
                }
                if (command.equals("add to cart")) {
                    addToCart();
                    return;
                }
                System.out.println("invalid command");
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
                if (command.matches("products")) {
                    commodityMenu.products();
                } else if (command.equals("digest")) {
                    digest();
                } else if (command.equals("attributes")) {
                    attributes();
                } else if (command.matches("compare (?<id>\\d+)")) {
                    compare(command);
                } else if (command.equals("comments")) {
                    comments();
                } else System.out.println("invalid command");
            }
        };
    }

    private void compare(String command) {
        Matcher matcher = Pattern.compile("compare (?<id>\\d+)").matcher(command);
        matcher.matches();
        int id = Integer.parseInt(matcher.group("id"));
        try {
            Commodity comparingCommodity = commodityMenu.compare(id);
            Commodity commodity = commodityMenu.getCommodity();
            System.out.format("%-15s: %-30s %-30s", "name", commodity.getName(), comparingCommodity.getName());
            System.out.format("%-15s: %-30s %-30s", "brand", commodity.getBrand(), comparingCommodity.getBrand());
            System.out.format("%-15s: %-30s %-30s", "price", commodity.getPrice(), comparingCommodity.getPrice());
            System.out.format("%-15s: %-30s %-30s", "seller", commodity.getSeller().getUsername(), comparingCommodity.
                    getSeller().getUsername());
            System.out.format("%-15s: %-30s %-30s", "amount", commodity.getInventory(), comparingCommodity.
                    getInventory());
            for (int i = 0; i < commodity.getCategorySpecifications().size(); i++) {
                Field specification = commodity.getCategorySpecifications().get(i);
                Field comparingSpecification = comparingCommodity.getCategorySpecifications().get(i);
                System.out.format("%-15s: %-30s %-30s", specification.getTitle(), specification.getValue(),
                        comparingSpecification.getValue());
            }
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
        String respond = commodity.getInformation();
        for (Field categorySpecification : commodity.getCategorySpecifications()) {
            respond += "[" + categorySpecification.toString() + "]";
        }
        System.out.println(respond);
    }

    private void digest() {
        Commodity commodity = commodityMenu.getCommodity();
        System.out.println(commodity.getInformation());
        commodityMenu.goToDigestMenu(digestMenu);
    }

    private void manageRequests(ManagerMenu managerMenu) throws IOException {
        String output = "";
        Request[] allRequests = managerMenu.manageRequest();
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
            startDate = format.parse(matcher.group("newfield"));
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
            finishDate = format.parse(matcher.group("newfield"));
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return;
        }
        getDiscountCode.changeFinishDate(finishDate, discountCode);
    }


    private void editManagerAccountFields(ViewPersonalInfoMenu viewPersonalInfoMenu, String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<field>\\S+ ?\\S+) (?<newfield>\\S+)$").matcher(command);
        matcher.matches();
        if (matcher.group("field").equals("first name")) {
            viewPersonalInfoMenu.editFirstName(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("last name")) {
            viewPersonalInfoMenu.editLastName(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("email")) {
            viewPersonalInfoMenu.editEmail(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("password")) {
            viewPersonalInfoMenu.editPassword(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("phone number")) {
            viewPersonalInfoMenu.editPhoneNumber(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
        }
    }

    private void initializeGetDiscountCodeMenu() {
        getDiscountCode.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    getDiscountCode.products();
                }
                if (command.matches("products")) {
                    getDiscountCode.products();
                    return;
                }
                if (command.matches("back")) {
                    getDiscountCode.goToPreviousMenu();
                    return;
                }
                if (command.equals("^view discount code (?<code>\\S+)$")) {
                    Matcher matcher = Pattern.compile("^view discount code (?<code>\\S+)$").matcher(command);
                    matcher.matches();
                    viewDiscountCode(getDiscountCode, matcher.group("code"));
                    return;
                }
                if (command.equals("^edit discount code (?<code>\\S+) (?<field>\\S+ ?\\S+ \\S+) (?<newField>\\S+)$")) {
                    Matcher matcher = Pattern.compile("^edit discount code (?<code>\\S+) (?<field>\\S+ ?\\S+ \\S+) (?<newField>\\S+)$").matcher(command);
                    matcher.matches();
                    DiscountCode discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    editDiscountCode(discountCode, command);
                    return;
                }
                if (command.equals("remove discount code (?<code>\\S+)")) {
                    Matcher matcher = Pattern.compile("remove discount code (?<code>\\S+)").matcher(command);
                    matcher.matches();
                    DiscountCode discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    deleteDiscountCode(discountCode);
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void initializeViewPersonalMenu() {
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    viewPersonalInfoMenu.products();
                    return;
                }
                if (command.matches("products")) {
                    viewPersonalInfoMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    viewPersonalInfoMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield>\\S+)$")) {
                    editManagerAccountFields(viewPersonalInfoMenu, command);
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }


    private void initializeManagerMenu() {
        managerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    managerMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    managerMenu.goToPreviousMenu();
                    return;
                }
                if (command.equals("view personal info")) {
                    viewPersonalInfo(managerMenu);
                    return;
                }
                if (command.matches("^manage users$")) {
                    manageUsers(manageUsersMenu);
                    return;
                }
                if (command.matches("^create discount code$")) {
                    createDiscountCode(managerMenu);
                    return;
                }
                if (command.matches("^view discount codes$")) {
                    viewDiscountCodes(managerMenu);
                    return;
                }
                if (command.matches("^manage requests$")) {
                    manageRequests(managerMenu);
                    return;
                }
                if (command.matches("^manage categories$")) {
                    manageCategories();
                    return;
                }
                if (command.matches("^manage all products")) {
                    manageAllProducts();
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void editDiscountCode(DiscountCode discountCode, String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<code>\\S+) (?<field>\\S+ ?\\S+ ?\\S+ ?\\S+) (?<newfield>\\S+)$").matcher(command);
        matcher.matches();
        if (matcher.group("field").equals("code")) {
            getDiscountCode.changeCode(matcher.group("newfield"), discountCode);
        }
        if (matcher.group("field").equals("maximum discount price")) {
            try {
                getDiscountCode.changeMaximumDiscountPrice(Integer.parseInt(matcher.group("newfield")), discountCode);
            } catch (Exception e) {
                System.out.println("invalid maximum discount price");
            }
        }
        if (matcher.group("field").equals("maximum number of uses")) {
            try {
                getDiscountCode.changeMaximumNumberOfUses(Integer.parseInt(matcher.group("newfield")), discountCode);
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
            String userName = matcher.group("newfield");
            try {
                getDiscountCode.addAccount(userName, discountCode);
                System.out.println("Account added successfully");
            } catch (Exception e) {
                System.out.println("Invalid user name");
            }
        }
        if (matcher.group("field").equals("delete account")) {
            String userName = matcher.group("newfield");
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
                if (command.matches("products")) {
                    viewPersonalInfoMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    viewPersonalInfoMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield>\\S+)$")) {
                    editFields(viewPersonalInfoMenu, command);
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void initializeManageUsersMenu() {
        manageUsersMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    manageUsersMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    manageUsersMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("view (?<username>\\S+)")) {
                    Matcher matcher = Pattern.compile("^view (?<username>\\S+)$").matcher(command);
                    matcher.matches();
                    SimpleAccount simpleAccount = manageUsersMenu.getAccountWithUserNameFromDatabase(matcher.group("username"));
                    System.out.println(simpleAccount.getInformation());
                    return;
                }
                if (command.matches("^delete user (?<username>\\S+)")) {
                    deleteUser(command, manageUsersMenu);
                    return;
                }
                if (command.matches("^create manager profile$")) {
                    createManagerProfile(manageUsersMenu);
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void initializeOrderMenu() {
        orderMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    orderMenu.products();
                    return;
                }
                if (command.matches("show order (?<id>\\S+)")) {
                    showOrder(command);
                } else if (command.matches("rate (?<id>\\d+) (?<rate>[1-5])")) {
                    rate(command);
                } else System.out.println("invalid command");
            }
        };
    }

    private void rate(String command) {
        Matcher matcher = Pattern.compile("rate (?<id>\\d+) (?<rate>[1-5])").matcher(command);
        matcher.matches();
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
        matcher.matches();
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
                if (command.matches("products")) {
                    customerMenu.products();
                    return;
                }
                if (command.equals("view personal info")) {
                    viewPersonalInfo(customerMenu);
                } else if (command.equals("view cart")) {
                    goToCartMenu();
                } else if (command.equals("purchase")) {
                    purchase();
                } else if (command.equals("view orders")) {
                    viewOrders();
                } else if (command.equals("view balance")) {
                    System.out.println("your balance is " + customerMenu.getBalance());
                } else if (command.equals("view discount codes")) {
                    viewMyDiscountCodes();
                } else System.out.println("invalid command");
            }
        };
    }

    private void initializeCartMenu() {
        cartMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    cartMenu.products();
                    return;
                }
                if (command.equals("show products")) {
                    showProducts();
                } else if (command.matches("view (?<id>\\d+)")) {
                    viewProduct(command);
                } else if (command.matches("increase (?<id>\\d+)")) {
                    increaseCommodityInCart(command);
                } else if (command.matches("decrease (?<id>\\d+)")) {
                    decreaseCommodityInCart(command);
                } else if (command.equals("show total price")) {
                    System.out.println("total price is " + cartMenu.calculateTotalPrice());
                } else if (command.equals("purchase")) {
                    purchase();
                } else System.out.println("invalid command");
            }
        };
    }

    private void viewOrders() {
        String output = "your orders:";
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            output += "\n" + log.toString();
        }
        System.out.println(output);
        customerMenu.goToOrderMenu();
    }

    private void viewMyDiscountCodes() {
        String output = "your discount codes:";
        for (DiscountCode discount : customerMenu.getMyDiscounts()) {
            output += "\n" + discount.toString();
        }
        System.out.println(output);
    }

    private void goToCartMenu() {
        System.out.println("enter your command");
        customerMenu.goToCartMenu();
    }

    private void purchase() {
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
                discountCode = cartMenu.getDiscountCodeWithCode(code);
                price = cartMenu.useDiscountCode(price, discountCode);
                done = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            cartMenu.purchase(price, discountCode, address, phone, postalCode);
            System.out.println("thanks for your purchase, see you soon!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void decreaseCommodityInCart(String command) {
        Matcher matcher = Pattern.compile("decrease (?<id>\\d+)").matcher(command);
        matcher.matches();
        int id = Integer.parseInt(matcher.group("id"));
        try {
            cartMenu.decrease(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void increaseCommodityInCart(String command) {
        Matcher matcher = Pattern.compile("increase (?<id>\\d+)").matcher(command);
        matcher.matches();
        int id = Integer.parseInt(matcher.group("id"));
        try {
            cartMenu.increase(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewProduct(String command) {
        Matcher matcher = Pattern.compile("view (?<id>\\d+)").matcher(command);
        matcher.matches();
        try {
            cartMenu.goToCommodityMenu(Integer.parseInt(matcher.group("id")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showProducts() {
        String respond = "";
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        for (Commodity commodity : personalAccount.getCart().keySet()) {
            respond += "[" + commodity.getInformation() + "]";
        }
        System.out.println(respond);
    }

    private void viewDiscountCodes(ManagerMenu managerMenu) throws Exception {
        DiscountCode[] discountCodes = managerMenu.viewDiscountCodesCommand();
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
        Matcher matcher = Pattern.compile("^delete user (?<username>\\S+)").matcher(command);
        matcher.matches();
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
        if (manageUsersMenu.checkEmail(email)){
            System.out.println("This email is unavailable");
            return;
        }
        System.out.println("Please enter you phone number");
        String phoneNumber = scanner.nextLine();
        ManagerAccount managerAccount = new ManagerAccount(userName, firstName, lastName, email, phoneNumber, password);
        manageUsersMenu.createNewManager(managerAccount);
        System.out.println("Manager profile successfully created");
    }

    private void editFields(ViewPersonalInfoMenu viewPersonalInfoMenu, String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<field>\\S+ ?\\S+) (?<newfield>\\S+)$").matcher(command);
        matcher.matches();
        if (matcher.group("field").equals("first name")) {
            viewPersonalInfoMenu.editFirstName(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
            viewPersonalInfoMenu.updateFile();
            return;
        }
        if (matcher.group("field").equals("last name")) {
            viewPersonalInfoMenu.editLastName(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
            viewPersonalInfoMenu.updateFile();
            return;
        }
        if (matcher.group("field").equals("email")) {
            viewPersonalInfoMenu.editEmail(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
            viewPersonalInfoMenu.updateFile();
            return;
        }
        if (matcher.group("field").equals("password")) {
            viewPersonalInfoMenu.editPassword(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
            viewPersonalInfoMenu.updateFile();
            return;
        }
        if (matcher.group("field").equals("phone number")) {
            viewPersonalInfoMenu.editPhoneNumber(matcher.group("newfield"), (ManagerAccount) Session.getOnlineAccount());
            viewPersonalInfoMenu.updateFile();
            return;
        }
        System.out.println("wrong field");

    }

    private void manageUsers(ManageUsersMenu manageUsersMenu) throws IOException {
        ManagerMenu menu = ((ManagerMenu) MenuHandler.getInstance().getCurrentMenu());
        ManagerAccount[] managerAccounts = DataManager.getAllManagers();
        PersonalAccount[] personalAccounts = DataManager.getAllPersonalAccounts();
        BusinessAccount[] businessAccounts = DataManager.getAllResellers();
        String output = "Managers;";
        for (int i = 0; i < managerAccounts.length; i++) {
            output += "\n" + managerAccounts[i].getUsername();
        }
        output += "\n" + "Business accounts:";
        for (int i = 0; i < businessAccounts.length; i++) {
            output += "\n" + businessAccounts[i].getUsername();
        }
        output += "\n" + "Personal accounts:";
        for (int i = 0; i < personalAccounts.length; i++) {
            output += "\n" + personalAccounts[i].getUsername();
        }
        System.out.println(output);
        managerMenu.manageUsers();
    }

    private void viewPersonalInfo(Menu menu) {
        System.out.println(Session.getOnlineAccount().getInformation());
        managerMenu.viewPersonalInfo();
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
                    if (command.matches("products")) {
                        loginRegisterMenu.products();
                        return;
                    }
                    if (command.matches("create account (personal|reseller|manager) (\\S+)")) {
                        registerCommand(command);
                    } else if (command.matches("^login \\S+$")) {
                        loginCommand(command);
                    } else if (command.equalsIgnoreCase("back")) {
                        loginRegisterMenu.goToPreviousMenu();
                    } else if (command.equalsIgnoreCase("help")) {
                        loginRegisterMenuHelp();
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
        matcher.matches();
        matcher.matches();
        String username = matcher.group("username");
        System.out.println("password for " + username + ":");
        String password = scanner.nextLine();
        loginRegisterMenu.login(username, password);
        System.out.println("you successfully logged in as " + username);
    }

    private void registerCommand(String command) throws Exception {

        Pattern pattern = Pattern.compile("create account (?<type>personal|reseller|manager) (?<username>\\S+)");
        Matcher matcher = pattern.matcher(command);
        matcher.matches();
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

    private void loginRegisterMenuHelp() {
        System.out.println("1 - create account [personal|reseller|manager] [username]\n" +
                "2 - login [username]");
    }

    private void initializeResellerMenu() {
        resellerMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("products")) {
                        resellerMenu.products();
                        return;
                    }
                    if (command.equalsIgnoreCase("view personal info")) {
                        viewPersonalInfo(resellerMenu);
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
                        viewResellerOffs();
                    } else if (command.equalsIgnoreCase("view balance")) {
                        viewResellerBalance();
                    } else if (command.equalsIgnoreCase("back")) {
                        resellerMenu.goToPreviousMenu();
                    } else if (command.equalsIgnoreCase("help")) {
                        resellerMenuHelp();
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
            System.out.println(commodity.getInformation());
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
            CategorySpecification categorySpecification = category.getFieldOptions().get(i);
            System.out.println("Enter product " + categorySpecification.getTitle() + ":");
            if (categorySpecification.getOptions().isEmpty()) {
                productCategorySpecification.add(new NumericalField(categorySpecification.getTitle(), scanner.nextInt()));
                scanner.nextLine();
            } else {
                productCategorySpecification.add(new OptionalField(categorySpecification.getTitle(), scanner.nextLine()));
            }
        }
        System.out.println("enter product description:");
        String description = scanner.nextLine();
        System.out.println("enter product amount:");
        int amount = scanner.nextInt();
        scanner.nextLine();
        resellerMenu.addProduct(brand, name, price, category, productCategorySpecification, description, amount);
    }

    private void removeProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^remove product (?<productId>\\d+)$").matcher(command);
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        resellerMenu.removeProduct(productId);
    }

    private void showCategories() throws Exception {
        for (Category category : DataManager.getAllCategories()) {
            System.out.println(category.toString());
        }
    }

    public void viewResellerOffs() throws Exception {
        for (Off off : resellerMenu.manageOffs()) {
            System.out.println(off.toString());
        }
    }

    private void viewResellerBalance() {
        System.out.println(resellerMenu.getBusinessAccount().getCredit());
    }

    private void resellerMenuHelp() {
        System.out.println("1 - view personal info\n" +
                "2 - view company info\n" +
                "3 - view sales history\n" +
                "4 - manage products\n" +
                "5 - add product\n" +
                "6 - remove product [productId]\n" +
                "7 - show categories\n" +
                "8 - view offs\n" +
                "9 - view balance");
    }

    private void initializeManageResellerProductMenu() {
        manageResellerProductsMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("products")) {
                        manageResellerProductsMenu.products();
                        return;
                    }
                    if (command.matches("^view (\\d+)$")) {
                        viewResellerProduct(command);
                    } else if (command.matches("^view buyers (\\d+)$")) {
                        viewResellerProductBuyers(command);
                    } else if (command.matches("^edit (\\d+)$")) {
                        editResellerProduct(command);
                    } else if (command.equalsIgnoreCase("back")) {
                        manageResellerProductsMenu.goToPreviousMenu();
                    } else if (command.equalsIgnoreCase("help")) {
                        manageResellerProductsMenuHelp();
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
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        System.out.println(manageResellerProductsMenu.getCommodityById(productId).getInformation());
    }

    public void viewResellerProductBuyers(String command) throws Exception {
        Matcher matcher = Pattern.compile("^view buyers (?<productId>\\d+)$").matcher(command);
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        for (SimpleAccount simpleAccount : manageResellerProductsMenu.getBuyersById(productId)) {
            System.out.println(simpleAccount.getUsername());
        }
    }

    public void editResellerProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<productId>\\d+)$").matcher(command);
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        Commodity oldCommodity = DataManager.getCommodityById(productId);
        System.out.println("enter product brand (type '-' if this field remained unchanged):");
        String brand = scanner.nextLine();
        System.out.println("enter product name (type '-' if this field remained unchanged):");
        String name = scanner.nextLine();
        System.out.println("enter product price (type '-' if this field remained unchanged):");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter product category (type '-' if this field remained unchanged):");
        String categoryString = scanner.nextLine();
        Category category;
        if (categoryString.equals("-")) {
            category = oldCommodity.getCategory();
        } else {
            category = resellerMenu.getCategoryByName(categoryString);
        }
        System.out.println("is product available?(y/n)");
        boolean availability = (scanner.nextLine().equals("y")) ? true : false;
        System.out.println("enter product specification:\n" +
                "(type '-' if optional field remained unchanged and '-1' if numerical field remained unchanged)");
        ArrayList<Field> productCategorySpecification = new ArrayList<>();
        for (int i = 0; i < category.getFieldOptions().size(); ++i) {
            CategorySpecification categorySpecification = category.getFieldOptions().get(i);
            System.out.println("Enter product " + categorySpecification.getTitle() + ":");
            if (categorySpecification.getOptions().isEmpty()) {
                int val = scanner.nextInt();
                if (val == -1) {
                    val = ((NumericalField) oldCommodity.getCategorySpecifications().get(i)).getValue();
                }
                productCategorySpecification.add(new NumericalField(categorySpecification.getTitle(), val));
                scanner.nextLine();
            } else {
                String val = scanner.nextLine();
                if (val.equals("-")) {
                    val = ((OptionalField) oldCommodity.getCategorySpecifications().get(i)).getValue();
                }
                productCategorySpecification.add(new OptionalField(categorySpecification.getTitle(), val));
            }
        }
        System.out.println("enter product description (type '-' if this field remained unchanged):");
        String description = scanner.nextLine();
        System.out.println("enter product amount (type '-1' if this field remained unchanged):");
        int amount = scanner.nextInt();
        scanner.nextLine();
        manageResellerProductsMenu.editProduct(oldCommodity, brand, name, price, availability, category, productCategorySpecification,
                description, amount);
    }

    private void manageResellerProductsMenuHelp() {
        System.out.println("1 - view [productId]\n" +
                "2 - view buyers [productId]\n" +
                "3 - edit [productId]");
    }

    private void initializeManageResellerOffMenu() {
        manageResellerOffMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("products")) {
                        manageResellerOffMenu.products();
                        return;
                    }
                    if (command.matches("^view \\d+$")) {
                        viewResellerOff(command);
                    } else if (command.matches("^edit \\w+$")) {
                        editResellerOff(command);
                    } else if (command.equalsIgnoreCase("add off")) {
                        addOff();
                    } else if (command.equalsIgnoreCase("back")) {
                        manageResellerOffMenu.goToPreviousMenu();
                    } else if (command.equalsIgnoreCase("help")) {
                        manageResellerOffMenuHelp();
                    } else {
                        throw new Exception("Invalid command");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    public void viewResellerOff(String command) throws Exception {
        Matcher matcher = Pattern.compile("^view (?<offId>\\d+)$").matcher(command);
        matcher.matches();
        int offId = Integer.parseInt(matcher.group("offId"));
        System.out.println(manageResellerOffMenu.getOffById(offId).toString());
    }

    public void editResellerOff(String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<offId>\\d+)$").matcher(command);
        matcher.matches();
        int offId = Integer.parseInt(matcher.group("offId"));
        Off oldOff = manageResellerOffMenu.getOffById(offId);
        System.out.println("enter off start time (put '-' for previous value):");
        System.out.println("(enter date in format : 'MMM d HH:mm:ss yyyy' ex. Jun 18 10:30:00 2020)");
        String startTime = scanner.nextLine();
        System.out.println("enter off end time (put '-' for previous value):");
        System.out.println("(enter date in format : 'MMM d HH:mm:ss yyyy' ex. Jun 18 10:30:00 2020)");
        String endTime = scanner.nextLine();
        System.out.println("enter off percent (put '-1' for previous value):");
        int offPercent = Integer.parseInt(scanner.nextLine());
        ArrayList<Commodity> addedProduct = new ArrayList<>();
        System.out.println("enter products id that you want to add to off");
        System.out.println("(type -1 when finished)");
        int productId = 0;

        while ((productId = scanner.nextInt()) != -1) {
            addedProduct.add(manageResellerProductsMenu.getCommodityById(productId));
            scanner.nextLine();
        }

        ArrayList<Commodity> removedProduct = new ArrayList<>();
        System.out.println("enter products id that you want to remove from off");
        System.out.println("(type -1 when finished)");

        while ((productId = scanner.nextInt()) != -1) {
            removedProduct.add(manageResellerProductsMenu.getCommodityById(productId));
            scanner.nextLine();
        }

        manageResellerOffMenu.editOff(oldOff, removedProduct, addedProduct, startTime, endTime, offPercent);
    }

    public void addOff() throws Exception {
        System.out.println("enter off start time:");
        System.out.println("(enter date in format : 'MMM d HH:mm:ss yyyy' ex. Jun 18 10:30:00 2020)");
        String startTime = scanner.nextLine();
        System.out.println("enter off end time:");
        System.out.println("(enter date in format : 'MMM d HH:mm:ss yyyy' ex. Jun 18 10:30:00 2020)");
        String endTime = scanner.nextLine();
        System.out.println("enter off percent:");
        int offPercent = Integer.parseInt(scanner.nextLine());
        ArrayList<Commodity> commoditiesInOff = new ArrayList<>();
        System.out.println("enter products id that you want to add to off");
        System.out.println("(type -1 when finished)");
        int productId = 0;
        while ((productId = scanner.nextInt()) != -1) {
            commoditiesInOff.add(manageResellerProductsMenu.getCommodityById(productId));
            scanner.nextLine();
        }
        manageResellerOffMenu.addOff(commoditiesInOff, startTime, endTime, offPercent);
    }

    private void manageResellerOffMenuHelp() {
        System.out.println("1 - view [offId]\n" +
                "2 - edit [offId]\n" +
                "3 - add off");
    }

    private void initializeOffMenu() {
        offMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("products")) {
                        offMenu.products();
                        return;
                    }
                    if (command.equalsIgnoreCase("offs")) {
                        showOffs();
                    } else if (command.matches("^show product \\w+$")) {
                        showOffProduct(command);
                    } else if (command.equalsIgnoreCase("back")) {
                        offMenu.goToPreviousMenu();
                    } else if (command.equalsIgnoreCase("help")) {
                        offMenuHelp();
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
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        DataManager.getCommodityById(productId).getInformation();
    }

    private void offMenuHelp() {
        System.out.println("1 - offs\n" +
                "2 - show product [productId]");
    }

    private void initializeManageRequestMenuCommandProcessor() {
        manageRequestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")) {
                    manageRequestMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    manageRequestMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^details (?<requestId>\\S+)")) {
                    Matcher matcher = Pattern.compile("^details (?<requestId>\\S+)").matcher(command);
                    matcher.matches();
                    viewRequestDetails(Integer.parseInt(matcher.group("requestId")));
                    return;
                }
                if (command.matches("^accept (?<requestId>\\S+)")) {
                    Matcher matcher = Pattern.compile("^accept (?<requestId>\\S+)").matcher(command);
                    matcher.matches();
                    acceptRequest(Integer.parseInt(matcher.group("requestId")));
                    return;
                }
                if (command.matches("^decline (?<requestId>\\S+)")) {
                    Matcher matcher = Pattern.compile("^decline (?<requestId>\\S+)").matcher(command);
                    matcher.matches();
                    declineRequest(Integer.parseInt(matcher.group("requestId")));
                    return;
                }
                System.out.println("invalid command");
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
                if (command.matches("products")) {
                    productsMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    productsMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^view categories$")) {
                    viewCategory();
                    return;
                }
                if (command.matches("^filtering$")) {
                    productsMenu.filtering();
                    return;
                }
                if (command.matches("^sorting$")) {
                    productsMenu.sorting();
                    return;
                }
                if (command.matches("^show products$")) {
                    String output = "products";
                    for (Commodity product : productsMenu.getProducts()) {
                        output += "\n" + product.getInformation();
                    }
                    System.out.println(output);
                    return;
                }
                if (command.matches("^show product (?<productID>\\d+)$")) {
                    Matcher matcher = Pattern.compile("^show roduct (?<productID>\\d+)$").matcher(command);
                    matcher.matches();
                    try {
                        Commodity commodity = productsMenu.getProducts(Integer.parseInt(matcher.group("productID")));
                        System.out.println(commodity.getInformation());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void manageCategories() throws IOException {
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
                if (command.matches("products")) {
                    manageCategoryMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    manageCategoryMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("edit (?<category>\\S+)")) {
                    Matcher matcher = Pattern.compile("edit (?<category>\\S+)").matcher(command);
                    matcher.matches();
                    editCategory(matcher.group("category"));
                    return;
                }
                if (command.matches("add (?<category>\\S+)")) {
                    Matcher matcher = Pattern.compile("add (?<category>\\S+)").matcher(command);
                    matcher.matches();
                    addCategory(matcher.group("category"));
                    return;
                }
                if (command.matches("remove (?<category>\\S+)")) {
                    Matcher matcher = Pattern.compile("remove (?<category>\\S+)").matcher(command);
                    matcher.matches();
                    removeCategory(matcher.group("category"));
                    return;
                }
                System.out.println("invalid command");
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

    private void editCategory(String categoryName) throws IOException {
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

    private void viewCategory() throws IOException {
        Category[] categories = productsMenu.getAllCategories();
        System.out.println("all categories:");
        for (Category category : categories) {
            System.out.println("\n" + category.getName());
        }
    }

    private void initializeFilteringMenu() {
        filteringMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")){
                    filteringMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    filteringMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("show available filters")) {
                    System.out.println("Filter by name, by category, by category specification\n");
                    return;
                }
                if (command.matches("filter (?<filter>\\S+ ?\\S+)")) {
                    Matcher matcher = Pattern.compile("filter (?<filter>\\S+ ?\\S+)").matcher(command);
                    matcher.matches();
                    filter(matcher.group("filter"));
                    return;
                }
                if (command.matches("currentfilters")) {
                    for (Filter filter : FilteringMenu.getCurrentFilters()) {
                        System.out.println("\n" + filter.getFilterName());
                    }
                    return;
                }
                if (command.matches("delete filter (?<filterName> \\.+)")) {
                    Matcher matcher = Pattern.compile("delete filter (?<filterName> \\.+)").matcher(command);
                    matcher.matches();
                    try {
                        filteringMenu.disableFilter(matcher.group("filterName"));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void filter(String filterName) throws Exception {
        if (filterName.equalsIgnoreCase("name")) {
            filterByName();
            System.out.println("Successfully filtered\n");
        }
        if (filterName.equalsIgnoreCase("category")) {
            System.out.println("Enter category name\n");
            String categoryName = scanner.nextLine();
            if (filterByCategory(categoryName))
                return;
            System.out.println("Successfully filtered\n");
        }
        if (filterName.equalsIgnoreCase("category specification")) {
            System.out.println("Enter category name\n");
            String categoryName = scanner.nextLine();
            if (filterByCategory(categoryName))
                return;
            System.out.println("Enter you correspondingFieldNumber\n");
            int correspondingFieldNumber = scanner.nextInt();
            System.out.println("Numerical filter or optional filter?\n");
            String type = scanner.nextLine();
            if (type.equalsIgnoreCase("Numerical")) {
                filterByNumericalField(categoryName, correspondingFieldNumber);
            }
            if (type.equalsIgnoreCase("Optional")) {
                filterByOptionalField(categoryName, correspondingFieldNumber);
            }

        }
    }

    private void filterByOptionalField(String categoryName, int correspondingFieldNumber) {
        System.out.println("Enter acceptable options");
        ArrayList<String> options = new ArrayList<String>();
        while (scanner.hasNext())
            options.add(scanner.next());
        OptionalFilter optionalFilter = new OptionalFilter("Optional filter " + categoryName + " " + correspondingFieldNumber, options, correspondingFieldNumber);
    }

    private void filterByNumericalField(String categoryName, int correspondingFieldNumber) throws Exception {
        System.out.println("Enter start range");
        int startRange = scanner.nextInt();
        System.out.println("Enter finish range");
        int finishRange = scanner.nextInt();
        Category category = manageCategoryMenu.getCategory(categoryName);
        NumericalFilter numericalFilter = new NumericalFilter("Numerical filter " + categoryName + " " + correspondingFieldNumber + " " + startRange + " " + finishRange, startRange, finishRange, correspondingFieldNumber);
        filteringMenu.filter(numericalFilter);
        System.out.println("successfully filtered");
    }

    private void filterByName() throws Exception {
        System.out.println("Enter name\n");
        String name = scanner.nextLine();
        FilterByName filterByName = new FilterByName("Filter by name " + name, name);
        filteringMenu.filter(filterByName);
    }

    private boolean filterByCategory(String categoryName) throws Exception {

        Category category = null;
        if (!manageCategoryMenu.checkCategoryName(categoryName)) {
            System.out.println("invalid category name\n");
            return true;
        }
        category = manageCategoryMenu.getCategory(categoryName);
        FilterByCategory filterByCategory = new FilterByCategory("Filter by category" + categoryName, category);
        filteringMenu.filter(filterByCategory);
        return false;
    }

    private void initializeSortingMenu() {
        sortingMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")){
                    sortingMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    sortingMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^show available sorts$")) {
                    System.out.println("price, number of visits, average score");
                    return;
                }
                if (command.matches("sort (?<sort>\\S+)")) {
                    Matcher matcher = Pattern.compile("sort (?<sort>\\S+)").matcher(command);
                    matcher.matches();
                    try {
                        sortingMenu.sort(matcher.group("sort"));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
                if (command.matches("current sort")) {
                    System.out.println(SortingMenu.getCurrentSort());
                    return;
                }
                if (command.matches("disable sort")) {
                    sortingMenu.disableSort();
                    System.out.println("sort successfully disabled");
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void manageAllProducts() {
        managerMenu.manageAllProducts();
    }

    private void initializeManageAllProductsMenu() {
        manageAllProducts.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("products")){
                    manageAllProducts.products();
                    return;
                }
                if (command.matches("back")) {
                    manageAllProducts.goToPreviousMenu();
                    return;
                }
                if (command.matches("remove (?<productId> \\d+)")) {
                    Matcher matcher = Pattern.compile("remove (?<prooductId>\\d+)").matcher(command);
                    matcher.matches();
                    try {
                        manageAllProducts.removeCommodity(Integer.parseInt(matcher.group("productId")));
                    } catch (Exception e) {
                        System.out.println("invalid commodity id");
                    }
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    public void run() throws Exception {
        MenuHandler.getInstance().setCurrentMenu(loginRegisterMenu);
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("exit")) {
            MenuHandler.getInstance().getCurrentMenu().commandProcess(command);
        }
    }
}
