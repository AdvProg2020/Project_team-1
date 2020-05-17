package controller.comparator;

import controller.comparator.off.OffDiscountPercentComparator;
import controller.comparator.off.OffEndTimeComparator;
import controller.comparator.off.OffIdComparator;
import controller.comparator.off.OffStartTimeComparator;
import controller.comparator.product.ProductBrandComparator;
import controller.comparator.product.ProductIdComparator;
import controller.comparator.product.ProductNameComparator;
import controller.comparator.product.ProductPriceComparator;
import model.Commodity;
import model.Off;

import java.util.ArrayList;

public class Sort {

    public static void sortOffArrayList(ArrayList<Off> offs, String field) throws Exception {
        switch (field) {
            case "discount percent":
                offs.sort(new OffDiscountPercentComparator());
                break;
            case "end time":
                offs.sort(new OffEndTimeComparator());
                break;
            case "start time":
                offs.sort(new OffStartTimeComparator());
                break;
            case "off id":
                offs.sort(new OffIdComparator());
                break;
            default:
                throw new Exception("field not exist");
        }
    }

    public static void sortProductArrayList(ArrayList<Commodity> commodities, String field) throws Exception {
        switch (field) {
            case "brand":
                commodities.sort(new ProductBrandComparator());
                break;
            case "name":
                commodities.sort(new ProductNameComparator());
                break;
            case "id":
                commodities.sort(new ProductIdComparator());
                break;
            case "price":
                commodities.sort(new ProductPriceComparator());
                break;
            default:
                throw new Exception("field not found");
        }
    }
}
