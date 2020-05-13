package old.business_menu;

import old.CommandProcess;
import model.Off;
import model.Request;
import model.DataManager;
import model.account.BusinessAccount;

import java.util.Date;

public class AddOffMenu implements CommandProcess {
    public void addOff(Date startTime, Date endTime, int discountPercent) throws Exception{
        Off off = new Off((BusinessAccount) DataManager.getOnlineAccount(), startTime, endTime, discountPercent);
        DataManager.addRequest(new Request(off, DataManager.getOnlineAccount()));
    }
    CommandProcess commandProcess;

    @Override
    public String commandProcessor(String command) throws Exception {
        return null;
    }
}
