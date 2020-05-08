package commands.business_panel.manag_products;

import commands.Command;
import model.Commodity;
import model.SuperMarket;
import model.account.BusinessAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewProductCommand extends Command {

    public ViewProductCommand() {
        super.regex = "^view (\\d+)$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        int productId = Integer.parseInt(matcher.group(1));
        Commodity commodity = ((BusinessAccount)SuperMarket.getOnlineAccount()).getCommodityById(productId);
        if (commodity == null) {
            throw new Exception("Product ID not found.");
        }
        return commodity.toString();
    }
}
