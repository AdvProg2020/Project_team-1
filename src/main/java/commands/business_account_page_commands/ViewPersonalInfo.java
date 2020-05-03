package commands.business_account_page_commands;

import commands.CommandAlt;
import model.account.SimpleAccount;

public class ViewPersonalInfo extends CommandAlt {

    public ViewPersonalInfo(){
        commandRegex = "^view personal info$";
    }

    @Override
    protected String runCommand(SimpleAccount requestSender, String input) throws Exception {
        return requestSender.toString();
    }
}
