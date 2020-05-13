package old.commands.business_panel.manag_products;

import old.commands.Command;
import model.Commodity;
import model.SuperMarket;
import model.account.BusinessAccount;
import model.account.SimpleAccount;
import model.log.SellLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewBuyersCommand extends Command {

    public ViewBuyersCommand() {
        super.regex = "^view buyers (\\d+)$";
    }

    @Override
    public String runCommand(String command) throws Exception {
        StringBuilder result = new StringBuilder();
        Matcher matcher = Pattern.compile(regex).matcher(command);
        int productId = Integer.parseInt(matcher.group(1));
        BusinessAccount businessAccount = ((BusinessAccount) SuperMarket.getOnlineAccount());
        Commodity commodity = businessAccount.getCommodityById(productId);
        int buyersCount = 0;
        for (SellLog sellLog : businessAccount.getSellLogs()) {
            if (sellLog.getCommodities().contains(commodity)) {
                SimpleAccount buyer = sellLog.getBuyer();
                result.append(++buyersCount);
                result.append(" - " + buyer.getFullName());
                result.append(" : " + buyer.getUsername());
                result.append('\n');
            }
        }
        return result.toString();
    }
}
