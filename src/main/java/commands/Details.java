package commands;

import model.Request;
import model.SuperMarket;

public class Details extends Command {

    public Details() {
        super.regex = "";
    }

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (!isNumeric(splitCommand[1]))
            return "Id is not numeric.";
        if (!SuperMarket.isThereAnyRequestWithThisId(Integer.parseInt(splitCommand[1])))
            return "Id is not valid";
        Request request = SuperMarket.getRequestWithId(Integer.parseInt(splitCommand[1]));
        return request.toString();
    }
}