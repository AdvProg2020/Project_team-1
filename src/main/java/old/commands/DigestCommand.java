package old.commands;

import old.DigestMenu;
import old.HandleMenu;
import model.Commodity;
import model.DataManager;

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
        this.commodity = (Commodity) DataManager.getNearHand();

    }

    @Override
    public String runCommand(String command) throws Exception {
        HandleMenu.setMenu(new DigestMenu(commodity));
        return commodity.toString();
    }
}
