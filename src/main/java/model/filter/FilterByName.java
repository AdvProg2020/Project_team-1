package model.filter;

import model.Commodity;

public class FilterByName implements Filter {
    private String commodityName;

    public FilterByName(String commodityName) {
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
