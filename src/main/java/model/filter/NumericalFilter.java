package model.filter;

import model.Commodity;
import model.field.NumericalField;

public class NumericalFilter implements Filter {
    private int rangeStart;
    private int rangeEnd;
    private int correspondingFieldNumber;

    public NumericalFilter(int rangeStart, int rangeEnd, int correspondingFieldNumber) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.correspondingFieldNumber = correspondingFieldNumber;
    }

    public void setCorrespondingFieldNumber(int correspondingFieldNumber) {
        this.correspondingFieldNumber = correspondingFieldNumber;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    @Override
    public boolean isCommodityMatches(Commodity commodity) {
        NumericalField numericalField = (NumericalField) commodity.getCategorySpecifications().get(correspondingFieldNumber);
        return rangeStart < numericalField.getValue() && numericalField.getValue() < rangeEnd;
    }
}
