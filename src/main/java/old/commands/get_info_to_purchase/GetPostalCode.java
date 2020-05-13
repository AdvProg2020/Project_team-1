package old.commands.get_info_to_purchase;

import old.commands.Command;
import controller.HandleMenu;
import old.get_info_to_purchase.GetDiscountCodeMenu;
import old.get_info_to_purchase.GetPostalCodeMenu;

public class GetPostalCode extends Command {
    public GetPostalCode() {
        this.regex = "\\d{10}";
    }

    @Override
    public String runCommand(String command) throws Exception {
        GetPostalCodeMenu menu = (GetPostalCodeMenu) HandleMenu.getMenu();
        String address = menu.getAddress();
        String phone = menu.getPhoneNumber();
        HandleMenu.setMenu(new GetDiscountCodeMenu(address, phone, command));
        return "please enter a discount code or if you don't want to use a discount code, enter nothing";
    }
}
