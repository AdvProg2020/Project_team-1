package commands;

import controller.ProductsMenu;
import model.SuperMarket;
import model.filter.FilterByCategory;
import model.filter.FilterByName;

import java.lang.ref.PhantomReference;

public class Filter extends Command {

    public Filter(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        String[] splitCommand = command.split("\\s");
        if (splitCommand[1].equals("name")){
            ProductsMenu.filter(new FilterByName(splitCommand[2]));
            return ProductsMenu.getFilteredCommodities().toString();
        }
        if (splitCommand[1].equals("category") && splitCommand.length == 3){
            ProductsMenu.filter(new FilterByCategory(SuperMarket.getCategoryByName(splitCommand[2])));
            return ProductsMenu.getFilteredCommodities().toString();
        }
        if (splitCommand[1].equals("category") && splitCommand.length > 3){
            ProductsMenu.filter(new FilterByCategory(SuperMarket.getCategoryByName(splitCommand[2])));
            if ()
        }
        return "invalid command";
    }
}
