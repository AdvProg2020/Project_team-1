package old;

import old.commands.Command;
import model.Commodity;
import model.DiscountCode;
import model.Request;
import model.DataManager;
import model.account.SimpleAccount;

import java.util.ArrayList;

public class ManagerMenu extends AccountMenu implements CommandProcess {
    private static  ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    CommandProcess commandProcess ;


    public ManagerMenu(SimpleAccount account) {
        super(account);
    }

    public ArrayList<SimpleAccount> getAllAccounts() {
        return DataManager.getAllAccounts();
    }

    public ArrayList<Commodity> getAllCommodities() {
        return DataManager.getAllCommodities();
    }

    public ArrayList<DiscountCode> getAllDiscounts() {
        return DataManager.getAllDiscountCodes();
    }

    public ArrayList<Request> getAllRequests() {
        return DataManager.getAllRequests();
    }

    public void createNewDiscount(DiscountCode discountCode) {
        DataManager.addToDiscounts(discountCode);
    }

    public String commandProcessor(String command) throws Exception {
        for (Command managerCommand : commands) {
            if (managerCommand.checkCommand(command))
                return managerCommand.runCommand(command);
        }
        return "invalid command";
    }

}
