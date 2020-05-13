package old.commands;

import old.HandleMenu;
import old.ManageProductsMenu;
import old.ManagerMenu;
import model.Commodity;

import java.util.ArrayList;

public class ManageProducts extends Command {
    public ManageProducts() {
        super.regex = "manage all products";
    }

    @Override
    public String runCommand(String command) throws Exception {
        ManagerMenu menu = (ManagerMenu) HandleMenu.getMenu();
        ArrayList<Commodity> commodities = menu.getAllCommodities();
        String output = commodities.get(0).getInformation();
        for (int i = 1; i < commodities.size(); i++) {
            output += commodities.get(i).getInformation();
        }
        HandleMenu.setMenu(new ManageProductsMenu());
        return output;
    }
}
