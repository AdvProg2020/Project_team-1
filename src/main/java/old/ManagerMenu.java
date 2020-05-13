package old;

import old.commands.Command;
import model.Commodity;
import model.DiscountCode;
import model.Request;
import model.SuperMarket;
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
        return SuperMarket.getAllAccounts();
    }

    public ArrayList<Commodity> getAllCommodities() {
        return SuperMarket.getAllCommodities();
    }

    public ArrayList<DiscountCode> getAllDiscounts() {
        return SuperMarket.getAllDiscountCodes();
    }

    public ArrayList<Request> getAllRequests() {
        return SuperMarket.getAllRequests();
    }

    public void createNewDiscount(DiscountCode discountCode) {
        SuperMarket.addToDiscounts(discountCode);
    }

    public String commandProcessor(String command) throws Exception {
        for (Command managerCommand : commands) {
            if (managerCommand.checkCommand(command))
                return managerCommand.runCommand(command);
        }
        return "invalid command";
    }

}
