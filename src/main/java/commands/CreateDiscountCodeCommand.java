package commands;

import model.DiscountCode;
import model.SuperMarket;
import model.account.SimpleAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateDiscountCodeCommand extends Command {
    public CreateDiscountCodeCommand() {
        super.regex = "^\\S+ \\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d \\d+ \\d+ \\d+ \\S+";
    }

    private String checkError(Date start , Date finish , int discountPercentage, int maximumDiscountPrice,
                              int maximumNumberOfUse){
        if  (finish.compareTo(start) > 0)
            return "wong dates";
        if (discountPercentage <= 0 )
            return "wrong discount percentage";
        if (maximumDiscountPrice <= 0)
            return "wrong maximum discount price";
        if (maximumNumberOfUse <= 0)
            return "wrong maximum number of use";

        return null;
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        String code = splitCommand[0];
        SimpleDateFormat format= new SimpleDateFormat("dd-mm-yyyy");
        Date startDate=format.parse(splitCommand[1]);
        Date finishDate = format.parse(splitCommand[2]);
        int discountPercentage = Integer.parseInt(splitCommand[3]);
        int maximumDiscountPrice = Integer.parseInt(splitCommand[4]);
        int maximumNumberOfUse = Integer.parseInt(splitCommand[5]);
        ArrayList<SimpleAccount> allAccount = new ArrayList<SimpleAccount>();
        for (int i = 6 ; i < splitCommand.length ; i++){
           SimpleAccount simpleAccount = SuperMarket.getAccountWithUsername(splitCommand[i]);
           if (simpleAccount == null)
               return "user name " + splitCommand[i] + " not found";
           allAccount.add(simpleAccount);
        }
        String error = checkError(startDate,finishDate,discountPercentage,maximumDiscountPrice,maximumDiscountPrice);
        if (error != null)
            return error;
        SuperMarket.addToDiscounts(new DiscountCode(code , startDate , finishDate, discountPercentage , maximumDiscountPrice, maximumNumberOfUse , allAccount));
        return "Discount code created";
    }
}
