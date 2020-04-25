package commands;

import controller.ProductsMenu;
import main.Main;
import model.Category;
import model.SuperMarket;
import model.filter.FilterByCategory;
import model.filter.FilterByName;
import model.filter.NumericalFilter;
import model.filter.OptionalFilter;

import java.util.ArrayList;
import java.util.HashSet;

public class Filter extends Command {

    public Filter(String regex) {
        super(regex);
    }

    public static boolean isOptionAvailable(String option, HashSet<String> allOptions) {
        for (String allOption : allOptions) {
            if (option.equals(allOption))
                return true;
        }
        return false;
    }

    public String filterByOptionalField(int correspondingFieldNumber, Category category) {
        ArrayList<String> options = new ArrayList<String>();
        options = Main.getAllOptions(category.getFieldOptions().get(correspondingFieldNumber).getOptions());
        ProductsMenu.filter(new OptionalFilter(correspondingFieldNumber, options));
        return ProductsMenu.getFilteredCommodities().toString();
    }

    public String filterByNumericalField(int correspondingFieldNumber) {
        int rangeStart = Main.getStartRange();
        int rangeEnd = Main.getEndRange();
        ProductsMenu.filter(new NumericalFilter(rangeStart, rangeEnd, correspondingFieldNumber));
        return ProductsMenu.getFilteredCommodities().toString();
    }

    public String filterByField(String[] splitCommand) {
        Category category = SuperMarket.getCategoryByName(splitCommand[2]);
        ProductsMenu.filter(new FilterByCategory(category));
        int correspondingFieldNumber = Integer.parseInt(splitCommand[3]);
        if (category.getFieldOptions().get(correspondingFieldNumber).getOptions() == null) {
            return filterByNumericalField(correspondingFieldNumber);
        }
        return filterByOptionalField(correspondingFieldNumber, category);

    }

    @Override
    public String runCommand(String command) {
        String[] splitCommand = command.split("\\s");
        if (splitCommand[1].equals("name")) {
            ProductsMenu.filter(new FilterByName(splitCommand[2]));
            return ProductsMenu.getFilteredCommodities().toString();
        }
        if (splitCommand[1].equals("category") && splitCommand.length == 3) {
            ProductsMenu.filter(new FilterByCategory(SuperMarket.getCategoryByName(splitCommand[2])));
            return ProductsMenu.getFilteredCommodities().toString();
        }
        if (splitCommand[1].equals("category") && splitCommand.length > 3) {
            return filterByField(splitCommand);
        }
        return "invalid command";
    }


}
