package controller;

import model.SuperMarket;

public class ManageProductsMenu extends ProductsMenu implements CommandProcess {
    public void removeCommodityWithId(int id) throws Exception {
        SuperMarket.getAllCommodities().remove(SuperMarket.getCommodityById(id));
    }

}
