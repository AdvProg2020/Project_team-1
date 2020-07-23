package client.view.commandline;

import client.controller.commodity.AuctionMenu;
import client.controller.commodity.ProductMenu;
import client.controller.customer.Chat;
import client.controller.customer.CustomerPanel;
import client.controller.manager.*;
import server.controller.SupportMenu;
import server.controller.customer.CartMenu;
import server.controller.customer.CustomerMenu;
import server.controller.manager.*;
import server.controller.off.OffMenu;
import server.controller.reseller.ManageResellerOffsMenu;
import server.controller.reseller.ManageResellerProductsMenu;
import server.controller.reseller.ResellerMenu;
import server.controller.share.*;

public class View {
    public static final ManageUsersPanel manageUsersPanel = new ManageUsersPanel();
    public static final MainMenu mainMenu = new MainMenu();
    public static final GetDiscountCodeMenu getDiscountCodeMenu = new GetDiscountCodeMenu();
    public static final CreateDiscountCodeMenu createDiscountCodeMenu = new CreateDiscountCodeMenu();
    public static final CustomerPanel customerPanel = new CustomerPanel();
    public static final ProductMenu productMenu = new ProductMenu();
    public static final GetDiscountCode getDiscountCode = new GetDiscountCode();
    public static final ManagerMenu managerMenu = new ManagerMenu();
    public static final ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
    public static final ManageUsersMenu manageUsersMenu = new ManageUsersMenu();
    public static final CustomerMenu customerMenu = new CustomerMenu();
    public static final CartMenu cartMenu = new CartMenu();
    public static final LoginRegisterMenu loginRegisterMenu = new LoginRegisterMenu();
    public static final ManageRequestMenu manageRequestMenu = new ManageRequestMenu();
    public static final ResellerMenu resellerMenu = new ResellerMenu();
    public static final ManageResellerProductsMenu manageResellerProductsMenu = new ManageResellerProductsMenu();
    public static final ManageResellerOffsMenu manageResellerOffMenu = new ManageResellerOffsMenu();
    public static final OffMenu offMenu = new OffMenu();
    public static final ProductsMenu productsMenu = new ProductsMenu();
    public static final ManageCategoryMenu manageCategoryMenu = new ManageCategoryMenu();
    public static final FilteringMenu filteringMenu = new FilteringMenu();
    public static final SortingMenu sortingMenu = new SortingMenu();
    public static final CreateDiscountCode createDiscountCode = new CreateDiscountCode();
    public static final SupportMenu supportMenu = new SupportMenu();
    public static final Chat chat = new Chat();
    public static final AuctionMenu auctionMenu = new AuctionMenu();
    public static final ManageCategoriesMenu manageCategoriesMenu = new ManageCategoriesMenu();
    public static final ManageRequestsMenu manageRequestsMenu = new ManageRequestsMenu();
}