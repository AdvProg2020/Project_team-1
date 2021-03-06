package common.model.filter;

import common.model.commodity.Category;
import common.model.commodity.Commodity;
import common.model.field.OptionalField;

import java.util.ArrayList;

public class OptionalFilter extends Filter {
    private ArrayList<String> acceptableOptions;
    private int correspondingFieldNumber;
    private Category category;

    public OptionalFilter(String filterName, ArrayList<String> acceptableOptions, int correspondingFieldNumber, Category category) {
        super(filterName);
        this.acceptableOptions = acceptableOptions;
        this.correspondingFieldNumber = correspondingFieldNumber;
        this.category = category;
    }

    public void addAcceptableOption(String acceptableOption) {
        acceptableOptions.add(acceptableOption);
    }

    public void removeAcceptableOption(String acceptableOption) {
        acceptableOptions.remove(acceptableOption);
    }

    public void clearAcceptableOptions() {
        acceptableOptions = new ArrayList<String>();
    }

    @Override
    public boolean isCommodityMatches(Commodity commodity) {
        OptionalField optionalField = (OptionalField) commodity.getCategorySpecifications().get(correspondingFieldNumber);
        return acceptableOptions.contains(optionalField.getValue()) && category.getName().equals(commodity.getCategoryName());
    }
}
