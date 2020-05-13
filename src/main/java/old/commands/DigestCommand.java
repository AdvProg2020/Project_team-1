package old.commands;

import old.DigestMenu;
import controller.HandleMenu;
import model.Commodity;
import model.SuperMarket;

public class DigestCommand extends Command {
    Commodity commodity;

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public DigestCommand() {
        this.regex = "^digest$";
        this.commodity = (Commodity)SuperMarket.getNearHand();

    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new DigestMenu(commodity));
        return commodity.toString();
    }
}
