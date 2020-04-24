package model.filter;

import model.Commodity;

public interface Filter {
    boolean isCommodityMatches(Commodity commodity);
}
