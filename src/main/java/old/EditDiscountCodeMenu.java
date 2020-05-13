package old;

import model.DiscountCode;
import model.SuperMarket;
import model.account.SimpleAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditDiscountCodeMenu implements CommandProcess {

    private DiscountCode discountCode;

    public EditDiscountCodeMenu(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }
    CommandProcess commandProcess;


    public void changeCode(String code) {
        discountCode.setCode(code);
    }

    public void changeStartDate(Date startDate) throws Exception {
        discountCode.setStartDate(startDate);
    }

    public void changeFinishDate(Date finishDate) throws Exception {
        discountCode.setFinishDate(finishDate);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (command.startsWith("1")) {
            changeCode(splitCommand[1]);
            return "Code successfully changed";
        }
        if (command.startsWith("2")) {
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
            Date startDate = format.parse(splitCommand[1]);
            changeStartDate(startDate);
            return "Start date successfully changed";
        }
        if (command.startsWith("3")) {
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
            Date finishDate = format.parse(splitCommand[1]);
            changeFinishDate(finishDate);
            return "Finish date successfully changed";
        }
        if (command.startsWith("4")) {
            int discountPercentage = Integer.parseInt(splitCommand[1]);
            discountCode.setDiscountPercentage(discountPercentage);
            return "Discount percentage changed";
        }
        if (command.startsWith("5")) {
            int maxDiscountPrice = Integer.parseInt(splitCommand[1]);
            discountCode.setMaximumDiscountPrice(maxDiscountPrice);
            return "Maximum discount percentage changed";
        }
        if (command.startsWith("6")) {
            int maxNumberOfUses = Integer.parseInt(splitCommand[1]);
            discountCode.setMaximumNumberOfUses(maxNumberOfUses);
            return "Maximum Discount percentage changed";
        }
        if (command.startsWith("7")) {
            return changeAccounts(splitCommand);
        }
        return null;
    }

    public String changeAccounts(String[] splitCommand) throws Exception {
        ArrayList<SimpleAccount> allAccount = new ArrayList<SimpleAccount>();
        for (int i = 2; i < splitCommand.length; i++) {
            SimpleAccount simpleAccount = SuperMarket.getAccountWithUsername(splitCommand[i]);
            if (simpleAccount == null)
                return "user name " + splitCommand[i] + " not found";
            allAccount.add(simpleAccount);
        }
        discountCode.setAccounts(allAccount);
        return "All accounts changed";
    }

}
