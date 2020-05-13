package old.commands;

import controller.HandleMenu;
import old.ProductMenu;
import model.Commodity;
import model.DataManager;

import java.util.regex.Matcher;

public class ViewProductCommand extends Command {

   Matcher matcher;
    public ViewProductCommand() {
        this.regex = "^view (?<productId>\\S+)$";
    }

    public boolean checkIsIdNumeric(String id){
        try {
            Integer.parseInt(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public String runCommand(String command) throws Exception {
        if (!checkIsIdNumeric(matcher.group("productId"))) {
            return "product id should be numeric";
        }
        int id = Integer.parseInt(matcher.group("productId"));
        Commodity commodity = DataManager.getCommodityById(id);
        HandleMenu.setMenu(new ProductMenu(commodity));
        DataManager.setNearHand(commodity);
        return "You are in product menu now";
    }
}
