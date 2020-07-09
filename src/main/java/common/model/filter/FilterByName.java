package common.model.filter;

import common.model.commodity.Commodity;

public class FilterByName extends Filter {
    private String commodityName;

    public FilterByName(String filterName, String commodityName) {
        super(filterName);
        this.commodityName = commodityName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    @Override
    public boolean isCommodityMatches(Commodity commodity) {
        return commodity.getName().equals(commodityName);
    }
}
