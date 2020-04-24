package model.filter;

import model.Commodity;
import model.field.OptionalField;
import sun.jvm.hotspot.types.Field;

import java.util.ArrayList;

public class OptionalFilter implements Filter {
    private ArrayList<String> acceptableOptions;
    private int correspondingFieldNumber;

    public OptionalFilter(int correspondingFieldNumber) {
        this.correspondingFieldNumber = correspondingFieldNumber;
        acceptableOptions = new ArrayList<String>();
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
        OptionalField optionalField = (OptionalField)commodity.getCategorySpecifications().get(correspondingFieldNumber);
        return acceptableOptions.contains(optionalField.getValue());
    }
}
