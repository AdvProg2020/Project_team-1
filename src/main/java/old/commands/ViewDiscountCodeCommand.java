package old.commands;


import old.GetDiscountCodes;
import old.HandleMenu;
import model.DiscountCode;
import model.SuperMarket;

public class ViewDiscountCodeCommand extends Command{

    public ViewDiscountCodeCommand() {
        super.regex = "^view discount code$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String respond = "";
        for (DiscountCode discountCode : SuperMarket.getAllDiscountCodes()) {
            respond += "[" + discountCode.toString() + "]";
        }
        HandleMenu.setMenu(new GetDiscountCodes());
        return respond;
    }
}
