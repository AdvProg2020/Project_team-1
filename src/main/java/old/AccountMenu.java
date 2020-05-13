package old;

import old.commands.Command;
import model.account.SimpleAccount;

import java.util.HashSet;

public abstract class AccountMenu extends ProductsMenu implements CommandProcess {
    private static HashSet<Command> managerMenuCommands = new HashSet<>();
    private SimpleAccount account;
    CommandProcess commandProcess;

    public AccountMenu(SimpleAccount account) {
        this.account = account;
    }

    public SimpleAccount getAccount() {
        return this.account;
    }
}