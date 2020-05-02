package controller;

import commands.Command;
import commands.DiscountCodeCommand;
import commands.ViewPersonalInfoCommand;
import model.Commodity;
import model.DiscountCode;
import model.Request;
import model.SuperMarket;
import model.account.SimpleAccount;

import java.util.ArrayList;
import java.util.HashSet;

public class ManagerMenu extends AccountMenu implements CommandProcess {
    private static  ArrayList<Command> commands = new ArrayList<>();

    public static ArrayList<Command> getCommands() {
        return commands;
    }

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
        for (Command registerAndLoginCommand : commands) {
            if (registerAndLoginCommand.checkCommand(command))
                return registerAndLoginCommand.runCommand(command);
        }
        return "invalid command";
    }

    @Override
    public String runCommand(String command) {
        return null;
    }
}
