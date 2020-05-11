package commands.get_info_to_purchase;

import commands.Command;

public class GetPostalCode extends Command {
    public GetPostalCode() {
        this.regex = "\\d{10}";
    }

    @Override
    public String runCommand(String command) throws Exception {

        return "please enter a discount code or go to next line";
    }
}
