package commands.business_panel;

import commands.Command;
import controller.HandleMenu;
import controller.business_menu.AddProductMenu;
import model.Request;
import model.SuperMarket;

public class AddProduct extends Command {
    public AddProduct() {
        super.regex = "^add product$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new AddProductMenu());
        return "";
    }
}
