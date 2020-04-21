package model.filter;

import model.Commodity;

public interface Filter {
    boolean isCommodityMatchs(Commodity commodity);
}
