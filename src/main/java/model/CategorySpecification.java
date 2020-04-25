package model;

import java.util.HashSet;

public class CategorySpecification {
    private String title;
    private HashSet<String> options;

    public CategorySpecification(String title, HashSet<String> options) {
        this.title = title;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashSet<String> getOptions() {
        return options;
    }

    public void setOptions(HashSet<String> options) {
        this.options = options;
    }
}
