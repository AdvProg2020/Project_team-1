package model.filter;

import model.commodity.Category;
import model.commodity.Commodity;

public class FilterByCategory extends Filter {
    private Category category;

    public FilterByCategory(String filterName, Category category) {
        super(filterName);
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
