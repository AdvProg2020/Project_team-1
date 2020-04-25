package model.filter;

import model.Commodity;
import model.field.OptionalField;

import java.util.ArrayList;

public class OptionalFilter extends Filter {
    private ArrayList<String> acceptableOptions;
    private int correspondingFieldNumber;

    public OptionalFilter(String filterName, ArrayList<String> acceptableOptions, int correspondingFieldNumber) {
        super(filterName);
        this.acceptableOptions = acceptableOptions;
        this.correspondingFieldNumber = correspondingFieldNumber;
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
        return acceptableOptions.contains(optionalField.getValue());
    }
}
