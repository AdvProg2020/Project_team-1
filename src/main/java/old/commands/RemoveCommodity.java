package old.commands;

import old.HandleMenu;
import old.ManageProductsMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveCommodity extends Command {
    public RemoveCommodity() {
        super.regex = "remove (?<id>\\d+)";
    }

    @Override
    public String runCommand(String command) {
        ManageProductsMenu menu = (ManageProductsMenu) HandleMenu.getMenu();
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        try {
            menu.removeCommodityWithId(Integer.parseInt(matcher.group("id")));
        } catch (Exception e) {
            return e.getMessage();
        }
        return "product removed successfully";
    }
}