package old.commands;

import model.DiscountCode;
import model.DataManager;

public class RemoveDiscountCode extends Command {
    public RemoveDiscountCode() {
        super.regex = "";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (!DataManager.isThereAnyDiscountCodeWithThisCode(splitCommand[2]))
            return "Wrong code";
        DiscountCode discountCode = DataManager.getDiscountWithCode(splitCommand[1]);
        DataManager.getAllDiscountCodes().remove(discountCode);
        return "Discount code removed";
    }
}
