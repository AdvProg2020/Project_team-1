package commands;

import model.Commodity;
import model.SuperMarket;
import model.field.Field;

public class AttributeCommand extends Command {
    public AttributeCommand() {
        this.regex = "^attributes$";
    }

    @Override
    public String runCommand(String command) throws Exception {
       String respond;
       Commodity commodity = (Commodity) SuperMarket.getNearHand();
       respond = commodity.toString();
        for (Field categorySpecification : commodity.getCategorySpecifications()) {
            respond += "[" + categorySpecification.toString() + "]";
        }
        return respond;
    }
}
