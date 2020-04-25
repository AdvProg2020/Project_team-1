package model.filter;

import model.Category;
import model.Commodity;

public class FilterByCategory implements Filter {
    private Category category;

    public FilterByCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean isCommodityMatches(Commodity commodity) {
        return commodity.getCategory().getName().equals(category.getName());
    }
}
