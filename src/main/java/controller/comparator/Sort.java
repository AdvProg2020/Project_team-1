package controller.comparator;

import controller.comparator.buy_log.DeductedMoneyComparator;
import controller.comparator.buy_log.PayedMoneyComparator;
import controller.comparator.category.CategoryNameComparator;
import controller.comparator.comment.ContentComparator;
import controller.comparator.comment.TitleComparator;
import controller.comparator.discount.*;
import controller.comparator.off.OffDiscountPercentComparator;
import controller.comparator.off.OffEndTimeComparator;
import controller.comparator.off.OffIdComparator;
import controller.comparator.off.OffStartTimeComparator;
import controller.comparator.product.*;
import model.commodity.*;
import model.log.BuyLog;
import model.log.SellLog;

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
            case "visits":
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
            case "payed":
                buyLogs.sort(new PayedMoneyComparator());
                break;
            case "discount":
                buyLogs.sort(new DeductedMoneyComparator());
                break;
            default:
                throw new Exception("field not found");
        }
    }

    public static void sortSellLogArrayList(ArrayList<SellLog> sellLogs, String field) throws Exception {
        switch (field) {
            case "payed":
                sellLogs.sort(new PayedMoneyComparator());
                break;
            case "discount":
                sellLogs.sort(new DeductedMoneyComparator());
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

    public static void sortCategoryArrayList(ArrayList<Category> categories, String field) throws Exception {
        switch (field) {
            case "name":
                categories.sort(new CategoryNameComparator());
                break;

            default:
                throw new Exception("field not found");
        }
    }

    public static void sortDiscountArrayList(ArrayList<DiscountCode> discountCodes, String field) throws Exception {
        switch (field) {
            case "code":
                discountCodes.sort(new CodeComparator());
                break;
            case "finish date":
                discountCodes.sort(new FinishDateComparator());
                break;
            case "max price":
                discountCodes.sort(new MaxPriceComparator());
                break;
            case "percentage":
                discountCodes.sort(new PercentageComparator());
                break;
            case "start date":
                discountCodes.sort(new StartDateComparator());
                break;
            case "times used":
                discountCodes.sort(new UsedComparator());
                break;
            case "max of uses":
                discountCodes.sort(new UsesComparator());
                break;
            default:
                throw new Exception("field not found");
        }
    }
}
