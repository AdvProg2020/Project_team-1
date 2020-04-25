package commands;

import controller.ProductsMenu;
import main.Main;
import model.Category;
import model.SuperMarket;
import model.filter.FilterByCategory;
import model.filter.FilterByName;
import model.filter.NumericalFilter;
import model.filter.OptionalFilter;
import org.graalvm.compiler.lir.StandardOp;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.HashSet;

public class Filter extends Command {

    public Filter(String regex) {
        super(regex);
    }

    public String filterByOptionalField(int correspondingFieldNumber, Category category) {
        ArrayList<String> options = new ArrayList<String>();
        options = Main.getAllOptions(category.getFieldOptions().get(correspondingFieldNumber).getOptions());
        ProductsMenu.filter(new OptionalFilter("filter by optional field " + correspondingFieldNumber, options,correspondingFieldNumber));
        return ProductsMenu.getFilteredCommodities().toString();
    }

    public String filterByNumericalField(int correspondingFieldNumber) {
        int rangeStart = Main.getStartRange();
        int rangeEnd = Main.getEndRange();
        ProductsMenu.filter(new NumericalFilter("filter numerical field " + correspondingFieldNumber,rangeStart, rangeEnd, correspondingFieldNumber));
        return ProductsMenu.getFilteredCommodities().toString();
    }

    public String filterByField(String[] splitCommand) {
        Category category = SuperMarket.getCategoryByName(splitCommand[2]);
        ProductsMenu.filter(new FilterByCategory("filter by category " + splitCommand[2],category));
        int correspondingFieldNumber = Integer.parseInt(splitCommand[3]);
        if (category.getFieldOptions().get(correspondingFieldNumber).getOptions() == null) {
            return filterByNumericalField(correspondingFieldNumber);
        }
        return filterByOptionalField(correspondingFieldNumber,category);

    }

    @Override
    public String runCommand(String command) {
        String[] splitCommand = command.split("\\s");
        if (splitCommand[1].equals("name")) {
            ProductsMenu.filter(new FilterByName("filter by name "+ splitCommand[2] , splitCommand[2]));
            return ProductsMenu.getFilteredCommodities().toString();
        }
        if (splitCommand[1].equals("category") && splitCommand.length == 3) {
            ProductsMenu.filter(new FilterByCategory("filter by category "+ splitCommand[2] , SuperMarket.getCategoryByName(splitCommand[2]) ));
            return ProductsMenu.getFilteredCommodities().toString();
        }
        if (splitCommand[1].equals("category") && splitCommand.length > 3) {
            return filterByField(splitCommand);
        }
        return "invalid command";
    }

    public static boolean isOptionAvailable(String option, HashSet<String> allOptions) {
        for (String allOption : allOptions) {
            if (option.equals(allOption))
                return true;
        }
        return false;
    }


}
