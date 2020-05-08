package commands.business_panel;

import commands.Command;
import model.Commodity;
import model.SuperMarket;
import model.account.BusinessAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveProductCommand extends Command {
    public RemoveProductCommand() {
        super.regex = "^remove product (\\d+)$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        int productId = Integer.parseInt(matcher.group(1));
        BusinessAccount businessAccount = ((BusinessAccount) SuperMarket.getOnlineAccount());
        Commodity commodity = businessAccount.getCommodityById(productId);
        if (commodity == null) {
            throw new Exception("Product ID not found.");
        }
        businessAccount.removeCommodity(commodity);
        return "Product removed.";
    }
}
