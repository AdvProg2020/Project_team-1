package view;

import commands.business_panel.manage_off.AddOff;
import controller.CommandProcess;

import java.util.ArrayList;
import controller.*;

public class View {
    public ArrayList<CommandProcess> allMenus = new ArrayList<CommandProcess>();

    public View() {
        allMenus.add(new CreateDiscountCodeMenu());
        allMenus.add(new CreateManagerMenu());
        allMenus.add(new DigestMenu());
        allMenus.add(new EditDiscountCodeMenu());
        allMenus.add(new Filtering());
        allMenus.add(new GetAccountInfoMenu());
        allMenus.add(new GetCartMenu());
        allMenus.add(new GetDiscountCodes());
        allMenus.add(new LoginRegisterMenu());
        allMenus.add(new ManageCategoryMenu());
        allMenus.add(new ManageDiscountsMenu());
        allMenus.add(new ManageProductsMenu());
        allMenus.add(new ManageRequestMenu());
        allMenus.add(new ManageRequestsMenu());
        allMenus.add(new ManagerMenu());
        allMenus.add(new ManageUsersMenu());
        allMenus.add(new PersonalAccountMenu());
        allMenus.add(new ProductMenu());
        allMenus.add(new ProductsMenu());
        allMenus.add(new ViewPersonalInfoMenu());
    }

    public void run(){

    }
}
