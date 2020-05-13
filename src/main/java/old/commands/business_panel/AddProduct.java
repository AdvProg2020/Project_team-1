package old.commands.business_panel;

import old.commands.Command;
import controller.HandleMenu;
import old.business_menu.AddProductMenu;

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
