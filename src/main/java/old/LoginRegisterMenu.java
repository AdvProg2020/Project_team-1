package old;

import old.commands.Command;
import model.SuperMarket;
import model.account.SimpleAccount;

import java.util.HashSet;

public class LoginRegisterMenu extends ProductsMenu implements CommandProcess {
    CommandProcess commandProcess;

    public static HashSet<Command> registerAndLoginCommands = new HashSet<>();

    public boolean isUserNameValid(String userName) {
        for (SimpleAccount account : SuperMarket.getAllAccounts()) {
            if (account.getUsername().equals(userName))
                return true;
        }
        return false;
    }

    public void login(String userName, String password) {
        SimpleAccount onlineAccount = SuperMarket.getAccountByUserAndPassword(userName, password);
        SuperMarket.setOnlineAccount(onlineAccount);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command registerAndLoginCommand : registerAndLoginCommands) {
            if (registerAndLoginCommand.checkCommand(command))
                return registerAndLoginCommand.runCommand(command);
        }
        return "invalid command";
    }
}
