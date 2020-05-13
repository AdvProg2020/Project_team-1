package old.commands;

import controller.HandleMenu;
import old.ManageRequestsMenu;
import model.Request;
import model.DataManager;

public class ManageRequestCommand extends Command {
    public ManageRequestCommand() {
        super.regex = "";
    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new ManageRequestsMenu());
        String respond = "";
        for (Request request : DataManager.getAllRequests()) {
            respond += "[" + request.toString() +"]";
        }
        return respond;
    }
}
