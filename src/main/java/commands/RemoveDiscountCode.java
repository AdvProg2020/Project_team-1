package commands;

import model.DiscountCode;
import model.SuperMarket;

public class RemoveDiscountCode extends Command {
    public RemoveDiscountCode() {
        super.regex = "";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (!SuperMarket.isThereAnyDiscountCodeWithThisCode(splitCommand[2]))
            return "Wrong code";
        DiscountCode discountCode = SuperMarket.getDiscountWithCode(splitCommand[1]);
        SuperMarket.getAllDiscountCodes().remove(discountCode);
        return "Discount code removed";
    }
}
