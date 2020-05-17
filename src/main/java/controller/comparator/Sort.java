package controller.comparator;

import controller.comparator.off.OffDiscountPercentComparator;
import controller.comparator.off.OffEndTimeComparator;
import controller.comparator.off.OffIdComparator;
import controller.comparator.off.OffStartTimeComparator;
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
}
