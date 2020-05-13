package old.commands;

import model.DataManager;
import model.account.PersonalAccount;

public class GetTotalPrice extends Command {
    public GetTotalPrice() {
        this.regex = "show total price";
    }

    @Override
    public String runCommand(String command) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) DataManager.getOnlineAccount();
        return "total price is " + personalAccount.getTotalPrice();
    }
}
