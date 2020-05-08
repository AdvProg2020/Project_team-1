package controller.business_menu;

import controller.CommandProcess;
import model.Off;
import model.Request;
import model.SuperMarket;
import model.account.BusinessAccount;

import java.util.Date;

public class AddOffMenu implements CommandProcess {
    public void addOff(Date startTime, Date endTime, int discountPercent) throws Exception{
        Off off = new Off((BusinessAccount) SuperMarket.getOnlineAccount(), startTime, endTime, discountPercent);
        SuperMarket.addRequest(new Request(off, SuperMarket.getOnlineAccount()));
    }

    @Override
    public String commandProcessor(String command) throws Exception {
        return null;
    }
}
