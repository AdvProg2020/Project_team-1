package commands;

import model.account.SimpleAccount;

public abstract class CommandAlt {
    protected String commandRegex;

    protected boolean isCommandMatch( String input) {
        return input.matches(commandRegex);
    }

    protected abstract String runCommand(SimpleAccount requestSender, String input) throws Exception;
}
