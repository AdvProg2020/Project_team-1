package commands.business_panel;

import commands.Command;

public class AddProduct extends Command {
    public AddProduct() {
        super.regex = "^add product$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        //Create product request
        return "New product request has been sent for manager.";
    }
}
