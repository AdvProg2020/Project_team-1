package common.model.filter;

import common.model.commodity.Commodity;

public abstract class Filter {
    private String filterName;

    @Override
    public String toString() {
        return "Filter{" +
                "filterName='" + filterName + '\'' +
                '}';
    }

    public String getFilterName() {
        return filterName;
    }

    public Filter(String filterName) {
        this.filterName = filterName;
    }

    public abstract boolean isCommodityMatches(Commodity commodity);
}
