package commands.business_account_page_commands;

import commands.CommandAlt;
import model.account.SimpleAccount;

public class ViewPersonalInfo extends CommandAlt {
    private String commandRegex = "^view personal info$";

    @Override
    protected String runCommand(SimpleAccount requestSender, String input) throws Exception {
        if (!isCommandMatch(input)) {
            throw new Exception("Command doesn't match.");
        }
        return requestSender.toString();
    }
}
