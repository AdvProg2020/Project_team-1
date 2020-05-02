package commands;

import controller.CreateDiscountCodeMenu;
import controller.HandleMenu;

public class DiscountCodeCommand extends Command {
    public DiscountCodeCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new CreateDiscountCodeMenu());
        return "Enter discount code information";
    }
}
