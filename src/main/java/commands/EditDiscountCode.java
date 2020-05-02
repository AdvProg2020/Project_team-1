package commands;

import controller.EditDiscountCodeMenu;
import controller.HandleMenu;
import model.DiscountCode;
import model.SuperMarket;

public class EditDiscountCode extends Command{
    public EditDiscountCode(String regex) {
        super(regex);
    }

    @Override
    protected String runCommand(String input) throws Exception {
        String[] splitCommand = input.split(" ");
        DiscountCode discountCode = SuperMarket.getDiscountWithCode(splitCommand[3]);
        HandleMenu.setMenu(new EditDiscountCodeMenu(discountCode));
        return "1.Change Code" + "\n" +
                "2.Change first date" + "\n" +
                "3.Change finish date" + "\n" +
                "4.Change discount percentage" + "\n" +
                "5.Change maximum number of uses" + "\n" +
                "6.Change maximum discount price" + "\n" +
                "7.Change accounts";

    }
}
