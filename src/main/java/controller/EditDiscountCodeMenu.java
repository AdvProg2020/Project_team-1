package controller;

import model.DiscountCode;

import java.util.Date;

public class EditDiscountCodeMenu implements CommandProcess{

    private DiscountCode discountCode;

    public EditDiscountCodeMenu(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }


    public void changeCode (String code){
        discountCode.setCode(code);
    }

    public void changeStartDate(Date startDate) throws Exception {
        discountCode.setStartDate(startDate);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (command.startsWith("1")){
            changeCode(splitCommand[1]);
            return "Code successfully changed";
        }
        if (command.startsWith("2")){

        }
    }

    @Override
    public String runCommand(String command) {
        return null;
    }
}
