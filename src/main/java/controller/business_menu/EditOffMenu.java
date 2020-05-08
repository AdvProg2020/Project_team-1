package controller.business_menu;

import controller.CommandProcess;
import model.Off;
import model.Status;
import model.SuperMarket;
import model.account.BusinessAccount;

import java.util.Date;

public class EditOffMenu implements CommandProcess {

    Off newOff;
    public EditOffMenu(String offId) {
        newOff = new Off(((BusinessAccount)SuperMarket.getOnlineAccount()).getOffById(offId));
        newOff.setStatus(Status.UNDER_CHECKING_FOR_EDIT);
    }

    public void editStartTime(Date newStartTime) {
        newOff.setStartTime(newStartTime);
    }

    public void editEndTime(Date newEndTime) {
        newOff.setEndTime(newEndTime);
    }

    public void editDiscountPercent(int newDiscountPercent) throws Exception{
        newOff.setDiscountPercent(newDiscountPercent);
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        return null;
    }
}
