package common.model.field;

import java.io.Serializable;

public abstract class Field implements Serializable {
    private String title;

    public Field(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract Object getValue();
}
