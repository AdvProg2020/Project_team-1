package model.field;

public class OptionalField extends Field {
    private String value;

    public OptionalField(String title, String value) {
        super(title);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
