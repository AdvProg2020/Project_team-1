package old.commands.get_info_to_purchase;

import old.commands.Command;
import old.HandleMenu;
import old.PersonalAccountMenu;
import model.DiscountCode;
import model.SuperMarket;
import model.account.PersonalAccount;

public class GetDiscountCode extends Command {
    @Override
    public String runCommand(String command) throws Exception {
        PersonalAccount account = (PersonalAccount) SuperMarket.getOnlineAccount();
        int price = account.getTotalPrice();
        int money = account.getCredit();
        if (!command.equals("")) {
            try {
                DiscountCode code = SuperMarket.getDiscountWithCode(command);
                account.doesHaveThisDiscount(code);
                code.isStillValid();
                account.useThisDiscount(code);
                if (code.getMaximumDiscountPrice() <= code.getDiscountPercentage() * price / 100) {
                    price -= code.getMaximumDiscountPrice();
                } else {
                    price -= code.getDiscountPercentage() * price / 100;
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        HandleMenu.setMenu(new PersonalAccountMenu(account));
        if (price <= money) {
            //to do
            return "you purchased successfully";
        }
        return "you don't have enough money to purchase";
    }
}
