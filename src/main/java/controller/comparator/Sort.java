package controller.comparator;

import controller.comparator.buy_log.DeductedMoneyComparator;
import controller.comparator.buy_log.PayedMoneyComparator;
import controller.comparator.comment.ContentComparator;
import controller.comparator.comment.TitleComparator;
import controller.comparator.off.OffDiscountPercentComparator;
import controller.comparator.off.OffEndTimeComparator;
import controller.comparator.off.OffIdComparator;
import controller.comparator.off.OffStartTimeComparator;
import controller.comparator.product.*;
import model.Comment;
import model.Commodity;
import model.Off;
import model.log.BuyLog;

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
            case "number of visits":
                commodities.sort(new ProductVisitsComparator());
                break;
            case "number of scores":
                commodities.sort(new ProductNumberOfScores());
                break;
            case "average score":
                commodities.sort(new ProductAverageScoreComparator());
                break;
            default:
                throw new Exception("field not found");
        }
    }

    public static void sortBuyLogArrayList(ArrayList<BuyLog> buyLogs, String field) throws Exception {
        switch (field) {
            case "payed money":
                buyLogs.sort(new PayedMoneyComparator());
                break;
            case "deducted money":
                buyLogs.sort(new DeductedMoneyComparator());
                break;
            default:
                throw new Exception("field not found");
        }
    }

    public static void sortCommentArrayList(ArrayList<Comment> comments, String field) throws Exception {
        switch (field) {
            case "title":
                comments.sort(new TitleComparator());
                break;
            case "content":
                comments.sort(new ContentComparator());
                break;
            default:
                throw new Exception("field not found");
        }
    }
}
