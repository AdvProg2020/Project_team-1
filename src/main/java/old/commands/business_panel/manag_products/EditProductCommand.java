package old.commands.business_panel.manag_products;

import old.commands.Command;

public class EditProductCommand extends Command {

    public EditProductCommand() {
        super.regex = "^edit (\\w+)$";
    }

    @Override
    public String runCommand(String command) throws Exception {

    }
}
