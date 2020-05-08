package controller.business_menu;

import controller.CommandProcess;
import model.Commodity;

public class EditProductMenu implements CommandProcess {
    Commodity commodity;

    public EditProductMenu(Commodity commodity) {
        this.commodity = new Commodity(commodity);
    }



    @Override
    public String commandProcessor(String command) throws Exception {
        return null;
    }
}
