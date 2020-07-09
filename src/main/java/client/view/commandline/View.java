package client.view.commandline;

import server.controller.commodity.CommentsMenu;
import server.controller.commodity.DigestMenu;
import server.controller.customer.CartMenu;
import server.controller.customer.CustomerMenu;
import server.controller.customer.OrderMenu;
import server.data.YaDataManager;
import server.controller.manager.*;
import server.controller.off.OffMenu;
import server.controller.reseller.ManageResellerOffsMenu;
import server.controller.reseller.ManageResellerProductsMenu;
import server.controller.reseller.ResellerMenu;
import server.controller.share.*;
import client.Session;
import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;
import common.model.commodity.*;
import common.model.field.Field;
import common.model.field.NumericalField;
import common.model.field.OptionalField;
import common.model.filter.*;
import common.model.log.BuyLog;
import common.model.log.SellLog;
import common.model.share.Request;

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
    public static final MainMenu mainMenu = new MainMenu();
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
    public static final CreateDiscountCode createDiscountCode = new CreateDiscountCode();
    public static final ManageAllProducts manageAllProducts = new ManageAllProducts();
    private final Scanner scanner = new Scanner(System.in);

    public View() {
        initializeManageRequestMenuCommandProcessor();
        initializeGetDiscountCodeMenu();
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
        commentsMenu.commandProcess = command -> {
            if (command.matches("products")) {
                commentsMenu.products();
                return;
            }
            if (command.equals("add comment")) {
                addComment();
                return;
            }
            if (command.equals("back")) {
                commentsMenu.goToPreviousMenu();
            } else if (command.equals("help")) {
                commentsMenuHelp();
                return;
            }
            System.out.println("invalid command");
        };
    }

    private void addComment() throws IOException {
        System.out.println("enter title:");
        String title = scanner.nextLine();
        System.out.println("enter your comment");
        String content = scanner.nextLine();
        commentsMenu.addComment(title, content);
    }

    private void initializeDigestMenu() {
        digestMenu.commandProcess = command -> {
            if (command.matches("products")) {
                digestMenu.products();
                return;
            }
            if (command.equals("add to cart")) {
                addToCart();
                return;
            }
            if (command.equals("back")) {
                digestMenu.goToPreviousMenu();
            } else if (command.equals("help")) {
                digestMenuHelp();
                return;
            }
            System.out.println("invalid command");
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
        commodityMenu.commandProcess = command -> {
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
            } else if (command.matches("sort comments by (?<field>\\w+)")) {
                sortComments(command);
            } else if (command.equals("back")) {
                commodityMenu.goToPreviousMenu();
            } else if (command.equals("help")) {
                commodityMenuHelp();
            } else System.out.println("invalid command");
        };
    }

    private void sortComments(String command) {
        Matcher matcher = Pattern.compile("sort comments by (?<field>\\w+)").matcher(command);
        matcher.matches();
        commodityMenu.setCommentsSortType(matcher.group("field"));
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
        String output = "rating: " + commodity.getAverageScore() + "\ncomments: \n";
        try {
            for (Comment comment : commodityMenu.getComments()) {
                output += '\n' + comment.getInformation();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(output);
    }

    private void attributes() {
        Commodity commodity = commodityMenu.getCommodity();
        StringBuilder respond = new StringBuilder(commodity.getInformation());
        for (Field categorySpecification : commodity.getCategorySpecifications()) {
            respond.append("[").append(categorySpecification.toString()).append("]");
        }
        System.out.println(respond);
    }

    private void digest() {
        Commodity commodity = commodityMenu.getCommodity();
        System.out.println(commodity.getInformation());
        commodityMenu.goToDigestMenu();
    }

    private void manageRequests(ManagerMenu managerMenu) throws IOException {
        String output = "";
        ArrayList<Request> allRequests = managerMenu.manageRequest();
        for (Request request : allRequests) {
            output += "[" + request.toString() + "]";
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

    private boolean changeStartDate(DiscountCode discountCode, GetDiscountCode getDiscountCode, Matcher matcher) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date startDate = null;
        try {
            startDate = format.parse(matcher.group("newfield"));
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return true;
        }
        try {
            getDiscountCode.changeStartDate(startDate, discountCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    private boolean changeFinishDate(DiscountCode discountCode, GetDiscountCode getDiscountCode, Matcher matcher) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date finishDate = null;
        try {
            finishDate = format.parse(matcher.group("newfield"));
        } catch (ParseException e) {
            System.out.println("invalid date format");
            return true;
        }
        try {
            getDiscountCode.changeFinishDate(finishDate, discountCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }


    private void editAccountInfo(String command) throws Exception {
        Matcher matcher = Pattern.compile("^edit (?<field>\\S+ ?\\S+) (?<newfield>\\S+)$").matcher(command);
        matcher.matches();
        if (matcher.group("field").equals("first name")) {
            viewPersonalInfoMenu.editFirstName(matcher.group("newfield"), Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("last name")) {
            viewPersonalInfoMenu.editLastName(matcher.group("newfield"), Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("email")) {
            viewPersonalInfoMenu.editEmail(matcher.group("newfield"), Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("password")) {
            viewPersonalInfoMenu.editPassword(matcher.group("newfield"), Session.getOnlineAccount());
        }
        if (matcher.group("field").equals("phone number")) {
            viewPersonalInfoMenu.editPhoneNumber(matcher.group("newfield"), Session.getOnlineAccount());
        }
    }

    private void initializeGetDiscountCodeMenu() {
        getDiscountCode.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("help")) {
                    viewDiscountCodeHelp();
                    return;
                }
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
                if (command.matches("^view discount code (?<code>\\S+)$")) {
                    Matcher matcher = Pattern.compile("^view discount code (?<code>\\S+)$").matcher(command);
                    matcher.matches();
                    viewDiscountCode(getDiscountCode, matcher.group("code"));
                    return;
                }
                if (command.matches("^edit discount code (?<code>\\S+) (?<field>code|maximum number of uses|maximum discount price|start date|finish date|add account|delete account|discount percentage) (?<newField>\\S+)$")) {
                    Matcher matcher = Pattern.compile("^edit discount code (?<code>\\S+) (?<field>code|maximum number of uses|maximum discount price|start date|finish date|add account|delete account|discount percentage) (?<newField>\\S+)$").matcher(command);
                    matcher.matches();
                    DiscountCode discountCode;
                    try {
                        discountCode = getDiscountCode.getDiscountCode(matcher.group("code"));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return;
                    }

                    editDiscountCode(discountCode, command);
                    return;
                }
                if (command.matches("remove discount code (?<code>\\S+)")) {
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
                if (command.matches("back")) {
                    viewPersonalInfoMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^edit (?<field>\\S+ ?\\S+) (?<newfield>\\S+)$")) {
                    editAccountInfo(command);
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
                if (command.matches("help")) {
                    managerMenuHelp();
                    return;
                }
                if (command.matches("products")) {
                    managerMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    managerMenu.goToPreviousMenu();
                    return;
                }
                if (command.equals("client.view personal info")) {
                    viewPersonalInfo();
                    return;
                }
                if (command.matches("^manage users$")) {
                    manageUsers(manageUsersMenu);
                    return;
                }
                if (command.matches("^create discount code$")) {
                    try {
                        createDiscountCode(managerMenu);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
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
        Matcher matcher = Pattern.compile("^edit discount code (?<code>\\S+) (?<field>code|maximum number of uses|maximum discount price|start date|finish date|add account|delete account|discount percentage) (?<newfield>\\S+)$").matcher(command);
        matcher.matches();

        if (matcher.group("field").equals("code")) {
            try {
                DiscountCode tmp = YaDataManager.getDiscountCodeWithCode(matcher.group("newfield"));
                System.out.println("invalid new code");
                return;
            } catch (Exception e) {

            }
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
        if (matcher.group("field").equals("discount percentage")) {
            try {
                getDiscountCode.changeDiscountPercentage(Integer.parseInt(matcher.group("newfield")), discountCode);
            } catch (Exception e) {
                System.out.println("invalid discount percentage");
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
                e.printStackTrace();
            }
        }
    }

    private void initializeViewPersonalInfoMenu() {
        viewPersonalInfoMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("help")) {
                    viewPersonalInfoHelp();
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
                if (command.matches("^edit (?<field>(first name||last name||email||phone number)) (?<newfield>\\S+)$")) {
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
                if (command.matches("help")) {
                    manageUsersHelp();
                    return;
                }
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
                if (command.matches("show order (?<id>.+)")) {
                    showOrder(command);
                } else if (command.matches("rate (?<id>\\d+) (?<rate>[1-5])")) {
                    rate(command);
                } else if (command.equals("back")) {
                    orderMenu.goToPreviousMenu();
                } else if (command.equals("help")) {
                    orderMenuHelp();
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
        Matcher matcher = Pattern.compile("show order (?<id>.+)").matcher(command);
        matcher.matches();
        int id = Integer.parseInt(matcher.group("id"));
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
                if (command.equals("client.view personal info")) {
                    viewPersonalInfo();
                } else if (command.equals("client.view cart")) {
                    goToCartMenu();
                } else if (command.equals("purchase")) {
                    purchase();
                } else if (command.equals("client.view orders")) {
                    viewOrders();
                } else if (command.equals("client.view balance")) {
                    System.out.println("your balance is " + customerMenu.getBalance());
                } else if (command.equals("client.view discount codes")) {
                    viewMyDiscountCodes();
                } else if (command.equals("back")) {
                    customerMenu.goToPreviousMenu();
                } else if (command.equals("help")) {
                    customerMenuHelp();
                } else if (command.matches("sort discounts by (?<field>.+)")) {
                    sortDiscountsCustomerMenu(command);
                } else if (command.matches("sort orders by (?<field>\\w+)")) {
                    sortOrdersCustomerMenu(command);
                } else System.out.println("invalid command");
            }
        };
    }

    private void sortDiscountsCustomerMenu(String command) {
        Matcher matcher = Pattern.compile("sort discounts by (?<field>.+)").matcher(command);
        matcher.matches();
        customerMenu.setDiscountsSortType(matcher.group("field"));
    }

    private void initializeCartMenu() {
        cartMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) {
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
                } else if (command.equals("back")) {
                    cartMenu.goToPreviousMenu();
                } else if (command.equals("help")) {
                    cartMenuHelp();
                } else if (command.matches("sort products by (?<field>\\w+)")) {
                    sortProductsCartMenu(command);
                } else System.out.println("invalid command");
            }
        };
    }

    private void sortOrdersCustomerMenu(String command) {
        Matcher matcher = Pattern.compile("sort orders by (?<field>\\w+)").matcher(command);
        matcher.matches();
        customerMenu.setOrderSortType(matcher.group("field"));
    }

    private void sortProductsCartMenu(String command) {
        Matcher matcher = Pattern.compile("sort products by (?<field>\\w+)").matcher(command);
        matcher.matches();
        cartMenu.setProductSortType(matcher.group("field"));
    }

    private void viewOrders() {
        String output = "your orders:";
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        try {
            for (BuyLog log : customerMenu.getOrders())
                output += "\n" + log.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(output);
    }

    private void viewMyDiscountCodes() {
        String output = "your discount codes:";
        try {
            for (DiscountCode discount : customerMenu.getMyDiscounts())
                output += "\n" + discount.getInformation() + ", numberOfTimesUsed = " + customerMenu.
                        getNumberOfTimesUsedDiscount(discount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(output);
    }

    private void goToCartMenu() {
        customerMenu.goToCartMenu();
    }

    private void purchase() {
        try {
            cartMenu.checkIsCommoditiesAvailable();
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
            DiscountCode discountCode = null;
            while (!done) {
                try {
                    discountCode = cartMenu.getDiscountCodeWithCode(code);
                    done = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage() + '\n' +
                            "please enter a discount code or enter nothing");
                    code = scanner.nextLine();
                }
            }
            try {
                cartMenu.purchase(discountCode);
                System.out.println("thanks for your purchase, see you soon!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
        String respond = "products in cart: ";
        try {
            for (Commodity commodity : cartMenu.getCartProducts())
                respond += '\n' + commodity.getInformation() + " ,amount: " + cartMenu.getAmountInCart(commodity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(respond);
    }

    private void viewDiscountCodes(ManagerMenu managerMenu) throws Exception {
        ArrayList<DiscountCode> discountCodes = managerMenu.viewDiscountCodesCommand();
        for (DiscountCode discountCode : discountCodes) {
            System.out.println(discountCode.toString() + "\n");
        }
    }

    private void viewDiscountCode(GetDiscountCode getDiscountCode, String code) {
        try {
            System.out.println(getDiscountCode.getDiscountCode(code).toString());
        } catch (Exception e) {
            System.out.println("Cant client.view discount code please try again");
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

    private void createManagerProfile(ManageUsersMenu manageUsersMenu) {
        try {
            System.out.println("Please enter your user name");
            String userName = scanner.nextLine();
            if (YaDataManager.isUsernameExist(userName)) {
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
            if (manageUsersMenu.checkEmail(email)) {
                System.out.println("This email is unavailable");
                return;
            }
            System.out.println("Please enter you phone number");
            String phoneNumber = scanner.nextLine();
            ManagerAccount managerAccount = new ManagerAccount(userName, firstName, lastName, email, phoneNumber, password);
            manageUsersMenu.createNewManager(managerAccount);
            System.out.println("Manager profile successfully created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void editFields(ViewPersonalInfoMenu viewPersonalInfoMenu, String command) {
        Matcher matcher = Pattern.compile("^edit (?<field>(first name||last name||email||phone number)) (?<newfield>\\S+)$").matcher(command);
        matcher.matches();
        try {
            if (matcher.group("field").equals("first name")) {
                viewPersonalInfoMenu.editFirstName(matcher.group("newfield"), Session.getOnlineAccount());
                viewPersonalInfoMenu.updateFile();
                return;
            }
            if (matcher.group("field").equals("last name")) {
                viewPersonalInfoMenu.editLastName(matcher.group("newfield"), Session.getOnlineAccount());
                viewPersonalInfoMenu.updateFile();
                return;
            }
            if (matcher.group("field").equals("email")) {
                viewPersonalInfoMenu.editEmail(matcher.group("newfield"), Session.getOnlineAccount());
                viewPersonalInfoMenu.updateFile();
                return;
            }
            if (matcher.group("field").equals("password")) {
                viewPersonalInfoMenu.editPassword(matcher.group("newfield"), Session.getOnlineAccount());
                viewPersonalInfoMenu.updateFile();
                return;
            }
            if (matcher.group("field").equals("phone number")) {
                viewPersonalInfoMenu.editPhoneNumber(matcher.group("newfield"), Session.getOnlineAccount());
                viewPersonalInfoMenu.updateFile();
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("wrong field");
    }

    private void manageUsers(ManageUsersMenu manageUsersMenu) throws IOException {
        ManagerMenu menu = ((ManagerMenu) MenuHandler.getInstance().getCurrentMenu());
        ArrayList<ManagerAccount> managerAccounts = YaDataManager.getManagers();
        ArrayList<PersonalAccount> personalAccounts = YaDataManager.getPersons();
        ArrayList<BusinessAccount> businessAccounts = YaDataManager.getBusinesses();
        String output = "Managers:";
        for (int i = 0; i < managerAccounts.size(); i++) {
            output += "\n" + managerAccounts.get(i).getUsername();
        }
        output += "\n" + "Business accounts:";
        for (int i = 0; i < businessAccounts.size(); i++) {
            output += "\n" + businessAccounts.get(i).getUsername();
        }
        output += "\n" + "Personal accounts:";
        for (int i = 0; i < personalAccounts.size(); i++) {
            output += "\n" + personalAccounts.get(i).getUsername();
        }
        System.out.println(output);
        managerMenu.manageUsers();
    }

    private void viewPersonalInfo() {
        System.out.println(Session.getOnlineAccount().getInformation());
        managerMenu.viewPersonalInfo();
    }

    private void createDiscountCode(ManagerMenu managerMenu) throws Exception {
        System.out.println("Enter code");
        String code = scanner.nextLine();
        try {
            DiscountCode discountCode = YaDataManager.getDiscountCodeWithCode(code);
            System.out.println("invalid code");
            return;
        } catch (Exception e) {

        }
        System.out.println("Enter start date in dd-mm-yyyy format");
        String stringStart = scanner.nextLine();
        if (!stringStart.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d")) {
            throw new Exception("invalid date format");
        }
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
        if (!stringFinish.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d")) {
            throw new Exception("invalid date format");
        }
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
            discountPercentage = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("invalid discount percentage");
            return;
        }
        System.out.println("Enter maximum discount price");
        int maximumDiscountPrice;
        try {
            maximumDiscountPrice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("invalid maximum discount price");
            return;
        }
        System.out.println("Enter maximum number of use");
        int maximumNumberOfUse;
        try {
            maximumNumberOfUse = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("invalid maximum number of user");
            return;
        }
        if (managerMenu.checkCreateDiscountCodeErrors(start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse)) {
            System.out.println("invalid input");
            return;
        }
        System.out.println("Enter accounts username in next line");
        String accounts = scanner.nextLine();
        String[] splitCommand = accounts.split(" ");
        ArrayList<PersonalAccount> allAccount = new ArrayList<PersonalAccount>();
        for (int i = 0; i < splitCommand.length; i++) {
            PersonalAccount personalAccount = YaDataManager.getPersonWithUserName(splitCommand[i]);
            if (personalAccount == null) {
                throw new Exception("user name " + splitCommand[i] + " not found");
            }
            allAccount.add(personalAccount);
        }
        try {
            managerMenu.addDiscountCode(code, start, finish, discountPercentage, maximumDiscountPrice, maximumNumberOfUse, allAccount);
        } catch (Exception e) {
            System.out.println("Invalid entry please try again");
            e.printStackTrace();
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
                    if (command.equalsIgnoreCase("client.view personal info")) {
                        viewPersonalInfo();
                    } else if (command.equalsIgnoreCase("client.view company info")) {
                        viewCompanyInfo();
                    } else if (command.equalsIgnoreCase("client.view sales history")) {
                        viewSalesHistory();
                    } else if (command.matches("sort sales history by (\\S+)")) {
                        sortSalesHistory(command);
                    } else if (command.equalsIgnoreCase("manage products")) {
                        manageResellerProduct();
                    } else if (command.equalsIgnoreCase("add product")) {
                        addProduct();
                    } else if (command.matches("^remove product \\d+$")) {
                        removeProduct(command);
                    } else if (command.equalsIgnoreCase("show categories")) {
                        showCategories();
                    } else if (command.matches("sort categories (\\S+)")) {
                        sortCategories(command);
                    } else if (command.equalsIgnoreCase("client.view offs")) {
                        viewResellerOffs();
                    } else if (command.equalsIgnoreCase("client.view balance")) {
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
                    e.printStackTrace();
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

    private void sortSalesHistory(String command) throws Exception {
        Matcher matcher = Pattern.compile("sort sales history by (?<field>\\S+)").matcher(command);
        matcher.matches();
        for (SellLog sellLog : resellerMenu.sortSalesHistory(matcher.group("field"))) {
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
        System.out.println("enter product specification bellow");
        ArrayList<Field> productCategorySpecification = new ArrayList<>();
        for (int i = 0; i < category.getFieldOptions().size(); ++i) {
            CategorySpecification categorySpecification = category.getFieldOptions().get(i);
            System.out.println("Enter product " + categorySpecification.getTitle() + ":");
            if (categorySpecification.getOptions() == null) {
                productCategorySpecification.add(new NumericalField(categorySpecification.getTitle(), scanner.nextInt()));
                scanner.nextLine();
            } else {
                StringBuilder options = new StringBuilder();
                for (String option : categorySpecification.getOptions()) {
                    options.append(option);
                    options.append('-');
                }
                System.out.println("(" + options.toString() + ")");
                productCategorySpecification.add(new OptionalField(categorySpecification.getTitle(), scanner.nextLine()));
            }
        }
        System.out.println("enter product description:");
        String description = scanner.nextLine();
        System.out.println("enter product amount:");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter image path;");
        String path = scanner.nextLine();
        resellerMenu.addProduct(brand, name, price, category, productCategorySpecification, description, amount, path);
        System.out.println("product request had been sent to managers");
    }

    private void removeProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^remove product (?<productId>\\d+)$").matcher(command);
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        resellerMenu.removeProduct(productId);
    }

    private void showCategories() throws Exception {
        for (Category category : YaDataManager.getCategories()) {
            System.out.println(category.toString());
        }
    }

    private void sortCategories(String command) throws Exception {
        Matcher matcher = Pattern.compile("sort categories (?<field>\\S+)").matcher(command);
        matcher.matches();
        for (Category category : resellerMenu.sortCategories(matcher.group("field"))) {
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
        System.out.println("1 - client.view personal info\n" +
                "2 - client.view company info\n" +
                "3 - client.view sales history\n" +
                "4 - sort sales history by [payed|discount]\n" +
                "5 - manage products\n" +
                "6 - add product\n" +
                "7 - remove product [productId]\n" +
                "8 - show categories\n" +
                "9 - sort categories [name]\n" +
                "10 - client.view offs\n" +
                "11 - client.view balance\n" +
                "12 - logout\n" +
                "13 - back");
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
                    if (command.matches("sort \\w+")) {
                        sortResellerProduct(command);
                    } else if (command.matches("^view (\\d+)$")) {
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

    public void sortResellerProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("sort (?<field>\\w+)").matcher(command);
        matcher.matches();
        for (Commodity commodity : manageResellerProductsMenu.sort(matcher.group("field"))) {
            System.out.println(commodity.toString());
        }
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
        Commodity oldCommodity = YaDataManager.getCommodityById(productId);
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
        boolean availability = scanner.nextLine().equals("y");
        System.out.println("enter product specification:\n" +
                "(type '-' if optional field remained unchanged and '-1' if numerical field remained unchanged)");
        ArrayList<Field> productCategorySpecification = new ArrayList<>();
        for (int i = 0; i < category.getFieldOptions().size(); ++i) {
            CategorySpecification categorySpecification = category.getFieldOptions().get(i);
            System.out.println("Enter product " + categorySpecification.getTitle() + ":");
            if (categorySpecification.getOptions() == null) {
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
                StringBuilder options = new StringBuilder();
                for (String option : categorySpecification.getOptions()) {
                    options.append(option);
                    options.append('-');
                }
                System.out.println("(" + options.toString() + ")");
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
        System.out.println("product edit request had been sent");
    }

    private void manageResellerProductsMenuHelp() {
        System.out.println("1 - client.view [productId]\n" +
                "2 - client.view buyers [productId]\n" +
                "3 - edit [productId]\n" +
                "4 - sort [brand | name | id | price]\n" +
                "5 - logout\n" +
                "6 - back");
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
                    if (command.matches("sort \\w+ \\w+")) {
                        sortResellerOff(command);
                    } else if (command.matches("^view \\d+$")) {
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

    public void sortResellerOff(String command) throws Exception {
        Matcher matcher = Pattern.compile("sort (?<field>\\w+ \\w+)").matcher(command);
        matcher.matches();
        for (Off off : manageResellerOffMenu.sort(matcher.group("field"))) {
            System.out.println(off.toString());
        }
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
        System.out.println("(enter date in format : 'MMM d yyyy' ex. Jun 18 10:30:00 2020)");
        String startTime = scanner.nextLine();
        System.out.println("enter off end time:");
        System.out.println("(enter date in format : 'MMM d yyyy' ex. Jun 18 10:30:00 2020)");
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
        System.out.println("1 - client.view [offId]\n" +
                "2 - edit [offId]\n" +
                "3 - add off\n" +
                "4 - sort[off id | start time | end time | discount percent]\n" +
                "5 - logout\n" +
                "6 - back");
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
                    if (command.matches("sort \\w+ \\w+")) {
                        sortOffs(command);
                    } else if (command.equalsIgnoreCase("offs")) {
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

    public void sortOffs(String command) throws Exception {
        Matcher matcher = Pattern.compile("sort (?<field>\\w+ \\w+)").matcher(command);
        matcher.matches();
        for (Off off : offMenu.sortOffs(matcher.group("field"))) {
            System.out.println(off.toString());
        }
    }

    private void showOffs() throws Exception {
        for (Off off : YaDataManager.getOffs()) {
            System.out.println(off.toString());
        }
    }

    private void showOffProduct(String command) throws Exception {
        Matcher matcher = Pattern.compile("^show product (?<productId>\\w+)$").matcher(command);
        matcher.matches();
        int productId = Integer.parseInt(matcher.group("productId"));
        YaDataManager.getCommodityById(productId).getInformation();
    }

    private void offMenuHelp() {
        System.out.println("1 - offs\n" +
                "2 - show product [productId]\n" +
                "3 - logout\n" +
                "4 - sort[off id | start time | end time | discount percent]\n" +
                "5 - help\n" +
                "6 - logout");
    }

    private void initializeManageRequestMenuCommandProcessor() {
        manageRequestMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("help")) {
                    manageRequestsHelp();
                    return;
                }
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
            System.out.println(manageRequestMenu.getRequestById(id).toString());
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
                if (command.matches("help")) {
                    productsMenuHelp();
                    return;
                }
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
                        output += "\n" + product.toString();
                    }
                    System.out.println(output);
                    return;
                }
                if (command.matches("^show product (?<productID>\\d+)$")) {
                    Matcher matcher = Pattern.compile("^show product (?<productID>\\d+)$").matcher(command);
                    matcher.matches();
                    try {
                        Commodity commodity = productsMenu.getProducts(Integer.parseInt(matcher.group("productID")));
                        System.out.println(commodity.toString());
                        commodity.setNumberOfVisits(commodity.getNumberOfScores() + 1);
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
        ArrayList<Category> categories = managerMenu.manageCategory();
        System.out.println("all categories:");
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    private void initializeManageCategoryMenu() {
        manageCategoryMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("help")) {
                    manageCategoriesHelp();
                    return;
                }
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
        System.out.println("category removed");
    }

    private void addCategory(String categoryName) throws Exception {
        if (manageCategoryMenu.checkCategoryName(categoryName)) {
            System.out.println("This name is not available");
            return;
        }
        ArrayList<CategorySpecification> categorySpecifications = new ArrayList<>();
        getCategorySpecificationFromUser(categorySpecifications);
        System.out.println("Enter commodities id, with the first non-numerical input scanner stops.");
        ArrayList<Integer> commoditiesId = new ArrayList<Integer>();
        String input = scanner.next();
        while (input.matches("^\\d+$")) {
            commoditiesId.add(Integer.parseInt(input));
            input = scanner.next();
        }
        if (!manageCategoryMenu.checkCommoditiesId(commoditiesId)) {
            System.out.println("wrong commodities id");
            return;
        }
        String tmp = scanner.nextLine();
        manageCategoryMenu.addCategory(categoryName, null, categorySpecifications);
        System.out.println("category created");
    }

    private void getCategorySpecificationFromUser(ArrayList<CategorySpecification> categorySpecifications) {
        String tmp = "yes";
        while (tmp.equalsIgnoreCase("Yes")) {
            System.out.println("Enter title");
            String title = scanner.nextLine();
            HashSet<String> options = null;
            System.out.println("Is your field optional?");
            String input = scanner.next();
            if (input.equalsIgnoreCase("Yes")) {
                options = new HashSet<String>();
                System.out.println("Enter options[end for exit]");
                input = scanner.next();
                while (!input.equals("end")) {
                    options.add(input);
                    input = scanner.next();
                }
            }
            categorySpecifications.add(manageCategoryMenu.createCategorySpecification(title, options));
            System.out.println("Do you want to add another category specification?");
            scanner.nextLine();
            tmp = scanner.nextLine();
        }
    }

    private void editCategory(String categoryName) throws IOException {
        if (!manageCategoryMenu.checkCategoryName(categoryName)) {
            System.out.println("invalid category name");
            return;
        }
        Category category = manageCategoryMenu.getCategory(categoryName);
        System.out.println("1-name");
        System.out.println("2-add category specification");
        System.out.println("3-remove category specification");
        System.out.println("4-add commodity");
        System.out.println("5-remove commodity");
        String field = scanner.nextLine();
        if (field.equalsIgnoreCase("name")) {
            System.out.println("Enter your new name");
            String newName = scanner.nextLine();
            manageCategoryMenu.changeName(newName, category);
            manageCategoryMenu.removeCategory(categoryName);
            manageCategoryMenu.updateFile(category);
            System.out.println("category updated");
            return;
        }
        if (field.equalsIgnoreCase("add category specification")) {
            addCategorySpecification(category);
            manageCategoryMenu.removeCategory(categoryName);
            manageCategoryMenu.updateFile(category);
            System.out.println("category updated");
            return;
        }
        if (field.equalsIgnoreCase("remove category specification")) {

            try {
                removeCategorySpecification(category);
                manageCategoryMenu.removeCategory(categoryName);
                manageCategoryMenu.updateFile(category);
                System.out.println("category updated");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return;
        }
        if (field.equalsIgnoreCase("add commodity")) {
            try {
                addCommodityToCategory(category);
                manageCategoryMenu.removeCategory(categoryName);
                manageCategoryMenu.updateFile(category);
                System.out.println("category updated");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        if (field.equalsIgnoreCase("remove commodity")) {
            try {
                removeCommodityFromCategory(category);
                manageCategoryMenu.removeCategory(categoryName);
                manageCategoryMenu.updateFile(category);
                System.out.println("category updated");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return;
        }
        System.out.println("wrong field");

    }

    private void addCategorySpecification(Category category) throws IOException {
        System.out.println("Enter title");
        String title = scanner.nextLine();
        HashSet<String> options = null;
        System.out.println("Is your field optional?");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            System.out.println("Enter options [end for exit]");
            options = new HashSet<String>();
            input = scanner.next();
            while (!input.equals("end")) {
                options.add(input);
                input = scanner.next();
            }
            String tmp = scanner.nextLine();
        }

        manageCategoryMenu.addCategorySpecification(options, title, category);
    }

    private void removeCategorySpecification(Category category) throws Exception {
        System.out.println("Enter category specification title");
        String title = scanner.nextLine();
        manageCategoryMenu.removeCategorySpecification(title, category);
    }

    private void addCommodityToCategory(Category category) throws Exception {
        System.out.println("Enter commodityID");
        int id = scanner.nextInt();
        String tmp = scanner.nextLine();
        manageCategoryMenu.addCommodity(id, category);
    }

    private void removeCommodityFromCategory(Category category) throws Exception {
        System.out.println("Enter commodityID");
        int id = scanner.nextInt();
        String tmp = scanner.nextLine();
        manageCategoryMenu.removeCommodity(id, category);
    }

    private void viewCategory() throws IOException {
        ArrayList<Category> categories = productsMenu.getAllCategories();
        System.out.println("all categories:");
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    private void initializeFilteringMenu() {
        filteringMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                if (command.matches("help")) {
                    filteringMenuHelp();
                    return;
                }
                if (command.matches("products")) {
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
            scanner.nextLine();
            String type = scanner.nextLine();
            if (type.equalsIgnoreCase("Numerical")) {
                filterByNumericalField(categoryName, correspondingFieldNumber);
            }
            if (type.equalsIgnoreCase("Optional")) {
                filterByOptionalField(categoryName, correspondingFieldNumber);
            }

        }
    }

    private void filterByOptionalField(String categoryName, int correspondingFieldNumber) throws Exception {
        System.out.println("Enter acceptable options");
        ArrayList<String> options = new ArrayList<String>();
        String input = scanner.next();
        while (!input.equals("end")) {
            options.add(input);
            input = scanner.next();
        }
        scanner.nextLine();
        // OptionalFilter optionalFilter = new OptionalFilter("Optional filter " + categoryName + " " + correspondingFieldNumber, options, correspondingFieldNumber , category);
        //filteringMenu.filter(optionalFilter);
        System.out.println("successfully filtered");
    }

    private void filterByNumericalField(String categoryName, int correspondingFieldNumber) throws Exception {
        System.out.println("Enter start range");
        int startRange = scanner.nextInt();
        System.out.println("Enter finish range");
        int finishRange = scanner.nextInt();
        scanner.nextLine();
        Category category = manageCategoryMenu.getCategory(categoryName);
        //NumericalFilter numericalFilter = new NumericalFilter("Numerical filter " + categoryName + " " + correspondingFieldNumber + " " + startRange + " " + finishRange, startRange, finishRange, correspondingFieldNumber);
        //.filter(numericalFilter);
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
                if (command.matches("help")) {
                    sortingMenuHelp();
                    return;
                }
                if (command.matches("products")) {
                    sortingMenu.products();
                    return;
                }
                if (command.matches("back")) {
                    sortingMenu.goToPreviousMenu();
                    return;
                }
                if (command.matches("^show available sorts$")) {
                    System.out.println("price, number of visits, average score, date");
                    return;
                }
                if (command.matches("sort (?<sort>\\S+ ?\\S+ ?\\S+)")) {
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
                if (command.matches("help")) {
                    manageAllProductsHelp();
                    return;
                }
                if (command.matches("products")) {
                    manageAllProducts.products();
                    return;
                }
                if (command.matches("back")) {
                    manageAllProducts.goToPreviousMenu();
                    return;
                }
                if (command.matches("remove (?<productId>\\d+)")) {
                    Matcher matcher = Pattern.compile("remove (?<productId>\\d+)").matcher(command);
                    matcher.matches();
                    try {
                        manageAllProducts.removeCommodity(Integer.parseInt(matcher.group("productId")));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(Integer.parseInt(matcher.group("productId")));
                        e.printStackTrace();
                    }
                    return;
                }
                System.out.println("invalid command");
            }
        };
    }

    private void customerMenuHelp() {
        System.out.println("1 - back\n" +
                "2 - help\n" +
                "3 - logout\n" +
                "4 - products\n" +
                "5 - purchase\n" +
                "6 - sort discounts by [code|finish date|max price|percentage|start date|times used|max of uses]\n" +
                "7 - sort orders by [discount|payed]\n" +
                "8 - client.view balance\n" +
                "9 - client.view cart\n" +
                "10 - client.view discount codes\n" +
                "11 - client.view orders\n" +
                "12 - client.view personal info");
    }

    private void cartMenuHelp() {
        System.out.println("1 - back\n" +
                "2 - decrease [productId]\n" +
                "3 - increase [productId]\n" +
                "4 - help\n" +
                "5 - logout\n" +
                "6 - products\n" +
                "7 - purchase\n" +
                "8 - show products\n" +
                "9 - show total price\n" +
                "10 - sort products by [average score|brand|id|name|number of scores|price|visits]\n" +
                "11 - client.view [productId]");
    }

    private void orderMenuHelp() {
        System.out.println("1 - back\n" +
                "2 - help\n" +
                "3 - logout\n" +
                "4 - products\n" +
                "5 - rate [productId] [1-5]\n" +
                "6 - show order [orderId]");
    }

    private void digestMenuHelp() {
        System.out.println("1 - add to cart\n" +
                "2 - back\n" +
                "3 - help\n" +
                "4 - login\n" +
                "5 - logout\n" +
                "6 - products\n" +
                "7 - register");
    }

    private void commentsMenuHelp() {
        System.out.println("1 - add comment\n" +
                "2 - back\n" +
                "3 - help\n" +
                "4 - login\n" +
                "5 - logout\n" +
                "6 - products\n" +
                "7 - register");
    }


    private void commodityMenuHelp() {
        System.out.println("1 - attributes\n" +
                "2 - back\n" +
                "3 - comments\n" +
                "4 - compare [productId]\n" +
                "5 - digest\n" +
                "6 - help\n" +
                "7 - login\n" +
                "8 - logout\n" +
                "9 - products\n" +
                "10 - register\n" +
                "11 - sort comments by [content|title]");
    }

    private void logout() {
        try {
            MenuHandler.getInstance().getCurrentMenu().logout();
            System.out.println("You logged out successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void managerMenuHelp() {
        System.out.println("1-client.view personal info");
        System.out.println("2-manage users");
        System.out.println("3-manage all products");
        System.out.println("4-client.view discount codes");
        System.out.println("5-manage requests");
        System.out.println("6-manage categories");
        System.out.println("7-create discount code");
    }

    private void viewPersonalInfoHelp() {
        System.out.println("1-edit (first name, last name, email, phone number ...) (new first name,...)");
    }

    private void manageUsersHelp() {
        System.out.println("1-client.view [user name]");
        System.out.println("2-delete user [user name]");
        System.out.println("3-create manager profile");
    }

    private void manageAllProductsHelp() {
        System.out.println("1-remove [productId]");
    }

    private void viewDiscountCodeHelp() {
        System.out.println("1-client.view discount code [code]");
        System.out.println("2-edit discount code [code] (code|maximum discount price|invalid maximum discount price|maximum number of uses|start date|finish date|add account|delete account|discount percentage) (new code|new maximum discount price|new invalid maximum discount price|new maximum number of uses|new start date(dd-mm-yyyy)|new finish date(dd-mm-yyyy)|user name)");
        System.out.println("3-remove discount code [code]");
    }

    private void manageRequestsHelp() {
        System.out.println("1-details [requestId]");
        System.out.println("2-accept [requestId]");
        System.out.println("3-decline [requestId]");
    }

    private void manageCategoriesHelp() {
        System.out.println("1-edit [category]");
        System.out.println("2-add [category]");
        System.out.println("3-remove [category]");
    }

    private void productsMenuHelp() {
        System.out.println("1-products");
        System.out.println("2-client.view categories");
        System.out.println("3-filtering");
        System.out.println("4-sorting");
        System.out.println("5-show products");
        System.out.println("6-show product [productId]");
    }

    private void filteringMenuHelp() {
        System.out.println("1-show available filter");
        System.out.println("2-filter [an available filter]");
        System.out.println("3-current filters");
        System.out.println("4-disable filter [a selected filter]");
    }

    private void sortingMenuHelp() {
        System.out.println("1-show available sorts");
        System.out.println("2-sort [an available sort]");
        System.out.println("3-current sort");
        System.out.println("4-disable sort");
    }

    private void setLoginRegisterMenu() {
        MenuHandler.getInstance().getCurrentMenu().setLoginAndRegisterMenu();
    }

    public void run() throws Exception {
        MenuHandler.getInstance().setCurrentMenu(loginRegisterMenu);
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("exit")) {
            if (command.equalsIgnoreCase("logout")) {
                logout();
                continue;
            }
            if (command.equalsIgnoreCase("login") || command.equalsIgnoreCase("register")) {
                setLoginRegisterMenu();
                continue;
            }
            MenuHandler.getInstance().getCurrentMenu().commandProcess(command);
            System.out.println("please enter your command");
        }
    }
}