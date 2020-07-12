package common.model.filter;

import common.model.commodity.Category;
import common.model.commodity.Commodity;

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
        return commodity.getCategoryName().equals(category.getName());
    }
}
