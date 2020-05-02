package commands;

import controller.HandleMenu;
import controller.ManageRequestsMenu;
import model.Request;
import model.SuperMarket;
import sun.tools.tree.SubtractExpression;

public class ManageRequestCommand extends Command {
    public ManageRequestCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) throws Exception {
        String respond = "";
        for (Request request : SuperMarket.getAllRequests()) {
            respond += "[" + request.toString() +"]";
        }
        return respond;
    }
}
