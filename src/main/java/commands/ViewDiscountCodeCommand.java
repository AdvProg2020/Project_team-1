package commands;


import controller.GetDiscountCodes;
import controller.HandleMenu;
import model.DiscountCode;
import model.SuperMarket;

public class ViewDiscountCodeCommand extends Command{

    public ViewDiscountCodeCommand(String regex) {
        super(regex);
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
