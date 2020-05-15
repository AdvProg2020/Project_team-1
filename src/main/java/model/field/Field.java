package model.field;

public abstract class Field {
    private String title;

    public Field(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract Object getValue();
}
