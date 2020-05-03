package commands;

import model.SuperMarket;

public class GetDiscountCodeCommand extends Command {

    public GetDiscountCodeCommand() {
        super.regex = "";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (SuperMarket.isThereAnyDiscountCodeWithThisCode(splitCommand[3])){
            return SuperMarket.getDiscountCodeWithCode(splitCommand[3]).toString();
        }else return "Wrong code";
    }
}
