package old.commands;

import model.DataManager;

public class GetDiscountCodeCommand extends Command {

    public GetDiscountCodeCommand() {
        super.regex = "";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (DataManager.isThereAnyDiscountCodeWithThisCode(splitCommand[3])){
            return DataManager.getDiscountCodeWithCode(splitCommand[3]).toString();
        }else return "Wrong code";
    }
}
