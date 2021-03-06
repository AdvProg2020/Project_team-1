package common.model.field;

public class NumericalField extends Field {
    private int value;

    public NumericalField(String title, int value) {
        super(title);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
